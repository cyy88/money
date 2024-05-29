package com.cyy.finance.biz.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.cyy.commom.config.RedisTemplateDefaultConfig;
import com.cyy.finance.biz.constant.RedisKeyConstant;
import com.cyy.finance.biz.dto.form.GetBase64CodeForm;
import com.cyy.finance.biz.service.MemberLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor  //构造参数注解
public class MemberLoginServiceImpl implements MemberLoginService {

    @Resource
    private RedisTemplate<String,String> redisTemplate;

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
     * 获取图形验证码
     *
     * @param form
     * @return
     */
    @Override
    public String getBase64Code(GetBase64CodeForm form) {
        //hutool坐标下的类，用于生成图形验证码
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(300,192,5,1000);
        //把图形验证码里面的内容读出来
        String code =lineCaptcha.getCode();
        //将code保存在redis中,设置超时时间15分钟，15分钟之后，redis会自动删除
        redisTemplate.opsForValue().set(RedisKeyConstant.GRAPHIC_VERIFICATION_CODE +form.getClientId(),code,15, TimeUnit.MINUTES);

        return lineCaptcha.getImageBase64();  //返回图形验证码
    }
}
