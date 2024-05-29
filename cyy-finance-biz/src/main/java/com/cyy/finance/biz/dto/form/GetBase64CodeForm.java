package com.cyy.finance.biz.dto.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class GetBase64CodeForm {

    @ApiModelProperty("客户端id")
    @NotNull(message = "客户端id不能为空")
    @Pattern(regexp = "^[0-9A-Za-z]{6,32}$", message = "clientId非法")
    private String clientId;
}
