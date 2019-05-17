package com.chenyanwu.erp.erpframework.controller.calendar;

import com.chenyanwu.erp.erpframework.entity.calendar.ErpRoom;
import com.chenyanwu.erp.erpframework.service.calendar.ErpRoomService;
import javafx.collections.MapChangeListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: chenyanwu
 * @Date: 2019/5/15 09:19
 * @Description:
 * @Version 1.0
 */
@Controller
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    ErpRoomService roomService;

    @GetMapping("/index")
    public ModelAndView index(ModelAndView modelAndView) {
        List<ErpRoom> list = roomService.selectAll();
        Map<String, List<ErpRoom>> map = new HashMap<>();
        map.put("roomList", list);
        modelAndView.addAllObjects(map);
        return modelAndView;
    }
}
