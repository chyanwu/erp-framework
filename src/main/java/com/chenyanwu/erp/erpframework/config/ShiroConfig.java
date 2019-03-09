package com.chenyanwu.erp.erpframework.config;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Auther: chenyanwu
 * @Date: 2019/3/8 13:08
 * @Description:
 * @Version 1.0
 */
@Configuration
public class ShiroConfig {

    private Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    // 权限管理，配置主要的Realm的管理认证
    @Bean
    public SecurityManager securityManager(@Qualifier("myShiroRealm")MyShiroRealm myShiroRealm) {
        logger.info("- - - - - - -shiro开始加载- - - - - - ");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm);
        return securityManager;
    }

    // Filter工厂，处理拦截资源文件问题，设置对应的过滤条件和跳转条件
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 登录
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 首页
        shiroFilterFactoryBean.setSuccessUrl("/index");
//        Map<String, Filter> map = new HashMap<>();
//        map.put("authc", new CaptchaFormAuthenticationFilter());
//        shiroFilterFactoryBean.setFilters(map);
        // 配置访问权限（拦截器）
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/assets/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/layui_ext/**", "anon");
        // 登录调整匿名访问
        filterChainDefinitionMap.put("/login/main","anon");
        // 验证码匿名访问
        filterChainDefinitionMap.put("/genCaptcha","anon");
        //对所有用户认证
        filterChainDefinitionMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    // 加入注解的使用，不加入这个注解不生效
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }

    /**
     * Shiro生命周期处理器
     * @return
     */
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
//        return new LifecycleBeanPostProcessor();
//    }

    /**
     * 自动创建代理
     * @return
     */
//    @Bean
//    @DependsOn({"lifecycleBeanPostProcessor"})
//    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
//        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        advisorAutoProxyCreator.setProxyTargetClass(true);
//        return advisorAutoProxyCreator;
//    }

//    @Bean
//    public FilterRegistrationBean delegatingFilterProxy(){
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
//        proxy.setTargetFilterLifecycle(true);
//        proxy.setTargetBeanName("shiroFilter");
//        filterRegistrationBean.setFilter(proxy);
//        filterRegistrationBean.setDispatcherTypes(DispatcherType.ERROR,DispatcherType.REQUEST,DispatcherType.FORWARD,DispatcherType.INCLUDE);
//        return filterRegistrationBean;
//    }
//
//    @Bean
//    public SimpleCookie rememberMeCookie(){
//        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
//        SimpleCookie cookie = new SimpleCookie("rememberMe");
//        cookie.setHttpOnly(true);
//        //记住我有效期长达30天
//        cookie.setMaxAge(2592000);
//        return cookie;
//    }
//
//    @Bean
//    public CookieRememberMeManager rememberMeManager(){
//        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
//        rememberMeManager.setCookie(rememberMeCookie());
//        rememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
//        return rememberMeManager;
//    }
}
