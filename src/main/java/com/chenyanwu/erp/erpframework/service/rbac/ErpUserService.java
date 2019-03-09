package com.chenyanwu.erp.erpframework.service.rbac;

import com.chenyanwu.erp.erpframework.entity.rbac.ErpUser;
import com.chenyanwu.erp.erpframework.service.BaseService;

import java.util.List;

/**
* <p>
    *  服务类
    * </p>
*
* @author chenyanwu
* @date 2019-02-27 11:14:41
* @version
*/
public interface ErpUserService extends BaseService<ErpUser, Object> {
    List<ErpUser> selectByExample(Object var1);

    /**
     * 通过登录名获取用户信息
     * @param loginName
     * @return
     */
    ErpUser findUserByLoginName(String loginName);
}
