package com.cyy.finance.biz.dto.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetMenuByIdVo {
    /**
     * 
     */
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 父级菜单
     */
    @ApiModelProperty(value = "父级菜单id")
    private Integer pid;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String name;

    /**
     * 菜单路由
     */
    @ApiModelProperty(value = "菜单路由")
    private String path;

    /**
     * 菜单组件
     */
    @ApiModelProperty(value = "菜单组件")
    private String component;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 是否使用布局
     */
    @ApiModelProperty(value = "是否使用布局")
    private Boolean layout;

    /**
     * 是否隐藏菜单
     */
    @ApiModelProperty(value = "是否隐藏菜单")
    private Boolean hideInMenu;

    /**
     * 重定向地址
     */
    @ApiModelProperty(value = "重定向地址")
    private String redirect;

    /**
     * 访问权限
     */
    @ApiModelProperty(value = "访问权限")
    private String access;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 是否禁用
     */
    @ApiModelProperty(value = "是否禁用")
    private Boolean disable;
}