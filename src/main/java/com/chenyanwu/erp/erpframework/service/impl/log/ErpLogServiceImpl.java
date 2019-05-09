package com.chenyanwu.erp.erpframework.service.impl.log;

import com.chenyanwu.erp.erpframework.entity.log.ErpLog;
import com.chenyanwu.erp.erpframework.dao.log.ErpLogMapper;
import com.chenyanwu.erp.erpframework.service.log.ErpLogService;
import com.chenyanwu.erp.erpframework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
* <p>
    *  服务实现类
    * </p>
*
* @author chenyanwu
* @date 2019-05-06 15:29:27
* @version
*/
@Service
public class ErpLogServiceImpl extends BaseServiceImpl<ErpLog, Object>
    implements ErpLogService {

@Autowired
private ErpLogMapper erplogMapper;

@Autowired
public void setMapper() {
super.setMapper(erplogMapper);
}
}
