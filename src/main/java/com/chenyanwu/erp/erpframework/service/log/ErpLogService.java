package com.chenyanwu.erp.erpframework.service.log;

import com.chenyanwu.erp.erpframework.entity.log.ErpLog;
import com.chenyanwu.erp.erpframework.service.BaseService;

import java.util.List;

/**
* <p>
    *  服务类
    * </p>
*
* @author chenyanwu
* @date 2019-05-06 15:29:27
* @version
*/
public interface ErpLogService extends BaseService<ErpLog, Object> {
    List<ErpLog> selectByExample(Object var1);

    Integer softDeleteByID(String id);
}
