package com.cyy.finance.biz.dto.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ListTreeSelectMenuVo {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "id")
    private String value;
    @ApiModelProperty(value = "菜单名称")
    private String title;
    @ApiModelProperty(value = "子菜单")
    private List<ListTreeSelectMenuVo> children;
}
