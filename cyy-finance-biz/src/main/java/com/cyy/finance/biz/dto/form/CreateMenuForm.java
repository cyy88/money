package com.cyy.finance.biz.dto.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class CreateMenuForm {

    /**
     * 父级菜单
     */
    @ApiModelProperty(value = "父级菜单")
    @NotNull
    @Min(value = 0)
    private Integer pid;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    @NotBlank
    @Size(max = 200)
    private String name;

    /**
     * 菜单路由
     */
    @ApiModelProperty(value = "菜单路由")
    @NotBlank
    @Size(max = 200)
    private String path;

    /**
     * 菜单组件
     */
    @ApiModelProperty(value = "菜单组件")
    @Size(max = 200)
    private String component;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    @Size(max = 20)
    private String icon;

    /**
     * 是否使用布局
     */
    @ApiModelProperty(value = "是否使用布局")
    @NotNull
    private Boolean layout;

    /**
     * 是否隐藏菜单
     */
    @ApiModelProperty(value = "是否隐藏菜单")
    @NotNull
    private Boolean hideInMenu;

    /**
     * 重定向地址
     */
    @ApiModelProperty(value = "重定向地址")
    private String redirect;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    @NotNull
    private Integer sort;
}