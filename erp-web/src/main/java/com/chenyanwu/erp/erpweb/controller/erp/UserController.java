package com.chenyanwu.erp.erpweb.controller.erp;

import com.chenyanwu.erp.erpdao.entity.erp.User;
import com.chenyanwu.erp.erpservice.service.erp.UserService;
import com.chenyanwu.erp.erpweb.common.PageResultBean;
import com.chenyanwu.erp.erpweb.common.ResultBean;
import com.chenyanwu.erp.erpweb.exception.ExceptionEnum;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author Ken
 * @date 2019-01-15 19:17:41
 */
@Controller
@Api(value = "swagger2中UserController的Api", description = "用户控制层")
@ApiModel(value = "User", description = "用户")
@RequestMapping(value = "/erp/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService service;


    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", paramType = "query", required = true, dataType = "String")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<User> get(String id) {
        User item = (User) service.selectByPrimaryKey(id);
        if (item != null) {
            return new ResultBean<>(item);
        } else {
            return new ResultBean<>(ExceptionEnum.RESOURCE_NOT_FOUND, null, "找不到该记录", null);
        }
    }

    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @RequestMapping(value = "/getlist", method = RequestMethod.GET)
    @ResponseBody
    public PageResultBean<List<User>> getList(int page, int limit) {
        List<User> list;
        PageHelper.startPage(page, limit);
        list = service.selectAll();
        return new PageResultBean(list, page, limit, ((Page) list).getTotal());

    }

    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> create(@RequestBody @Validated User item) {
        service.insertSelective(item);
        return new ResultBean<String>("");
    }

    @ApiOperation(value = "更新信息", notes = "根据User对象更新用户信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> update(@RequestBody @Validated User item) {
        service.updateByPrimaryKey(item);
        return new ResultBean<String>("");
    }

    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除用户")
    @RequestMapping(value = "/deleteByID", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Integer> delete(String id) {
        int result = service.deleteByPrimaryKey(id);
        return new ResultBean<Integer>(result);
    }

    @ApiOperation(value = "删除用户", notes = "根据User对象来指定删除用户")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Integer> delete(@RequestBody User item) {
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
        User item = (User) service.selectByPrimaryKey(id);
        modelAndView.addObject("entity", item);
        return modelAndView;
    }


}