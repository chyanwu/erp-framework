package com.chenyanwu.erp.erpframework.dao.log;

import java.util.List;
import com.chenyanwu.erp.erpframework.entity.log.ErpLog;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;

/**
* <p>
    * </p>
*
* @author chenyanwu
* @date 2019-05-06 15:29:27
* @version
*/
public interface ErpLogMapper extends Mapper<ErpLog>,InsertListMapper<ErpLog> {


}