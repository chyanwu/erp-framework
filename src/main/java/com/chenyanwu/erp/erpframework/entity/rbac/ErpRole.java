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
* @date 2019-02-21 17:39:55
* @version
*/
@Table(name = "erp_role")
public class ErpRole extends BaseEntity implements Serializable {

                        /**
        * 角色名称
        */
                        @Length(max=40,message="角色名称 长度不能超过40")
                    @Column(name = "name")
    private String name;
                        /**
        * 备注
        */
                        @Length(max=255,message="备注 长度不能超过255")
                    @Column(name = "comment")
    private String comment;


    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }

        
    public String getComment() {
    return comment;
    }

    public void setComment(String comment) {
    this.comment = comment;
    }

                    }