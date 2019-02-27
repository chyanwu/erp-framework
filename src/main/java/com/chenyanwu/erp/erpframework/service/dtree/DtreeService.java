package com.chenyanwu.erp.erpframework.service.dtree;

import com.chenyanwu.erp.erpframework.entity.dtree.Dtree;
import com.chenyanwu.erp.erpframework.entity.dtree.DtreeResponse;
import com.chenyanwu.erp.erpframework.service.BaseService;

/**
 * @Auther: chenyanwu
 * @Date: 2019/2/27 15:09
 * @Description:
 * @Version 1.0
 */
public interface DtreeService extends BaseService<Dtree, Object> {
    DtreeResponse getMenuDtreeResponse(String roleId);
}
