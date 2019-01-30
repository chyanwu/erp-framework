package com.chenyanwu.erp.erpservice.service.impl.erp;

import com.chenyanwu.erp.erpdao.entity.erp.User;
import com.chenyanwu.erp.erpdao.dao.erp.UserMapper;
import com.chenyanwu.erp.erpservice.service.erp.UserService;
import com.chenyanwu.erp.erpservice.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Ken
 * @date 2019-01-15 19:17:41
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Object>
        implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    public void setMapper() {
        super.setMapper(userMapper);
    }
}
