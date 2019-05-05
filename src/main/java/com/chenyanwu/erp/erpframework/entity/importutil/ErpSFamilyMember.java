package com.chenyanwu.erp.erpframework.entity.importutil;

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
 * @date 2019-04-29 11:37:43
 */
@Table(name = "erp_s_family_member")
public class ErpSFamilyMember implements Serializable {

    @Length(max=40,message="id 长度不能超过40")
    @Id
    @Column(name = "id")
    private String id;

    /**
     * 学生ID
     */
    @Length(max = 32, message = "学生ID 长度不能超过32")
    @Column(name = "stud_id")
    private String studId;
    /**
     * 成员名称
     */
    @Length(max = 20, message = "成员名称 长度不能超过20")
    @Column(name = "name")
    private String name;
    /**
     * 成员工作
     */
    @Length(max = 50, message = "成员工作 长度不能超过50")
    @Column(name = "job")
    private String job;
    /**
     * 成员关系
     */
    @Column(name = "relation")
    private Integer relation;


    public String getStudId() {
        return studId;
    }

    public void setStudId(String studId) {
        this.studId = studId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }


    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}