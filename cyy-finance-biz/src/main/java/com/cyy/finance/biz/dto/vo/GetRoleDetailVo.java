package com.cyy.finance.biz.dto.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetRoleDetailVo {
    @ApiModelProperty(value = "角色id")
    private Integer id;
    @ApiModelProperty(value = "角色名称")
    private String roleName;
}
