package com.chenyanwu.erp.erpframework.common.codegenerator.model;

/**
 * @ClassName DictItem
 * @Description TODO
 * @Author chenyanwu
 * @Date 2019/1/14 17:22
 * @Version 1.0
 */
public class DictItem {
    private String dictId;
    private String dictCode;
    private String dictText;
    private int itemSort;
    private String itemText;
    private String itemValue;

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictText() {
        return dictText;
    }

    public void setDictText(String dictText) {
        this.dictText = dictText;
    }

    public int getItemSort() {
        return itemSort;
    }

    public void setItemSort(int itemSort) {
        this.itemSort = itemSort;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }
}
