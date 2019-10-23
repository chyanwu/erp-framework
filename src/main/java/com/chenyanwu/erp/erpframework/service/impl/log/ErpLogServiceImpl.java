package com.chenyanwu.erp.erpframework.service.impl.log;

import com.chenyanwu.erp.erpframework.annotation.Log;
import com.chenyanwu.erp.erpframework.entity.log.ErpLog;
import com.chenyanwu.erp.erpframework.dao.log.ErpLogMapper;
import com.chenyanwu.erp.erpframework.service.log.ErpLogService;
import com.chenyanwu.erp.erpframework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chenyanwu
 * @date 2019-05-06 15:29:27
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

    @Override
    public List<ErpLog> selectByExample(Object var1) {
        return erplogMapper.selectByExample(var1);
    }

    @Override
    public Integer softDeleteByID(String id) {
        ErpLog erpLog = erplogMapper.selectByPrimaryKey(id);
        erpLog.setIsDeleted(1);
        return erplogMapper.updateByPrimaryKey(erpLog);
    }
}
