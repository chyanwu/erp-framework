package com.chenyanwu.erp.erpweb.common;

import com.chenyanwu.erp.erpweb.exception.ExceptionEnum;

import java.util.Collection;

/**
 * @ClassName ResultBean
 * @Description TODO
 * @Author chenyanwu
 * @Date 2019/1/15 16:00
 * @Version 1.0
 */
public class ResultBean<T> {
    private Integer code;
    private Integer count;
    private T data;
    private String errStr;
    private String msg = "";

    public ResultBean(ExceptionEnum exceptionEnum, String errStr, String msg, T data) {
        this.code = exceptionEnum.getCode();
        this.errStr = errStr;
        this.msg = msg;
        this.data = data;
        if (data instanceof Collection) {
            this.count = ((Collection) data).size();
        }
    }

    public ResultBean() {
    }

    public ResultBean(T data) {
        this(ExceptionEnum.SUCCESS, "", "操作成功", data);
    }

    public ResultBean(Throwable e) {
        this.code = ExceptionEnum.SERVER_ERROR.getCode();

        this.msg = e.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErrStr() {
        return errStr;
    }

    public void setErrStr(String errStr) {
        this.errStr = errStr;
    }
}
