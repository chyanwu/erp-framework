package com.chenyanwu.erp.erpframework.entity.rbac;
import javax.persistence.*;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import com.chenyanwu.erp.erpframework.entity.BaseEntity;
import java.util.Date;
import java.util.List;


/**
* <p>
    *
    *
    *
    * </p>
*
* @author chenyanwu
* @date 2019-02-27 11:14:41
* @version
*/
@Table(name = "erp_user")
public class ErpUser extends BaseEntity implements Serializable {

                    /**
        * 盐值
        */
                        @Length(max=40,message="盐值 长度不能超过40")
                    @Column(name = "salt")
    private String salt;
                        /**
        * 是否启用
        */
                            @Column(name = "enabled")
    private Integer enabled;
                                /**
        * 用户密码
        */
                        @Length(max=40,message="用户密码 长度不能超过40")
                    @Column(name = "password")
    private String password;
                        /**
        * 登陆账号
        */
                        @Length(max=30,message="登陆账号 长度不能超过30")
                    @Column(name = "login_name")
    private String loginName;
                        /**
        * 手机号码
        */
                        @Length(max=11,message="手机号码 长度不能超过11")
                    @Column(name = "phone")
    private String phone;
                        /**
        * 用户名称
        */
                        @Length(max=30,message="用户名称 长度不能超过30")
                    @Column(name = "name")
    private String name;
                            /**
        * 是否被锁
        */
                            @Column(name = "locked")
    private Integer locked;
                                /**
        * 邮箱
        */
                        @Length(max=30,message="邮箱 长度不能超过30")
                    @Column(name = "email")
    private String email;

                        private List<ErpRole> roles;
                        private List<ErpMenu> menus;

    public List<ErpRole> getRoles() {
        return roles;
    }

    public void setRoles(List<ErpRole> roles) {
        this.roles = roles;
    }

    public List<ErpMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<ErpMenu> menus) {
        this.menus = menus;
    }

    public String getSalt() {
    return salt;
    }

    public void setSalt(String salt) {
    this.salt = salt;
    }

        
    public Integer getEnabled() {
    return enabled;
    }

    public void setEnabled(Integer enabled) {
    this.enabled = enabled;
    }

                
    public String getPassword() {
    return password;
    }

    public void setPassword(String password) {
    this.password = password;
    }

        
    public String getLoginName() {
    return loginName;
    }

    public void setLoginName(String loginName) {
    this.loginName = loginName;
    }

        
    public String getPhone() {
    return phone;
    }

    public void setPhone(String phone) {
    this.phone = phone;
    }

        
    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }

            
    public Integer getLocked() {
    return locked;
    }

    public void setLocked(Integer locked) {
    this.locked = locked;
    }

                
    public String getEmail() {
    return email;
    }

    public void setEmail(String email) {
    this.email = email;
    }

    }