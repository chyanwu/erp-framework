package com.chenyanwu.erp.erpframework.service.rbac;

import com.chenyanwu.erp.erpframework.entity.rbac.ErpRole;
import com.chenyanwu.erp.erpframework.service.BaseService;

import java.util.List;

/**
* <p>
    *  服务类
    * </p>
*
* @author chenyanwu
* @date 2019-02-21 17:39:55
* @version
*/
public interface ErpRoleService extends BaseService<ErpRole, Object> {
    List<ErpRole> selectByExample(Object var1);
}
