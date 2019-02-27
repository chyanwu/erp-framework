package com.chenyanwu.erp.erpframework.entity.dtree;

/**
 * @Auther: chenyanwu
 * @Date: 2019/2/27 15:13
 * @Description:
 * @Version 1.0
 */
public class CheckArr {
    public static final String CHECKED = "1";
    /** 复选框标记 官方文档没有太多说明 先用默认值*/
    private String type = "0";
    /** 复选框是否选中 默认0未选中*/
    private String isChecked = "0";

    public CheckArr(){}
    public CheckArr(String type, String isChecked){
        this.isChecked = isChecked;
        this.type = type;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }
}
