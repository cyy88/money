package com.cyy.finance.biz.service;

import com.cyy.commom.dto.TokenResponse;
import com.cyy.finance.biz.dto.form.GetBase64CodeForm;
import com.cyy.finance.biz.dto.form.GetSmsCodeForm;
import com.cyy.finance.biz.dto.form.PhonePasswordLoginForm;
import com.cyy.finance.biz.dto.form.PhoneSmsCodeLoginForm;

public interface MemberLoginService {
    /*
    * 获取客户端id
    * */
    String getClientId();

    /*
    * 获取图形验证码*/
    String getBase64Code(GetBase64CodeForm form);

    /*
    * 获取短信验证码
    * */
    void sendSmsCode(GetSmsCodeForm form);

    /*
    * 校验图形验证码
    * */
    boolean checkBase64Code(String clientId, String code);

    /*
    * 校验短信验证码
    * */
    boolean checkSmsCode(String phone, String smsCode, String code);


    /*
    * 手机密码登录
    * */
    TokenResponse phonePasswordLogin(PhonePasswordLoginForm form);

    /*
    * 获取客户端token
    * */
    TokenResponse getClientToken(String clientId);

    TokenResponse phoneSmsCodeLogin(PhoneSmsCodeLoginForm form);
}
