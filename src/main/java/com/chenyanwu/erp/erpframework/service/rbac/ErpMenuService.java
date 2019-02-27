package com.chenyanwu.erp.erpframework.service.rbac;

import com.chenyanwu.erp.erpframework.entity.rbac.ErpMenu;
import com.chenyanwu.erp.erpframework.service.BaseService;

/**
* <p>
    *  服务类
    * </p>
*
* @author chenyanwu
* @date 2019-02-27 11:15:03
* @version
*/
public interface ErpMenuService extends BaseService<ErpMenu, Object> {
    String getTreeMenuList(String roleId);
}
