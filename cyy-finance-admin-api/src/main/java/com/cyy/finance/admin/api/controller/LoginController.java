package com.cyy.finance.admin.api.controller;

import com.cyy.commom.dto.ApiResponse;
import com.cyy.finance.biz.dto.form.GetBase64CodeForm;
import com.cyy.finance.biz.service.MemberLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ApiResponse.success(clientId);
    }

    /*
    * 获取图形验证码*/
    @ApiOperation("获取图形验证码")
    @GetMapping("/getBase64Code")
    //@Valid @ModelAttribute 进行校验规则
    public ApiResponse<String> getBase64Code(@Valid @ModelAttribute GetBase64CodeForm form){
        //返回图形验证码
        return ApiResponse.success(memberLoginService.getBase64Code(form));
    }

}
