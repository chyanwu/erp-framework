package com.chenyanwu.erp.erpframework.entity.rbac;
import javax.persistence.*;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import com.chenyanwu.erp.erpframework.entity.BaseEntity;
import java.util.Date;


/**
* <p>
    *
    *
    *
    * </p>
*
* @author chenyanwu
* @date 2019-02-27 11:15:03
* @version
*/
@Table(name = "erp_menu")
public class ErpMenu extends BaseEntity implements Serializable {

                    /**
        * 菜单层级
        */
                            @Column(name = "level")
    private Integer level;
                        /**
        * 菜单图标
        */
                        @Length(max=100,message="菜单图标 长度不能超过100")
                    @Column(name = "icon")
    private String icon;
                        /**
        * 权限标识
        */
                        @Length(max=200,message="权限标识 长度不能超过200")
                    @Column(name = "permission")
    private String permission;
                        /**
        * 排序
        */
                            @Column(name = "sort")
    private Integer sort;
                        /**
        * 菜单类型
        */
                            @Column(name = "type")
    private Boolean type;
                        /**
        * 菜单URL
        */
                        @Length(max=500,message="菜单URL 长度不能超过500")
                    @Column(name = "url")
    private String url;
                            /**
        * 打开方式
        */
                            @Column(name = "target")
    private Boolean target;
                            /**
        * 菜单父ID
        */
                        @Length(max=40,message="菜单父ID 长度不能超过40")
                    @Column(name = "parent_id")
    private String parentId;
                        /**
        * 菜单名称
        */
                        @Length(max=40,message="菜单名称 长度不能超过40")
                    @Column(name = "name")
    private String name;
                
    
    public Integer getLevel() {
    return level;
    }

    public void setLevel(Integer level) {
    this.level = level;
    }

        
    public String getIcon() {
    return icon;
    }

    public void setIcon(String icon) {
    this.icon = icon;
    }

        
    public String getPermission() {
    return permission;
    }

    public void setPermission(String permission) {
    this.permission = permission;
    }

        
    public Integer getSort() {
    return sort;
    }

    public void setSort(Integer sort) {
    this.sort = sort;
    }

        
    public Boolean getType() {
    return type;
    }

    public void setType(Boolean type) {
    this.type = type;
    }

        
    public String getUrl() {
    return url;
    }

    public void setUrl(String url) {
    this.url = url;
    }

            
    public Boolean getTarget() {
    return target;
    }

    public void setTarget(Boolean target) {
    this.target = target;
    }

            
    public String getParentId() {
    return parentId;
    }

    public void setParentId(String parentId) {
    this.parentId = parentId;
    }

        
    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }

                }