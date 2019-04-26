package com.chenyanwu.erp.erpframework.dao.importutil;

import java.util.List;
import com.chenyanwu.erp.erpframework.entity.importutil.ErpStudent;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;

/**
* <p>
    * </p>
*
* @author chenyanwu
* @date 2019-04-26 12:37:18
* @version
*/
public interface ErpStudentMapper extends Mapper<ErpStudent>,InsertListMapper<ErpStudent> {


}