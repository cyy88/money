package com.cyy.finance.biz.dto.form;

import com.cyy.commom.dto.PageHelperRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ListRoleForm extends PageHelperRequest {
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 是否禁用；true：禁用；false：启用
     */
    @ApiModelProperty(value = "是否禁用；true：禁用；false：启用")
    private Boolean disable;
}
