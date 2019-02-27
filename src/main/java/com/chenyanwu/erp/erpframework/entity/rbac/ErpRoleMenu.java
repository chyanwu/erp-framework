package com.chenyanwu.erp.erpframework.entity.rbac;
import javax.persistence.*;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import com.chenyanwu.erp.erpframework.entity.BaseEntity;


/**
* <p>
    *
    *
    *
    * </p>
*
* @author chenyanwu
* @date 2019-02-27 17:04:18
* @version
*/
@Table(name = "erp_role_menu")
public class ErpRoleMenu implements Serializable {

                    /**
        * 角色ID
        */
                        @Length(max=32,message="角色ID 长度不能超过32")
                    @Column(name = "role_id")
    private String roleId;
                        /**
        * 菜单ID
        */
                        @Length(max=32,message="菜单ID 长度不能超过32")
                    @Column(name = "menu_id")
    private String menuId;
    
    
    public String getRoleId() {
    return roleId;
    }

    public void setRoleId(String roleId) {
    this.roleId = roleId;
    }

        
    public String getMenuId() {
    return menuId;
    }

    public void setMenuId(String menuId) {
    this.menuId = menuId;
    }

    }