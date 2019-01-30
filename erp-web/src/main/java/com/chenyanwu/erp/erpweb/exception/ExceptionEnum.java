package com.chenyanwu.erp.erpweb.exception;

public enum ExceptionEnum {
    SUCCESS(200),
    RESOURCE_NOT_FOUND(404),
    ARGUMENTS_INVALID(401),
    BUSINESS_ERROR(400),
    SERVER_ERROR(500);

    private ExceptionEnum(int code) {
        this.code = code;
    }

    private ExceptionEnum() {
    }

    // 成员变量
    private int code;

    public int getCode() {
        return this.code;
    }
}
