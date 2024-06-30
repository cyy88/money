package com.cyy.finance.biz.dto.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 删除系统角色
 *
 */
@Data
public class DelSysRoleForm {

    /**
     * 角色id
     */
    @NotNull
    private Integer id;
}