package com.chenyanwu.erp.erpframework.entity.importutil;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @Auther: chenyanwu
 * @Date: 2019/4/26 15:05
 * @Description:
 * @Version 1.0
 */
public class StudentExcelImport {
    @Excel(name = "姓名", orderNum = "1", width = 20)
    private String name;

    @Excel(name = "年龄", orderNum = "2")
    private Integer age;

    @Excel(name = "地址", orderNum = "3", width = 60)
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
