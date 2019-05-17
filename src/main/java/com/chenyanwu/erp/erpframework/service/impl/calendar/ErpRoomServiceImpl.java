package com.chenyanwu.erp.erpframework.service.impl.calendar;

import com.chenyanwu.erp.erpframework.entity.calendar.ErpRoom;
import com.chenyanwu.erp.erpframework.dao.calendar.ErpRoomMapper;
import com.chenyanwu.erp.erpframework.service.calendar.ErpRoomService;
import com.chenyanwu.erp.erpframework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
* <p>
    *  服务实现类
    * </p>
*
* @author chenyanwu
* @date 2019-05-16 16:14:13
* @version
*/
@Service
public class ErpRoomServiceImpl extends BaseServiceImpl<ErpRoom, Object>
    implements ErpRoomService {

@Autowired
private ErpRoomMapper erproomMapper;

@Autowired
public void setMapper() {
super.setMapper(erproomMapper);
}
}
