package com.chenyanwu.erp.erpframework.config;

import com.chenyanwu.erp.erpframework.common.ResultBean;
import com.chenyanwu.erp.erpframework.common.util.JsonUtil;
import com.chenyanwu.erp.erpframework.exception.BusinessException;
import com.chenyanwu.erp.erpframework.exception.ExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @Auther: chenyanwu
 * @Date: 2019/4/4 17:55
 * @Description: 全局异常处理类
 * @Version 1.0
 */
@ControllerAdvice(annotations = {Controller.class, RestController.class})
@ResponseBody
public class CommonExceptionHandler {
    Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

    /**
     * 统一处理bean验证抛出的参数校验异常
     * 参数校验失败，统一采用warn记录日志
     * @see javax.validation.Valid
     * @see org.springframework.validation.Validator
     * @see org.springframework.validation.DataBinder
     */
    @ExceptionHandler(BindException.class)
    public ResultBean<List<FieldError>> validExceptionHandler(BindException e, WebRequest request, HttpServletResponse response) {

        List<FieldError> fieldErrors=e.getBindingResult().getFieldErrors();
        logger.warn("参数绑定失败,{}", JsonUtil.bean2Json(fieldErrors));

        return  new ResultBean<>(ExceptionEnum.ARGUMENTS_INVALID, null, "arguments invalid", fieldErrors);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultBean<List<FieldError>> validExceptionHandler(MethodArgumentNotValidException e) {

        List<FieldError> fieldErrors=e.getBindingResult().getFieldErrors();
        logger.warn("参数校验失败,{}", JsonUtil.bean2Json(fieldErrors));
        return  new ResultBean<>(ExceptionEnum.ARGUMENTS_INVALID,null,"arguments invalid",fieldErrors);

    }

    /**
     * 统一拦截处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResultBean<String> validExceptionHandler(BusinessException e) {
        logger.warn("业务异常：【{}】", e.getMessage(),e);
        ResultBean<String> result= new ResultBean<>();
        result.setCode(ExceptionEnum.BUSINESS_ERROR.getCode());
        result.setErrStr(e.getErrCode());
        result.setMsg(e.getMessage());
        result.setData(JsonUtil.bean2Json(e.getData()));
        return result;
    }

    /**
     * 默认统一异常处理方法
     * @param e 默认Exception异常对象
     * @return
     */
    @ExceptionHandler
    public ResultBean<String> runtimeExceptionHandler(Exception e) {
        logger.error("运行时异常：【{}】", e.getMessage(),e);
        ResultBean<String> result= new ResultBean<>();
        result.setCode(ExceptionEnum.SERVER_ERROR.getCode());
        result.setMsg(e.getMessage()+"-- traceid:"+ MDC.get("traceId"));
        return result;
    }
}
