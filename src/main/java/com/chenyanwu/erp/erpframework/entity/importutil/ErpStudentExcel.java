package com.chenyanwu.erp.erpframework.entity.importutil;
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
* @date 2019-04-26 15:26:52
* @version
*/
@Table(name = "erp_student_excel")
public class ErpStudentExcel extends BaseEntity implements Serializable {

                        /**
        * 错误原因
        */
                        @Length(max=2000,message="错误原因 长度不能超过2000")
                    @Column(name = "reason")
    private String reason;
                        /**
        * 地址
        */
                        @Length(max=255,message="地址 长度不能超过255")
                    @Column(name = "address")
    private String address;
                        /**
        * 姓名
        */
                        @Length(max=40,message="姓名 长度不能超过40")
                    @Column(name = "name")
    private String name;
                                    /**
        * 年龄
        */
                            @Column(name = "age")
    private Integer age;
        
        
    public String getReason() {
    return reason;
    }

    public void setReason(String reason) {
    this.reason = reason;
    }

        
    public String getAddress() {
    return address;
    }

    public void setAddress(String address) {
    this.address = address;
    }

        
    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }

                    
    public Integer getAge() {
    return age;
    }

    public void setAge(Integer age) {
    this.age = age;
    }

        }