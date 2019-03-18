package com.chenyanwu.erp.erpframework.dao.rbac;

import java.util.List;
import com.chenyanwu.erp.erpframework.entity.rbac.ErpUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
* <p>
    * </p>
*
* @author chenyanwu
* @date 2019-02-27 11:14:41
* @version
*/
public interface ErpUserMapper extends Mapper<ErpUser>,InsertListMapper<ErpUser> {


    ErpUser findUserByLoginName(@Param("loginName") String loginName);

}