package com.cyy.finance.biz.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.cyy.commom.constant.ApiResponseCode;
import com.cyy.commom.dto.TokenResponse;
import com.cyy.commom.exception.BizException;
import com.cyy.commom.exception.ParameterException;
import com.cyy.commom.service.TokenService;
import com.cyy.commom.util.DateUtil;
import com.cyy.commom.util.MyUtil;
import com.cyy.finance.biz.constant.RedisKeyConstant;
import com.cyy.finance.biz.domain.Member;
import com.cyy.finance.biz.domain.MemberBindPhone;
import com.cyy.finance.biz.dto.AdminDTO;
import com.cyy.finance.biz.dto.form.*;
import com.cyy.finance.biz.enums.SmsCodeTypeEnum;
import com.cyy.finance.biz.service.MemberBindPhoneService;
import com.cyy.finance.biz.service.MemberLoginService;
import com.cyy.finance.biz.service.MemberService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor  //构造参数注解
public class MemberLoginServiceImpl implements MemberLoginService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    final PasswordEncoder passwordEncoder;

    final MemberService memberService;

    final ObjectMapper objectMapper;

    final TokenService<AdminDTO> tokenService;

    /**
     * 获取图形验证码
     *
     * @param form
     * @return
     */
    @Override
    public String getBase64Code(GetBase64CodeForm form) {
        //hutool坐标下的类，用于生成图形验证码
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(300, 192, 5, 1000);
        //把图形验证码里面的内容读出来
        String code = lineCaptcha.getCode();
        log.info("图形验证码：{}", code);
        //将code保存在redis中,设置超时时间15分钟，15分钟之后，redis会自动删除
        redisTemplate.opsForValue().set(RedisKeyConstant.GRAPHIC_VERIFICATION_CODE + form.getClientId(), code, 15, TimeUnit.MINUTES);

        return lineCaptcha.getImageBase64();  //返回图形验证码
    }

    final MemberBindPhoneService memberBindPhoneService;

    /**
     * 获取客户端ID
     *
     * @return
     */
    @Override
    public String getClientId() {

        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取短信验证码
     *
     * @param form
     */
    @Override
    public void sendSmsCode(GetSmsCodeForm form) {
        //发送短信验证码之前，先校验图形验证码是否正确
        checkBase64Code(form.getClientId(), form.getCode());
        //设置短信验证码的key，存入redis中
        String key = RedisKeyConstant.SMS_CODE + form.getSmsCodeType() + form.getPhone();
        //通过key获取短信验证码，如果存在，则获取
        SmsCodeResult smsCodeResult = (SmsCodeResult) redisTemplate.opsForValue().get(key);
        //判断短信验证码是否为空
        if (smsCodeResult != null) {
            //判断短信验证码是否是60秒之内创建的
            Duration duration = DateUtil.getDuration(smsCodeResult.getGetTime(), DateUtil.getSystemTime());
            if (duration.getSeconds() < 60) {
                throw new BizException("验证码获取太频繁，请稍后重试");
            }
        }

        //校验手机号是否已经注册,调用mapper，执行sql语句
        MemberBindPhone memberBindPhone = memberBindPhoneService.getMemberByPhone(form.getPhone());
        //判断前端的短信验证码类型和是否是注册类型
        if (form.getSmsCodeType().equals(SmsCodeTypeEnum.REG.getCode()) && memberBindPhone != null) {
            throw new ParameterException("phone", "该手机号已注册！");
        }
        //判断前端的短信验证码类型和是否是登录类型
        if (form.getSmsCodeType().equals(SmsCodeTypeEnum.LOGIN.getCode()) && memberBindPhone == null) {
            throw new ParameterException("phone", "该手机号未注册！");
        }

        //如果以上都不是，则随机生成一个6位的短信验证码
        int smsCode = MyUtil.getRandom(6);
        smsCodeResult = new SmsCodeResult();
        smsCodeResult.setCode(String.valueOf(smsCode));
        //生成验证码的当前时间
        smsCodeResult.setGetTime(DateUtil.getSystemTime());
        //将短信验证码保存在redis中，设置过期时间15分钟
        redisTemplate.opsForValue().set(key, smsCodeResult, 15, TimeUnit.MINUTES);
        log.info("客户端id{},手机号：{},短信验证码：{}", form.getClientId(), form.getPhone(), smsCode);
        //smsService.sendSmsCode(form.getPhone(), smsCodeResult.getCode(), form.getSmsCodeType());

        //todo 调用第三方短信验证接口
    }

    /**
     * 校验图形验证码
     *
     * @param clientId
     * @return
     */
    @Override
    public boolean checkBase64Code(String clientId, String code) {
        //生成图片，获取base64编码字符串
        String cacheCode = (String) redisTemplate.opsForValue().get(RedisKeyConstant.GRAPHIC_VERIFICATION_CODE + clientId);
        //获取redis中的图形验证码之后就要删除，是一次性的
        redisTemplate.delete(RedisKeyConstant.GRAPHIC_VERIFICATION_CODE + clientId);
        //将用户输入的图形验证码与redis只能缓存的验证码进行比较
        if (!code.equalsIgnoreCase(cacheCode)) {
            //抛出参数异常，进行全局异常处理
            throw new ParameterException("code", "图形验证码错误！");
        }
        return true;
    }

    /**
     * 校验短信验证码
     *
     * @param phone
     * @param smsCode
     * @param smsCodeType
     */
    @Override
    public boolean checkSmsCode(String phone, String smsCode, String smsCodeType) {
        //从redis缓存中获取短信验证码
        SmsCodeResult cacheSmsCode = (SmsCodeResult) redisTemplate.opsForValue().get(RedisKeyConstant.SMS_CODE + smsCodeType + phone);
        //获取之后直接删除验证码即可
        redisTemplate.delete(RedisKeyConstant.SMS_CODE + smsCodeType + phone);
        //判断获取的短信验证码是否为空和前端传过来的短信验证码是否与缓存中一致
        if (cacheSmsCode == null || !smsCode.equals(cacheSmsCode.getCode())) {
            throw new ParameterException("smsCode", "短信证码错误，请重新获取验证码！");
        }
        return true;
    }

    /**
     * 手机密码登录
     *
     * @param form
     * @return
     */
    @Override
    public TokenResponse phonePasswordLogin(PhonePasswordLoginForm form) {
        //校验图形验证码
        checkBase64Code(form.getClientId(), form.getCode());
        //查询手机号是否注册
        MemberBindPhone memberBindPhone = memberBindPhoneService.getMemberByPhone(form.getPhone());
        //判断是否注册
        if (memberBindPhone == null || Strings.isBlank(memberBindPhone.getPassword())) {
            //抛出账号或者密码错误异常
            throw new BizException(ApiResponseCode.ACCOUNT_PASSWORD_ERROR.getCode(),
                    ApiResponseCode.ACCOUNT_PASSWORD_ERROR.getMessage());
        }
        if (!passwordEncoder.matches(form.getPassword(), memberBindPhone.getPassword())) {
            throw new BizException(ApiResponseCode.ACCOUNT_PASSWORD_ERROR.getCode(),
                    ApiResponseCode.ACCOUNT_PASSWORD_ERROR.getMessage());
        }
        //获取用户信息
        Member member = memberService.get(memberBindPhone.getMemberId());

        //调用loginSuccess方法，返回token
        return loginSuccess(memberBindPhone.getMemberId(), member.getTenantId(), member.getSysRoleIds());

    }

    /**
     * 获取客户端token
     *
     * @param clientId
     * @return
     */
    @Override
    public TokenResponse getClientToken(String clientId) {
        return (TokenResponse) redisTemplate.opsForValue().get(RedisKeyConstant.CLIENT_TOKEN_KEY + clientId);
    }

    /**
     * 手机短信登录
     *
     * @param form
     * @return
     */
    @Override
    public TokenResponse phoneSmsCodeLogin(PhoneSmsCodeLoginForm form) {
        checkSmsCode(form.getPhone(), form.getSmsCode(), SmsCodeTypeEnum.LOGIN.getCode());
        //根据手机号查询信息，判断手机号是否被注册
        MemberBindPhone memberBindPhone = memberBindPhoneService.getMemberByPhone(form.getPhone());
        //手机号未注册
        if (memberBindPhone == null) {
            throw new ParameterException("phone", "该手机号未注册");
        }
        Member member = memberService.get(memberBindPhone.getMemberId());
        return loginSuccess(memberBindPhone.getMemberId(), member.getTenantId(), member.getSysRoleIds());
    }

    /**
     * 登录成功,设置token
     *
     * @param memberId
     * @param tenantId
     * @param sysRoleIds
     * @return
     */

    private TokenResponse loginSuccess(long memberId, long tenantId, String sysRoleIds) {
        try {
            // 创建一个新的AdminDTO实例，用于存储管理员的相关信息
            AdminDTO adminDTO = new AdminDTO();

            // 设置管理员的唯一标识ID
            adminDTO.setId(memberId);
            // 设置管理员所属的租户ID
            adminDTO.setTenantId(tenantId);

            // 将系统角色ID字符串转换为Long类型的集合
            // 使用ObjectMapper的readValue方法进行反序列化
            adminDTO.setSysRoleIds(objectMapper.readValue(sysRoleIds, new TypeReference<Set<Long>>() {
            }));

            // 将管理员信息封装在AdminDTO中，用于生成和管理token
            tokenService.setToken(adminDTO);

//        redisTemplate.opsForValue().set(RedisKeyConstant.CLIENT_TOKEN_KEY + memberId, adminDTO.getToken(), 10, TimeUnit.MINUTES);
            return adminDTO.getToken();
        } catch (Exception ex) {
            throw new BizException("登录失败", ex);
        }
    }

}