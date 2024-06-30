package com.cyy.finance.biz.dto.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SaveSendSmsCodeTemplateConfigForm {
    @ApiModelProperty(value = "配置key")
    @NotBlank
    @Pattern(regexp = "^REG|LOGIN|UPDATE_PHONE$",message = "短信验证码类型非法")
    private String configKey;

    @ApiModelProperty(value = "配置名称")
    @NotBlank
    private String configName;

    @ApiModelProperty(value = "短信签名")
    @NotBlank
    private String signName;

    @ApiModelProperty(value = "短信模板编号")
    @NotBlank
    private String templateCode;
}