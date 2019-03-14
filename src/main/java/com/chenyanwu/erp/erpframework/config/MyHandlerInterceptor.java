package com.chenyanwu.erp.erpframework.config;

import com.chenyanwu.erp.erpframework.common.util.StringUtils;
import com.chenyanwu.erp.erpframework.entity.MySysUser;
import com.chenyanwu.erp.erpframework.entity.rbac.ErpUser;
import com.chenyanwu.erp.erpframework.service.rbac.ErpUserService;
import org.slf4j.MDC;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: chenyanwu
 * @Date: 2019/3/8 16:46
 * @Description:
 * @Version 1.0
 */
@Component
public class MyHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private ErpUserService erpUserService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(erpUserService == null) {
            System.out.println("erpUserService is null!!!");
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            erpUserService = (ErpUserService) factory.getBean("erpUserService");
        }
        ErpUser user = erpUserService.findUserByLoginName(MySysUser.loginName());
        if(user != null) {
            // 通过拦截器，将登录用户信息存储，便于登录后展示
            request.setAttribute("currentUser", user);
            // 将登录用户名写入MDC中，方便后面mybatis拦截器中对createby  updateby字段赋值
            MDC.put("userid", MySysUser.loginName());
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
