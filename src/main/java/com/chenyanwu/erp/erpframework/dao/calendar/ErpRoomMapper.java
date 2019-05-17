package com.chenyanwu.erp.erpframework.dao.calendar;

import java.util.List;
import com.chenyanwu.erp.erpframework.entity.calendar.ErpRoom;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;

/**
* <p>
    * </p>
*
* @author chenyanwu
* @date 2019-05-16 16:14:13
* @version
*/
public interface ErpRoomMapper extends Mapper<ErpRoom>,InsertListMapper<ErpRoom> {


}