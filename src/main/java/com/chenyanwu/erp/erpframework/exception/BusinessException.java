package com.chenyanwu.erp.erpframework.exception;


/**
 * Created by wd on 2016-7-20.
 */
//业务异常，异常里的信息为友好信息，可以直接展示给用户查看，例如登录时提示“用户名不存在或密码错误”
public class BusinessException extends  RuntimeException{
    private String errCode;
    private Object data;

    public BusinessException(String errCode, String errMsg){
        super(errMsg);
        this.errCode = errCode;
    }

    public BusinessException(MsgContentStruct errEntity){
        super(errEntity.Value());

        this.errCode = errEntity.Key();
    }

    public BusinessException(MsgContentStruct errEntity, Object... paras){
        super(errEntity.FormatValue(paras));

        this.errCode = errEntity.Key();
    }

    public BusinessException(String errCode, String errMsg, Object data){
        super(errMsg);
        this.errCode = errCode;
        this.data = data;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}