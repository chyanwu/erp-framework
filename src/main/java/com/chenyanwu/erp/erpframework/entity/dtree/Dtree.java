package com.chenyanwu.erp.erpframework.entity.dtree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: chenyanwu
 * @Date: 2019/2/27 15:12
 * @Description:
 * @Version 1.0
 */
public class Dtree<T> {
    /** 节点ID*/
    private String id;
    /** 上级节点ID*/
    private String parentId;
    /** 节点名称*/
    private String title;
    /** 层级*/
    private String level;
    /** 是否最后一级节点*/
    private Boolean isLast;
    /** 自定义图标class*/
    private String iconClass;
    /** 表示用户自定义需要存储在树节点中的数据*/
    private T basicData;
    /** 复选框集合*/
    private List<CheckArr> checkArr = new ArrayList<CheckArr>();
    /** 子节点集合*/
    private List<Dtree> children = new ArrayList<Dtree>();
    /** 物业项目id*/
    private String itemId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Boolean getLast() {
        return isLast;
    }

    public void setLast(Boolean last) {
        isLast = last;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public T getBasicData() {
        return basicData;
    }

    public void setBasicData(T basicData) {
        this.basicData = basicData;
    }

    public List<CheckArr> getCheckArr() {
        if(checkArr.size() == 0){
            checkArr.add(new CheckArr());
        }
        return checkArr;
    }

    public void setCheckArr(List<CheckArr> checkArr) {
        this.checkArr = checkArr;
    }

    public List<Dtree> getChildren() {
        return children;
    }

    public void setChildren(List<Dtree> children) {
        this.children = children;
    }


    public void addChildren(Dtree child) {
        if(children == null){
            children = new ArrayList<>();
        }
        children.add(child);
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
