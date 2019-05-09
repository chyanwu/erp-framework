package com.chenyanwu.erp.erpframework.entity.log;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 *
 *
 *
 * </p>
 *
 * @author chenyanwu
 * @date 2019-05-06 15:29:27
 */
@Table(name = "erp_log")
public class ErpLog implements Serializable {

    @Column(name = "id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 错误信息
     */
    @Length(max = 65535, message = "错误信息 长度不能超过65535")
    @Column(name = "error_message")
    private String errorMessage;
    /**
     * 返回信息
     */
    @Length(max = 2147483647, message = "返回信息 长度不能超过2147483647")
    @Column(name = "response_value")
    private String responseValue;
    /**
     * 操作结束时间
     */
    @Column(name = "end_time")
    private Date endTime;
    /**
     * 请求类型
     */
    @Length(max = 20, message = "请求类型 长度不能超过20")
    @Column(name = "type")
    private String type;
    /**
     * 日志标题
     */
    @Length(max = 255, message = "日志标题 长度不能超过255")
    @Column(name = "title")
    private String title;
    /**
     * 请求参数
     */
    @Length(max = 65535, message = "请求参数 长度不能超过65535")
    @Column(name = "params")
    private String params;
    /**
     * 请求地址
     */
    @Length(max = 255, message = "请求地址 长度不能超过255")
    @Column(name = "uri")
    private String uri;
    /**
     * 操作浏览器
     */
    @Length(max = 50, message = "操作浏览器 长度不能超过50")
    @Column(name = "brower")
    private String brower;
    /**
     * 操作开始时间
     */
    @Column(name = "start_time")
    private Date startTime;
    /**
     * HTTP方法
     */
    @Length(max = 10, message = "HTTP方法 长度不能超过10")
    @Column(name = "http_method")
    private String httpMethod;
    /**
     * 软删除
     */
    @Column(name = "is_deleted")
    private Integer isDeleted;
    /**
     * 请求方法
     */
    @Length(max = 255, message = "请求方法 长度不能超过255")
    @Column(name = "class_method")
    private String classMethod;
    /**
     * 执行时间
     */
    @Column(name = "execute_time")
    private Long executeTime;
    /**
     * 请求主机
     */
    @Length(max = 255, message = "请求主机 长度不能超过255")
    @Column(name = "host")
    private String host;
    /**
     * 请求头
     */
    @Length(max = 255, message = "请求头 长度不能超过255")
    @Column(name = "header")
    private String header;
    /**
     * 操作系统
     */
    @Length(max = 255, message = "操作系统 长度不能超过255")
    @Column(name = "operating_system")
    private String operatingSystem;
    /**
     * 错误码
     */
    @Column(name = "error_code")
    private Integer errorCode;
    /**
     * 操作用户
     */
    @Length(max = 30, message = "操作用户 长度不能超过30")
    @Column(name = "username")
    private String username;


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public String getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(String responseValue) {
        this.responseValue = responseValue;
    }


    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


    public String getBrower() {
        return brower;
    }

    public void setBrower(String brower) {
        this.brower = brower;
    }


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }


    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }


    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }


    public String getClassMethod() {
        return classMethod;
    }

    public void setClassMethod(String classMethod) {
        this.classMethod = classMethod;
    }


    public Long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Long executeTime) {
        this.executeTime = executeTime;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }


    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }


    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}