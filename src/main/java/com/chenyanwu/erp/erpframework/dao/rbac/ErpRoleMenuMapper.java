package com.chenyanwu.erp.erpframework.dao.rbac;

import java.util.List;
import com.chenyanwu.erp.erpframework.entity.rbac.ErpRoleMenu;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;

/**
* <p>
    * </p>
*
* @author chenyanwu
* @date 2019-02-27 17:04:18
* @version
*/
public interface ErpRoleMenuMapper extends Mapper<ErpRoleMenu>,InsertListMapper<ErpRoleMenu> {


}