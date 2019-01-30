package com.chenyanwu.erp.erpdao.entity.erp;
import javax.persistence.*;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import com.chenyanwu.erp.erpdao.entity.BaseEntity;
import java.util.Date;


/**
* <p>
    *
    *
    *
    * </p>
*
* @author Ken
* @date 2019-01-15 19:17:41
* @version
*/
@Table(name = "erp_user")
public class User extends BaseEntity implements Serializable {

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