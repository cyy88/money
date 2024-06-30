package com.cyy.finance.admin.api.controller;

import com.cyy.commom.dto.ApiResponse;
import com.cyy.commom.dto.TokenResponse;
import com.cyy.finance.biz.dto.form.*;
import com.cyy.finance.biz.service.MemberLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "用户登录模块")
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    @Autowired
    private MemberLoginService memberLoginService;

    /*
    * 用户第一次登录时获取的客户端id*/
    @ApiOperation("获取客户端id")
    @GetMapping("/getClientId")
    public ApiResponse<String > getClientId(){
        String clientId = memberLoginService.getClientId();
        log.info("客户端id：{}", clientId);
        return ApiResponse.success(clientId);
    }

    /*
    * 获取图形验证码*/
    @ApiOperation("获取图形验证码")
    @GetMapping("/getBase64Code")
    //@Valid @ModelAttribute 进行校验规则
    public ApiResponse<String> getBase64Code(@Validated @ModelAttribute GetBase64CodeForm form){
        //返回图形验证码
        return ApiResponse.success(memberLoginService.getBase64Code(form));
    }

    /*
    * 获取短信验证码*/
    @ApiOperation(value = "获取短信验证码")
    @GetMapping(value = "/sendSmsCode")
    public ApiResponse<Void> sendSmsCode(@Validated @ModelAttribute GetSmsCodeForm form) {
        memberLoginService.sendSmsCode(form);
        return ApiResponse.success();
    }

    /*
    * 手机密码登录*/
    @ApiOperation(value = "手机密码登录")
    @PostMapping(value = "/phonePasswordLogin")
    public ApiResponse<TokenResponse> phonePasswordLogin(@Validated @RequestBody PhonePasswordLoginForm form) {
        TokenResponse tokenResponse = memberLoginService.phonePasswordLogin(form);
        return ApiResponse.success(tokenResponse);
    }

    @ApiOperation(value = "手机短信登录")
    @PostMapping(value = "/phoneSmsCodeLogin")
    public ApiResponse<TokenResponse> phoneSmsCodeLogin(@Validated @RequestBody PhoneSmsCodeLoginForm request) {
        TokenResponse tokenResponse = memberLoginService.phoneSmsCodeLogin(request);
        return ApiResponse.success(tokenResponse);
    }

    @ApiOperation(value = "获取客户端token")
    @GetMapping(value = "/getClientToken")
    public ApiResponse<TokenResponse> getClientToken(@Validated @ModelAttribute GetClientTokenForm request) {
        TokenResponse result = memberLoginService.getClientToken(request.getClientId());
        return ApiResponse.success(result);
    }


}
