package com.chenyanwu.erp.erpframework.entity;

import com.chenyanwu.erp.erpframework.common.util.JsonUtil;
import com.chenyanwu.erp.erpframework.config.MyShiroRealm;
import org.apache.shiro.SecurityUtils;

/**
 * @Auther: chenyanwu
 * @Date: 2019/3/8 16:54
 * @Description:
 * @Version 1.0
 */
public class MySysUser {
    /**
     * 取出Shiro中的当前用户LoginName.
     */
    public static String icon() {
        return ShiroUser().getIcon();
    }

    public static String id() {
        return ShiroUser().getId();
    }

    public static String loginName() {
        return ShiroUser().getLoginName();
    }

    public static String name(){
        return ShiroUser().getName();
    }

    public static MyShiroRealm.ShiroUser ShiroUser() {
        MyShiroRealm.ShiroUser user= (MyShiroRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return user;

        /**
         * 这个方式解决了拦截时候，从SecurityUtils.getSubject().getPrincipal()获取的对象为null，而强行转换而报错，但是返回的数据导致拦截器return false，而不能继续执行
         * 目前验证码显示不出来，从而问题依旧没有解决
         */
//        Object obj = SecurityUtils.getSubject().getPrincipal();
//        MyShiroRealm.ShiroUser user = new MyShiroRealm.ShiroUser();
//        if(obj==null){
//            return new MyShiroRealm.ShiroUser();
//        }
//
//        if(obj instanceof MyShiroRealm.ShiroUser) {
//            user = (MyShiroRealm.ShiroUser) obj;
//        } else {
//            System.out.print(obj.getClass()+"1111");
//            user = JsonUtil.json2Bean(JsonUtil.bean2Json(obj), MyShiroRealm.ShiroUser.class);
//        }
//        return user;
    }
}
