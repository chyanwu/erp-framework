package com.chenyanwu.erp.erpframework.service.impl.calendar;

import com.chenyanwu.erp.erpframework.dao.calendar.ErpCalendarTaskMapper;
import com.chenyanwu.erp.erpframework.entity.calendar.ErpCalendarTask;
import com.chenyanwu.erp.erpframework.entity.vo.CalendarTaskVo;
import com.chenyanwu.erp.erpframework.service.calendar.ErpCalendarTaskService;
import com.chenyanwu.erp.erpframework.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chenyanwu
 * @date 2019-05-16 17:02:47
 */
@Service
public class ErpCalendarTaskServiceImpl extends BaseServiceImpl<ErpCalendarTask, Object>
        implements ErpCalendarTaskService {

    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    private ErpCalendarTaskMapper erpcalendartaskMapper;

    @Autowired
    public void setMapper() {
        super.setMapper(erpcalendartaskMapper);
    }

    @Override
    public List<CalendarTaskVo> getErpCalendarTaskByParams(String roomId, Date sTime, Date eTime) {
        List<ErpCalendarTask> list = erpcalendartaskMapper.getErpCalendarTaskByParams(roomId, sTime, eTime);
        List<CalendarTaskVo> voList = new ArrayList<>();
        CalendarTaskVo vo;
        if(list.size() > 0) {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            for(ErpCalendarTask task: list) {
                vo = new CalendarTaskVo();
                vo.setId(task.getId());
                vo.setTitle(task.getAppointTheme());
                vo.setStart(formatter.format(task.getStime()));
                vo.setEnd(formatter.format(task.getEtime()));
                Map<String, Object> map = new HashMap<>();
                map.put("appointPerson", task.getAppointPerson());
                map.put("tel", task.getTel());
                vo.setExtendedProps(map);
                voList.add(vo);
            }
        }

        return voList;
    }
}
