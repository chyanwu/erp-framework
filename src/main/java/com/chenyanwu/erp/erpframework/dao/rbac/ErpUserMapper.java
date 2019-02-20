package com.chenyanwu.erp.erpframework.dao.rbac;

import java.util.List;
import com.chenyanwu.erp.erpframework.entity.rbac.ErpUser;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
* <p>
    * </p>
*
* @author chenyanwu
* @date 2019-02-20 11:17:03
* @version
*/
@org.apache.ibatis.annotations.Mapper
public interface ErpUserMapper extends Mapper<ErpUser>,InsertListMapper<ErpUser> {


}