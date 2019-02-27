package com.chenyanwu.erp.erpframework.service.impl.rbac;

import com.chenyanwu.erp.erpframework.entity.dtree.DtreeResponse;
import com.chenyanwu.erp.erpframework.entity.rbac.ErpMenu;
import com.chenyanwu.erp.erpframework.dao.rbac.ErpMenuMapper;
import com.chenyanwu.erp.erpframework.service.dtree.DtreeService;
import com.chenyanwu.erp.erpframework.service.rbac.ErpMenuService;
import com.chenyanwu.erp.erpframework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONObject;

/**
* <p>
    *  服务实现类
    * </p>
*
* @author chenyanwu
* @date 2019-02-27 11:15:03
* @version
*/
@Service
public class ErpMenuServiceImpl extends BaseServiceImpl<ErpMenu, Object>
    implements ErpMenuService {

@Autowired
private ErpMenuMapper erpmenuMapper;

@Autowired
private DtreeService dtreeService;

@Autowired
public void setMapper() {
super.setMapper(erpmenuMapper);
}

    @Override
    public String getTreeMenuList(String roleId) {
        DtreeResponse response = dtreeService.getMenuDtreeResponse(roleId);
        JSONObject obj = JSONObject.fromObject(response);
        return obj.toString();
    }
}
