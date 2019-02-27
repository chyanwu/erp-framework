package com.chenyanwu.erp.erpframework.service.impl.dtree;

import com.chenyanwu.erp.erpframework.dao.rbac.ErpMenuMapper;
import com.chenyanwu.erp.erpframework.entity.dtree.CheckArr;
import com.chenyanwu.erp.erpframework.entity.dtree.Dtree;
import com.chenyanwu.erp.erpframework.entity.dtree.DtreeResponse;
import com.chenyanwu.erp.erpframework.entity.dtree.Status;
import com.chenyanwu.erp.erpframework.entity.rbac.ErpMenu;
import com.chenyanwu.erp.erpframework.service.dtree.DtreeService;
import com.chenyanwu.erp.erpframework.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
//import javax.annotation.Nullable;

/**
 * @Auther: chenyanwu
 * @Date: 2019/2/27 15:09
 * @Description:
 *  * 提供菜单树{@link DtreeService#getMenuDtreeResponse(String roleId)}；<br>
 *  *     如果传入roleId，返回的菜单信息包含该角色已获取的菜单信息，dtree中使用checkbar时可以看到 <br>
 * @Version 1.0
 */
@Service
public class DtreeServiceImpl extends BaseServiceImpl<Dtree, Object> implements DtreeService {

    @Autowired
    private ErpMenuMapper erpMenuMapper;

    @Override
    public DtreeResponse getMenuDtreeResponse(String roleId) {
        List<Dtree> menuList = selectTreeMenuList(roleId);
        DtreeResponse response = new DtreeResponse();
        response.setCode(200);
        response.setMsg("获取配置菜单成功");
        response.setData(menuList);
        response.setStatus(new Status());
        return response;
    }

    private List<Dtree> selectTreeMenuList(String roleId) {
        List<Dtree> origin = erpMenuMapper.getSysRoleTreeMenus(null);
        List<ErpMenu> menuList = erpMenuMapper.selectAll();

        setBasicData(origin, menuList);

        if (!StringUtil.isEmpty(roleId)) {
            setCheckArrayStatus(roleId, origin);
        }

        if (origin != null && origin.size() > 0) {
            return getTargetList(origin);
        }
        return null;
    }

    private void setCheckArrayStatus(String roleId, List<Dtree> origin) {
        List<Dtree> roleMenus = erpMenuMapper.getSysRoleTreeMenus(roleId);
        for (Dtree item : roleMenus) {
            for (Dtree oItem : origin) {
                if (item.getId().equals(oItem.getId())) {
                    List list = new ArrayList<CheckArr>();
                    list.add(new CheckArr("0", CheckArr.CHECKED));
                    oItem.setCheckArr(list);
                }
            }
        }
    }

    private void setBasicData(List<Dtree> origin, List<ErpMenu> menuList) {
        for (Dtree item : origin) {
            for (ErpMenu menu : menuList) {
                if (item.getId().equals(menu.getId())) {
                    item.setBasicData(menu);
                }
            }
        }
    }

    /**
     * @param origin
     * @return
     */
    private List<Dtree> getTargetList(List<Dtree> origin) {
        List<Dtree> rootLevel = new ArrayList<>();
        List<Dtree> otherLevel = new ArrayList<>();

        for (int i = 0; i < origin.size(); i++) {
            Dtree item = origin.get(i);
            if ("1".equals(item.getLevel())) {
                rootLevel.add(item);
            } else {
                otherLevel.add(item);
            }
        }

        for (int i = 0; i < rootLevel.size(); i++) {
            setUpData(rootLevel.get(i), otherLevel);
        }
        return rootLevel;
    }

    private void setUpData(Dtree root, List<Dtree> dtreeList) {
        for (int i = 0; i < dtreeList.size(); i++) {
            String rootId = root.getId();
            Dtree item = dtreeList.get(i);
            String parentId = item.getParentId();
            if (rootId.equals(parentId)) {
                root.addChildren(item);
                setUpData(item, dtreeList);
            }
        }

    }
}
