package com.cyy.finance.biz.dto;

import com.cyy.commom.dto.BaseUserInfoDTO;
import lombok.Data;

import java.util.List;


@Data
public class AdminDTO extends BaseUserInfoDTO {
    private List<Integer> permissions;
}
