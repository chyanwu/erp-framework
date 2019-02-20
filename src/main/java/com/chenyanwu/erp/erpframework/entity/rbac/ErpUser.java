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
* @date 2019-02-20 11:17:03
* @version
*/
@Table(name = "erp_user")
public class ErpUser extends BaseEntity implements Serializable {

                    /**
        * 密码
        */
                        @Length(max=255,message="密码 长度不能超过255")
                    @Column(name = "password")
    private String password;
                        /**
        * 性别
        */
                        @Length(max=1,message="性别 长度不能超过1")
                    @Column(name = "sex")
    private String sex;
                            /**
        * 年龄
        */
                            @Column(name = "age")
    private Integer age;
                        /**
        * 用户名
        */
                        @Length(max=40,message="用户名 长度不能超过40")
                    @Column(name = "username")
    private String username;
    
    
    public String getPassword() {
    return password;
    }

    public void setPassword(String password) {
    this.password = password;
    }

        
    public String getSex() {
    return sex;
    }

    public void setSex(String sex) {
    this.sex = sex;
    }

            
    public Integer getAge() {
    return age;
    }

    public void setAge(Integer age) {
    this.age = age;
    }

        
    public String getUsername() {
    return username;
    }

    public void setUsername(String username) {
    this.username = username;
    }

    }