package com.cyy.finance.biz.dto.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 获取资源
 */
@Data
public class ListSysResourceVo {
    @ApiModelProperty(value = "资源id")
    private Integer id;

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "上级pid")
    private Integer pid;

    @ApiModelProperty(value = "资源路径")
    private String path;

    @ApiModelProperty(value = "下级资源")
    private List<ListSysResourceVo> children;
}
