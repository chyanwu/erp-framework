package com.chenyanwu.erp.erpframework.service.rbac;

import com.chenyanwu.erp.erpframework.entity.rbac.ErpRoleMenu;
import com.chenyanwu.erp.erpframework.service.BaseService;

import java.util.List;

/**
* <p>
    *  服务类
    * </p>
*
* @author chenyanwu
* @date 2019-02-27 17:04:18
* @version
*/
public interface ErpRoleMenuService extends BaseService<ErpRoleMenu, Object> {
    int insertList(List<? extends ErpRoleMenu> var1);
}
