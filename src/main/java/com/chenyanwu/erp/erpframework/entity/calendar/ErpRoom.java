package com.chenyanwu.erp.erpframework.entity.calendar;

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
 * @date 2019-05-16 16:14:13
 */
@Table(name = "erp_room")
public class ErpRoom extends BaseEntity implements Serializable {

    /**
     * 会议室名称
     */
    @Length(max = 50, message = "会议室名称 长度不能超过50")
    @Column(name = "name")
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}