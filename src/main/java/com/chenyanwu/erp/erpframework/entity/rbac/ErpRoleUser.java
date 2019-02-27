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
* @date 2019-02-27 17:28:29
* @version
*/
@Table(name = "erp_role_user")
public class ErpRoleUser implements Serializable {

                    /**
        * 角色ID
        */
                        @Length(max=32,message="角色ID 长度不能超过32")
                    @Column(name = "role_id")
    private String roleId;
                        /**
        * 用户ID
        */
                        @Length(max=32,message="用户ID 长度不能超过32")
                    @Column(name = "user_id")
    private String userId;
    
    
    public String getRoleId() {
    return roleId;
    }

    public void setRoleId(String roleId) {
    this.roleId = roleId;
    }

        
    public String getUserId() {
    return userId;
    }

    public void setUserId(String userId) {
    this.userId = userId;
    }

    }