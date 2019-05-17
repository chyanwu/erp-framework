package com.chenyanwu.erp.erpframework.entity.vo;

import java.util.Map;

/**
 * @Auther: chenyanwu
 * @Date: 2019/5/16 17:54
 * @Description:
 * @Version 1.0
 */
public class CalendarTaskVo {
    private String title;
    private String start;
    private String end;
    private String id;
    private Map<String, Object> extendedProps;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getExtendedProps() {
        return extendedProps;
    }

    public void setExtendedProps(Map<String, Object> extendedProps) {
        this.extendedProps = extendedProps;
    }
}
