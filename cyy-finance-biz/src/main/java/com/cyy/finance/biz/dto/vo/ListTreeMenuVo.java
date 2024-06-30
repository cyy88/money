package com.cyy.finance.biz.dto.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ListTreeMenuVo {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "主键")
    private String key;
    @ApiModelProperty(value = "菜单名称")
    private String title;
    @ApiModelProperty(value = "icon")
    private String icon;
    @ApiModelProperty(value = "默认是否选中")
    private Boolean checked;
    @ApiModelProperty(value = "父节点id")
    private Integer pid;
    @ApiModelProperty(value = "菜单路由")
    private String path;
    @ApiModelProperty(value = "顺序")
    private Integer sort;
    @ApiModelProperty(value = "是否隐藏菜单")
    private Boolean hideInMenu;
    @ApiModelProperty(value = "子菜单")
    private List<ListTreeMenuVo> children;
}
