package com.chenyanwu.erp.erpframework.service.calendar;

import com.chenyanwu.erp.erpframework.entity.calendar.ErpCalendarTask;
import com.chenyanwu.erp.erpframework.entity.vo.CalendarTaskVo;
import com.chenyanwu.erp.erpframework.service.BaseService;

import java.util.Date;
import java.util.List;

/**
* <p>
    *  服务类
    * </p>
*
* @author chenyanwu
* @date 2019-05-16 17:02:47
* @version
*/
public interface ErpCalendarTaskService extends BaseService<ErpCalendarTask, Object> {

    List<CalendarTaskVo> getErpCalendarTaskByParams(String roomId, Date sTime, Date eTime);
}
