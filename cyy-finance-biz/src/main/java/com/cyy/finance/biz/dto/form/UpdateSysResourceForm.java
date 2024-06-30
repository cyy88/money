package com.cyy.finance.biz.dto.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 修改资源
 */
@Data
public class UpdateSysResourceForm {
    @ApiModelProperty(value = "资源id")
    @NotNull
    @Range(min = 1, max = Integer.MAX_VALUE)
    private Integer id;

    @ApiModelProperty(value = "资源名称")
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @ApiModelProperty(value = "资源路径")
    @NotBlank
    @Size(min = 1, max = 50)
    private String path;
}
