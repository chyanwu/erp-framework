package com.chenyanwu.erp.erpframework.controller.importutil;

import com.chenyanwu.erp.erpframework.common.PageResultBean;
import com.chenyanwu.erp.erpframework.common.ResultBean;
import com.chenyanwu.erp.erpframework.exception.ExceptionEnum;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.chenyanwu.erp.erpframework.service.importutil.ErpStudentExcelService;
import com.chenyanwu.erp.erpframework.entity.importutil.ErpStudentExcel;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author chenyanwu
 * @date 2019-04-26 15:26:52
 */
@Controller

@RequestMapping(value = "/erpstudentexcel")
public class ErpStudentExcelController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ErpStudentExcelService service;


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<ErpStudentExcel> get(String id) {
        ErpStudentExcel item = (ErpStudentExcel) service.selectByPrimaryKey(id);
        if (item != null) {
            return new ResultBean<>(item);
        } else {
            return new ResultBean<>(ExceptionEnum.RESOURCE_NOT_FOUND, null, "找不到该记录", null);
        }
    }


    @RequestMapping(value = "/getlist", method = RequestMethod.GET)
    @ResponseBody
    public PageResultBean<List<ErpStudentExcel>> getList(int page, int limit) {
        List<ErpStudentExcel> list;
        PageHelper.startPage(page, limit);
        list = service.selectAll();
        return new PageResultBean(list, page, limit, ((Page) list).getTotal());

    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> create(@RequestBody @Validated ErpStudentExcel item) {
        service.insertSelective(item);
        return new ResultBean<String>("");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> update(@RequestBody @Validated ErpStudentExcel item) {
        service.updateByPrimaryKeySelective(item);
        return new ResultBean<String>("");
    }

    @RequestMapping(value = "/deleteByID", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Integer> delete(String id) {
        int result = service.deleteByPrimaryKey(id);
        return new ResultBean<Integer>(result);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Integer> delete(@RequestBody ErpStudentExcel item) {
        int result = service.delete(item);
        return new ResultBean<Integer>(result);
    }

    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView();
    }

    @GetMapping("add")
    public ModelAndView add() {
        return new ModelAndView();
    }

    @GetMapping("edit")
    public ModelAndView edit(String id) {
        ModelAndView modelAndView = new ModelAndView();
        ErpStudentExcel item = (ErpStudentExcel) service.selectByPrimaryKey(id);
        modelAndView.addObject("entity", item);
        return modelAndView;
    }


}