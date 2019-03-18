package com.chenyanwu.erp.erpframework.service.impl.rbac;

import com.chenyanwu.erp.erpframework.entity.rbac.ErpRoleMenu;
import com.chenyanwu.erp.erpframework.dao.rbac.ErpRoleMenuMapper;
import com.chenyanwu.erp.erpframework.service.rbac.ErpRoleMenuService;
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
 * @date 2019-02-27 17:04:18
 */
@Service
public class ErpRoleMenuServiceImpl extends BaseServiceImpl<ErpRoleMenu, Object>
        implements ErpRoleMenuService {

    @Autowired
    private ErpRoleMenuMapper erprolemenuMapper;

    @Autowired
    public void setMapper() {
        super.setMapper(erprolemenuMapper);
    }

    @Override
    public int insertList(List<? extends ErpRoleMenu> var1) {
        return erprolemenuMapper.insertList(var1);
    }
}
