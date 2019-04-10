package com.chenyanwu.erp.erpframework.service.impl.rbac;

import com.chenyanwu.erp.erpframework.entity.rbac.ErpRole;
import com.chenyanwu.erp.erpframework.dao.rbac.ErpRoleMapper;
import com.chenyanwu.erp.erpframework.service.rbac.ErpRoleService;
import com.chenyanwu.erp.erpframework.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chenyanwu
 * @date 2019-02-21 17:39:55
 */
@Service
public class ErpRoleServiceImpl extends BaseServiceImpl<ErpRole, Object>
        implements ErpRoleService {

    private final Logger logger = LoggerFactory.getLogger(ErpRoleServiceImpl.class);

    @Autowired
    private ErpRoleMapper erproleMapper;

    @Autowired
    public void setMapper() {
        super.setMapper(erproleMapper);
    }

    @Override
    public List<ErpRole> selectByExample(Object var1) {
        logger.info("当前的条件值为：[{}]" + var1);
        return erproleMapper.selectByExample(var1);
    }
}
