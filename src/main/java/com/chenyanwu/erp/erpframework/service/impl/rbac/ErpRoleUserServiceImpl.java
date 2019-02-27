package com.chenyanwu.erp.erpframework.service.impl.rbac;

import com.chenyanwu.erp.erpframework.entity.rbac.ErpRoleUser;
import com.chenyanwu.erp.erpframework.dao.rbac.ErpRoleUserMapper;
import com.chenyanwu.erp.erpframework.service.rbac.ErpRoleUserService;
import com.chenyanwu.erp.erpframework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
* <p>
    *  服务实现类
    * </p>
*
* @author chenyanwu
* @date 2019-02-27 17:28:29
* @version
*/
@Service
public class ErpRoleUserServiceImpl extends BaseServiceImpl<ErpRoleUser, Object>
    implements ErpRoleUserService {

@Autowired
private ErpRoleUserMapper erproleuserMapper;

@Autowired
public void setMapper() {
super.setMapper(erproleuserMapper);
}

    @Override
    public List<ErpRoleUser> selectByExample(Object var1) {
        return erproleuserMapper.selectByExample(var1);
    }

    @Override
    public int insertList(List<? extends ErpRoleUser> var1) {
        return erproleuserMapper.insertList(var1);
    }
}
