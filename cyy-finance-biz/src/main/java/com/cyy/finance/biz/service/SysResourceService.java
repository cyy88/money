package com.cyy.finance.biz.service;



import com.cyy.finance.biz.domain.SysResource;
import com.cyy.finance.biz.dto.form.CreateSysResourceForm;
import com.cyy.finance.biz.dto.form.DelSysResourceForm;
import com.cyy.finance.biz.dto.form.ListSysResourceForm;
import com.cyy.finance.biz.dto.form.UpdateSysResourceForm;
import com.cyy.finance.biz.dto.vo.GetSysResourceVo;
import com.cyy.finance.biz.dto.vo.ListSysResourceVo;

import java.util.List;

public interface SysResourceService {
    /**
     * 创建资源
     *
     * @param form
     * @return
     */
    boolean create(CreateSysResourceForm form);

    /**
     * 修改资源
     *
     * @param form
     * @return
     */
    boolean update(UpdateSysResourceForm form);

    /**
     * 删除资源
     *
     * @param form
     * @return
     */
    boolean del(DelSysResourceForm form);


    /**
     * 获取资源
     *
     * @param id
     * @return
     */
    GetSysResourceVo get(int id);

    /**
     * 获取资源列表
     *
     * @param form
     * @return
     */
    List<ListSysResourceVo> list(ListSysResourceForm form);

    /**
     * 根据id列表查询数量
     *
     * @param ids
     * @return
     */
    int countByIds(List<Integer> ids);

    /**
     * 根据id查询资源列表
     *
     * @param ids
     * @return
     */
    List<SysResource> listByIds(List<Integer> ids);
}
