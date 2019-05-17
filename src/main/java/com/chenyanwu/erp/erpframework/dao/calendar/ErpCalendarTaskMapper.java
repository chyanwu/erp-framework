package com.chenyanwu.erp.erpframework.dao.calendar;

import java.util.Date;
import java.util.List;
import com.chenyanwu.erp.erpframework.entity.calendar.ErpCalendarTask;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;

/**
* <p>
    * </p>
*
* @author chenyanwu
* @date 2019-05-16 17:02:47
* @version
*/
public interface ErpCalendarTaskMapper extends Mapper<ErpCalendarTask>,InsertListMapper<ErpCalendarTask> {

    List<ErpCalendarTask> getErpCalendarTaskByParams(@Param("roomId") String roomId, @Param("sTime") Date sTime, @Param("eTime") Date eTime);

}