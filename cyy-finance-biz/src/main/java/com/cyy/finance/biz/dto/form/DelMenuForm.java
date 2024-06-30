package com.cyy.finance.biz.dto.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 删除菜单
 */
@Data
public class DelMenuForm {
    @ApiModelProperty(value = "菜单id")
    @NotNull
    @Min(value = 1)
    private Integer id;
}