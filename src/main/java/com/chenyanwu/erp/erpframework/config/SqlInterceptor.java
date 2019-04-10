package com.chenyanwu.erp.erpframework.config;

import cn.hutool.core.util.IdUtil;
import com.chenyanwu.erp.erpframework.common.util.JsonUtil;
import com.chenyanwu.erp.erpframework.common.util.TimeBasedUUIDGenerator;
import com.chenyanwu.erp.erpframework.entity.BaseEntity;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * @Auther: chenyanwu
 * @Date: 2019/2/22 10:39
 * @Description: 1、自动设置id updatedate等5个字段，使用时间序列生成UUI，jdk默认的uud有重复概率
 * 2、自动化写日志
 * @Version 1.0
 */
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class SqlInterceptor implements Interceptor {

    private Logger logger = LoggerFactory.getLogger(SqlInterceptor.class);


    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        //获取原始的ms
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        String commandName = ms.getSqlCommandType().name();
        Object parameter = invocation.getArgs()[1];

        if (commandName.startsWith("DELETE")) {
            logger.info("删除记录{}，id信息：{}", parameter.getClass().getName(), parameter);
            return invocation.proceed();
        } else if (commandName.startsWith("INSERT")) {
            if (parameter instanceof BaseEntity) {
                BaseEntity entity = (BaseEntity) parameter;
                setDefaultValue(entity);
                logger.info("新增记录{}：{}", parameter.getClass().getName(), JsonUtil.bean2Json(parameter));
            } else if (parameter instanceof HashMap) {
                if (((HashMap) parameter).size() == 2 && ((HashMap<String, Object>) parameter).containsKey("collection")) {
                    //批量插入
                    HashMap<String, Object> temp = (HashMap<String, Object>) parameter;
                    List<Object> list = (List<Object>) temp.get("collection");
                    for (Object obj : list) {
                        if (obj instanceof BaseEntity) {
                            setDefaultValue((BaseEntity) obj);
                        }
                    }
                    logger.info("批量新增记录{}", list.get(0).getClass().getName(), JsonUtil.bean2Json(list));
                }

            }

        } else {//修改
            if (parameter instanceof BaseEntity) {
                BaseEntity entity = (BaseEntity) parameter;
                entity.setUpdateBy(MDC.get("userid"));
                entity.setUpdateDate(new Date());
            }
            logger.info("修改记录{}：{}", parameter.getClass().getName(), JsonUtil.bean2Json(parameter));
        }
        return invocation.proceed();
    }

    private void setDefaultValue(BaseEntity entity) {
        if (StringUtils.isEmpty(entity.getId())) {
            entity.setId(IdUtil.simpleUUID());
        }
        entity.setCreateBy(MDC.get("userid"));
        entity.setCreateDate(new Date());
        entity.setUpdateBy(MDC.get("userid"));
        entity.setUpdateDate(new Date());
    }

    /**
     * @param
     * @return
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
