package com.chenyanwu.erp.erpdao.codegenerator.model;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName Table
 * @Description TODO
 * @Author chenyanwu
 * @Date 2019/1/14 17:18
 * @Version 1.0
 */
public class Table {
    private List<String> baseColumns = Arrays.asList("id", "createBy", "createDate", "updateBy", "updateDate");
    private boolean modulePrefix;
    private String module;
    private String lowerBeanName;
    private String tableName;
    private String beanName;
    private String remark;
    private List<String> primaryKeys;
    private List<Column> columns;
    private boolean hasDate;
    private boolean hasBigdecimal;

    public List<String> getBaseColumns() {
        return baseColumns;
    }

    public void setBaseColumns(List<String> baseColumns) {
        this.baseColumns = baseColumns;
    }

    public boolean isModulePrefix() {
        return modulePrefix;
    }

    public void setModulePrefix(boolean modulePrefix) {
        this.modulePrefix = modulePrefix;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getLowerBeanName() {
        return lowerBeanName;
    }

    public void setLowerBeanName(String lowerBeanName) {
        this.lowerBeanName = lowerBeanName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(List<String> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public boolean isHasDate() {
        return hasDate;
    }

    public void setHasDate(boolean hasDate) {
        this.hasDate = hasDate;
    }

    public boolean isHasBigdecimal() {
        return hasBigdecimal;
    }

    public void setHasBigdecimal(boolean hasBigdecimal) {
        this.hasBigdecimal = hasBigdecimal;
    }
}
