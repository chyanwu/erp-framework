package com.chenyanwu.erp.erpframework.service.rbac;

import com.chenyanwu.erp.erpframework.entity.rbac.ErpRoleUser;
import com.chenyanwu.erp.erpframework.service.BaseService;

import java.util.List;

/**
* <p>
    *  服务类
    * </p>
*
* @author chenyanwu
* @date 2019-02-27 17:28:29
* @version
*/
public interface ErpRoleUserService extends BaseService<ErpRoleUser, Object> {
    List<ErpRoleUser> selectByExample(Object var1);

    int insertList(List<? extends ErpRoleUser> var1);
}
