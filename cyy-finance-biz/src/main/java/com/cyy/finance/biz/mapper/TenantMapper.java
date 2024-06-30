package com.cyy.finance.biz.mapper;

import com.cyy.finance.biz.domain.Tenant;
import com.cyy.mybatis.help.CommonMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TenantMapper extends CommonMapper<Tenant> {
}