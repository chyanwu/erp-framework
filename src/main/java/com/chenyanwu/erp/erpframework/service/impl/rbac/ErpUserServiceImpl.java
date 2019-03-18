package com.chenyanwu.erp.erpframework.service.impl.rbac;

import com.chenyanwu.erp.erpframework.entity.rbac.ErpUser;
import com.chenyanwu.erp.erpframework.dao.rbac.ErpUserMapper;
import com.chenyanwu.erp.erpframework.service.rbac.ErpUserService;
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
 * @date 2019-02-27 11:14:41
 */
@Service("erpUserService")
public class ErpUserServiceImpl extends BaseServiceImpl<ErpUser, Object>
        implements ErpUserService {

    @Autowired
    private ErpUserMapper erpuserMapper;

    @Autowired
    public void setMapper() {
        super.setMapper(erpuserMapper);
    }

    @Override
    public List<ErpUser> selectByExample(Object var1) {
        return erpuserMapper.selectByExample(var1);
    }

    @Override
    public ErpUser findUserByLoginName(String loginName) {
        return erpuserMapper.findUserByLoginName(loginName);
    }
}
