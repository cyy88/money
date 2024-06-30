package com.cyy.finance.biz.dto.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateRoleForm {
    @ApiModelProperty(value = "角色id")
    @NotNull
    @Range(min = 1)
    private Integer id;

    @ApiModelProperty(value = "角色名称")
    @NotBlank
    private String roleName;
}
