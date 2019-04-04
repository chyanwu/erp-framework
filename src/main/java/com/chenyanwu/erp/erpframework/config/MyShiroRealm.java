package com.chenyanwu.erp.erpframework.config;

import com.chenyanwu.erp.erpframework.common.Constants;
import com.chenyanwu.erp.erpframework.common.util.Encodes;
import com.chenyanwu.erp.erpframework.common.util.StringUtils;
import com.chenyanwu.erp.erpframework.entity.rbac.ErpMenu;
import com.chenyanwu.erp.erpframework.entity.rbac.ErpRole;
import com.chenyanwu.erp.erpframework.entity.rbac.ErpUser;
import com.chenyanwu.erp.erpframework.service.rbac.ErpUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: chenyanwu
 * @Date: 2019/3/7 15:52
 * @Description:
 * @Version 1.0
 */
@Component(value = "myShiroRealm")
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    ErpUserService erpUserService;

    // 登录认证
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        // 当前登录信息
        ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
        // 查询当前用户
        ErpUser user = erpUserService.findUserByLoginName(shiroUser.getLoginName());
        // 添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        List<ErpRole> roles = user.getRoles();
        for(ErpRole role: roles) {
            if(StringUtils.isNotBlank(role.getName())) {
                simpleAuthorizationInfo.addRole(role.getName());
            }
        }
        List<ErpMenu> menus = user.getMenus();
        for(ErpMenu menu: menus) {
            if(StringUtils.isNotBlank(menu.getPermission())) {
                simpleAuthorizationInfo.addRole(menu.getPermission());
            }
        }
        return simpleAuthorizationInfo;
    }

    // 授权 (在Post请求的时候，先进行认证，然后在请求)
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取用户信息
        String username = authenticationToken.getPrincipal().toString();
        ErpUser user = erpUserService.findUserByLoginName(username);

        if (user == null) {
            //没找到帐号
            throw new UnknownAccountException();
        }
        if (Boolean.TRUE.equals(user.getLocked() > 0)) {
            //帐号锁定
            throw new LockedAccountException();
        }
        byte[] salt = Encodes.decodeHex(user.getSalt());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                // 用户信息
                new ShiroUser(user.getId(), user.getLoginName(),
                        user.getName()),
                //密码
                user.getPassword(),
                // 盐值
                ByteSource.Util.bytes(salt),
                //realm name
                getName());
        return authenticationInfo;
    }

    /**
     * 重写shiro的密码验证，让shiro用我自己的验证.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Constants.HASH_ALGORITHM);
        matcher.setHashIterations(Constants.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }

    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser implements Serializable {
        private String id;
        private String loginName;
        private String name;
        private String icon;

        public ShiroUser() {

        }

        public ShiroUser(String id, String loginName, String name) {
            this.id = id;
            this.loginName = loginName;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
