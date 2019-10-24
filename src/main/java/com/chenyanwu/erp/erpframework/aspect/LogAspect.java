package com.chenyanwu.erp.erpframework.aspect;

import cn.hutool.core.util.IdUtil;
import com.chenyanwu.erp.erpframework.annotation.Log;
import com.chenyanwu.erp.erpframework.common.util.JsonUtil;
import com.chenyanwu.erp.erpframework.common.util.ToolUtil;
import com.chenyanwu.erp.erpframework.entity.MySysUser;
import com.chenyanwu.erp.erpframework.entity.log.ErpLog;
import com.chenyanwu.erp.erpframework.service.log.ErpLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;


/**
 * @Auther: chenyanwu
 * @Date: 2019/5/6 09:36
 * @Description:
 * @Version 1.0
 */
@Aspect
@Order(5)
@Component
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private ErpLogService logService;

    @Autowired
    ObjectMapper objectMapper;

    private ThreadLocal<Date> startTime = new ThreadLocal<Date>();

    @Pointcut("@annotation(com.chenyanwu.erp.erpframework.annotation.Log)")
    public void pointcut() {

    }

    /**
     * 前置通知，在Controller层操作前拦截
     *
     * @param joinPoint 切入点
     */
    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        // 获取当前调用时间
        startTime.set(new Date());
    }

    /**
     * 正常情况返回
     *
     * @param joinPoint 切入点
     * @param rvt       正常结果
     */
    @AfterReturning(pointcut = "pointcut()", returning = "rvt")
    public void doAfter(JoinPoint joinPoint, Object rvt) throws Exception {
        handleLog(joinPoint, null, rvt);
    }

    /**
     * 异常信息拦截
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e) throws Exception {
        handleLog(joinPoint, e, null);
    }

    @Async
    private void handleLog(final JoinPoint joinPoint, final Exception e, Object rvt) throws Exception{
        // 获得注解
        Method method = getMethod(joinPoint);
        Log log = getAnnotationLog(method);
        if (log == null) {
            return;
        }
        Date now = new Date();
        // 操作数据库日志表
        ErpLog erpLog = new ErpLog();
        erpLog.setErrorCode(0);
        erpLog.setIsDeleted(0);
        // 请求信息
        HttpServletRequest request = ToolUtil.getRequest();
        erpLog.setType(ToolUtil.isAjaxRequest(request) ? "Ajax请求" : "普通请求");
        erpLog.setTitle(log.value());
        erpLog.setHost(ToolUtil.getClientIp(request));
        erpLog.setUri(request.getRequestURI().toString());
//        erpLog.setHeader(request.getHeader(HttpHeaders.USER_AGENT));
        erpLog.setHttpMethod(request.getMethod());
        erpLog.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u
                = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            StringBuilder params = new StringBuilder();
            params = handleParams(params, args, Arrays.asList(paramNames));
            erpLog.setParams(params.toString());
        }
        String retString = JsonUtil.bean2Json(rvt);
        erpLog.setResponseValue(retString.length() > 5000 ? JsonUtil.bean2Json("请求参数数据过长不与显示") : retString);
        if (e != null) {
            erpLog.setErrorCode(1);
            erpLog.setErrorMessage(e.getMessage());
        }
        Date stime = startTime.get();
        erpLog.setStartTime(stime);
        erpLog.setEndTime(now);
        erpLog.setExecuteTime(now.getTime() - stime.getTime());
        erpLog.setUsername(MySysUser.loginName());
        HashMap<String, String> browserMap = ToolUtil.getOsAndBrowserInfo(request);
        erpLog.setOperatingSystem(browserMap.get("os"));
        erpLog.setBrower(browserMap.get("browser"));
        erpLog.setId(IdUtil.simpleUUID());
        logService.insertSelective(erpLog);
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(Method method) {
        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    private Method getMethod(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method;
        }
        return null;
    }

    private StringBuilder handleParams(StringBuilder params, Object[] args, List paramNames) throws JsonProcessingException {
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Map) {
                Set set = ((Map) args[i]).keySet();
                List list = new ArrayList();
                List paramList = new ArrayList<>();
                for (Object key : set) {
                    list.add(((Map) args[i]).get(key));
                    paramList.add(key);
                }
                return handleParams(params, list.toArray(), paramList);
            } else {
                if (args[i] instanceof Serializable) {
                    Class<?> aClass = args[i].getClass();
                    try {
                        aClass.getDeclaredMethod("toString", new Class[]{null});
                        // 如果不抛出NoSuchMethodException 异常则存在 toString 方法 ，安全的writeValueAsString ，否则 走 Object的 toString方法
                        params.append("  ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i]));
                    } catch (NoSuchMethodException e) {
                        params.append("  ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i].toString()));
                    }
                } else if (args[i] instanceof MultipartFile) {
                    MultipartFile file = (MultipartFile) args[i];
                    params.append("  ").append(paramNames.get(i)).append(": ").append(file.getName());
                } else {
                    params.append("  ").append(paramNames.get(i)).append(": ").append(args[i]);
                }
            }
        }
        return params;
    }
}
