package com.chenyanwu.erp.erpframework.service.impl.rbac;

import com.chenyanwu.erp.erpframework.entity.dtree.DtreeResponse;
import com.chenyanwu.erp.erpframework.entity.rbac.ErpMenu;
import com.chenyanwu.erp.erpframework.dao.rbac.ErpMenuMapper;
import com.chenyanwu.erp.erpframework.entity.vo.ShowMenu;
import com.chenyanwu.erp.erpframework.service.dtree.DtreeService;
import com.chenyanwu.erp.erpframework.service.rbac.ErpMenuService;
import com.chenyanwu.erp.erpframework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<ShowMenu> getShowMenuByUser(String id) {
        Map<String, Object> map = new HashMap();
        map.put("userId", id);
        map.put("parentId", "0");
        return erpmenuMapper.selectShowMenuByUser(map);
    }
}
