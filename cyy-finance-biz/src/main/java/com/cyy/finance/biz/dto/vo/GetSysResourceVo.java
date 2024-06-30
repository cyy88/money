package com.cyy.finance.biz.dto.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取资源
 */
@Data
public class GetSysResourceVo {
    @ApiModelProperty(value = "资源id")
    private Integer id;

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "上级pid")
    private Integer pid;

    @ApiModelProperty(value = "上级资源名称")
    private String parentName;

    @ApiModelProperty(value = "资源路径")
    private String path;
}
