package com.chenyanwu.erp.erpframework.entity.calendar;

import com.chenyanwu.erp.erpframework.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 *
 *
 *
 * </p>
 *
 * @author chenyanwu
 * @date 2019-05-16 17:02:47
 */
@Table(name = "erp_calendar_task")
public class ErpCalendarTask extends BaseEntity implements Serializable {

    /**
     * 会议室ID
     */
    @Length(max = 32, message = "会议室ID 长度不能超过32")
    @Column(name = "room_id")
    private String roomId;
    /**
     * 预约主题
     */
    @Length(max = 100, message = "预约主题 长度不能超过100")
    @Column(name = "appoint_theme")
    private String appointTheme;
    /**
     * 预约人
     */
    @Length(max = 20, message = "预约人 长度不能超过20")
    @Column(name = "appoint_person")
    private String appointPerson;
    /**
     * 预约结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @Column(name = "etime")
    private Date etime;
    /**
     * 预约开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @Column(name = "stime")
    private Date stime;
    /**
     * 联系方式
     */
    @Length(max = 20, message = "联系方式 长度不能超过20")
    @Column(name = "tel")
    private String tel;


    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }


    public String getAppointTheme() {
        return appointTheme;
    }

    public void setAppointTheme(String appointTheme) {
        this.appointTheme = appointTheme;
    }


    public String getAppointPerson() {
        return appointPerson;
    }

    public void setAppointPerson(String appointPerson) {
        this.appointPerson = appointPerson;
    }


    public Date getEtime() {
        return etime;
    }

    public void setEtime(Date etime) {
        this.etime = etime;
    }


    public Date getStime() {
        return stime;
    }

    public void setStime(Date stime) {
        this.stime = stime;
    }


    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}