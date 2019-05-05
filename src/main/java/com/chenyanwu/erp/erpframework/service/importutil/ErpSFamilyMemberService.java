package com.chenyanwu.erp.erpframework.service.importutil;

import com.chenyanwu.erp.erpframework.entity.importutil.ErpSFamilyMember;
import com.chenyanwu.erp.erpframework.service.BaseService;

import java.util.List;

/**
* <p>
    *  服务类
    * </p>
*
* @author chenyanwu
* @date 2019-04-29 11:37:43
* @version
*/
public interface ErpSFamilyMemberService extends BaseService<ErpSFamilyMember, Object> {

    List<ErpSFamilyMember> findFamilyMemberByStuId(String stuId);

    int insertList(List<ErpSFamilyMember> list);
}
