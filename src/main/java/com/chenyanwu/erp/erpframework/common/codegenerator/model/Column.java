package com.chenyanwu.erp.erpframework.common.codegenerator.model;

import java.util.List;

/**
 * @ClassName Column
 * @Description TODO
 * @Author chenyanwu
 * @Date 2019/1/14 17:19
 * @Version 1.0
 */
public class Column {

    private String lowerProperty;
    private String property;
    private String column;
    private String type;
    private String jdbcType;
    private String remark;
    private int dataType;
    private boolean allowNull;
    private boolean hasDictItem;
    private List<DictItem> dictitemList;
    private int maxLength;

    public String getLowerProperty() {
        return lowerProperty;
    }

    public void setLowerProperty(String lowerProperty) {
        this.lowerProperty = lowerProperty;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public boolean isAllowNull() {
        return allowNull;
    }

    public void setAllowNull(boolean allowNull) {
        this.allowNull = allowNull;
    }

    public boolean isHasDictItem() {
        return hasDictItem;
    }

    public void setHasDictItem(boolean hasDictItem) {
        this.hasDictItem = hasDictItem;
    }

    public List<DictItem> getDictitemList() {
        return dictitemList;
    }

    public void setDictitemList(List<DictItem> dictitemList) {
        this.dictitemList = dictitemList;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
