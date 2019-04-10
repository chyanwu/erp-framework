package com.chenyanwu.erp.erpframework.controller.rbac;

import com.chenyanwu.erp.erpframework.common.ResultBean;
import com.chenyanwu.erp.erpframework.entity.rbac.ErpRole;
import com.chenyanwu.erp.erpframework.entity.rbac.ErpRoleMenu;
import com.chenyanwu.erp.erpframework.exception.ExceptionEnum;
import com.chenyanwu.erp.erpframework.service.rbac.ErpRoleMenuService;
import com.chenyanwu.erp.erpframework.service.rbac.ErpRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author chenyanwu
 * @date 2019-02-21 17:38:30
 */
@Controller
@RequestMapping(value = "/erprole")
public class ErpRoleController {
    @Autowired
    private ErpRoleService service;

    @Autowired
    private ErpRoleMenuService roleMenuService;


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<ErpRole> get(String id) {
        ErpRole item = (ErpRole) service.selectByPrimaryKey(id);
        if (item != null) {
            return new ResultBean<>(item);
        } else {
            return new ResultBean<>(ExceptionEnum.RESOURCE_NOT_FOUND, null, "找不到该记录", null);
        }
    }


    @RequestMapping(value = "/getlist", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<List<ErpRole>> getList(String keyword) {
        List<ErpRole> list;
        if (keyword != null && !keyword.trim().isEmpty()) {
            keyword = "%" + keyword.trim() + "%";
            Example example = new Example(ErpRole.class);
            example.createCriteria().andLike("name", keyword);
            list = service.selectByExample(example);
        } else {
            list = service.selectAll();
        }

        return new ResultBean<List<ErpRole>>(list);

    }

    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<List<ErpRole>> getAll() {
        List<ErpRole> list = service.selectAll();
        return new ResultBean<List<ErpRole>>(list);

    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> create(ErpRole item) {
        service.insertSelective(item);
        return new ResultBean<String>("");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> update(@Validated ErpRole item) {
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
    public ResultBean<Integer> delete(ErpRole item) {
        int result = service.delete(item);
        return new ResultBean<Integer>(result);
    }

    @GetMapping("index")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("/rbac/role/index");
        return modelAndView;
    }

    @GetMapping("add")
    public ModelAndView add() {
        return new ModelAndView();
    }

    @GetMapping("edit")
    public ModelAndView edit(String id) {
        ModelAndView modelAndView = new ModelAndView();
        ErpRole item = (ErpRole) service.selectByPrimaryKey(id);
        modelAndView.addObject("entity", item);
        return modelAndView;
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> auth(String id, String ids) {
        String[] split = ids.split(",");
        List<ErpRoleMenu> rms = new ArrayList<>();
        for (String menuId : split) {
            ErpRoleMenu rm = new ErpRoleMenu();
            rm.setMenuId(menuId);
            rm.setRoleId(id);
            rms.add(rm);
        }
        Example example = new Example(ErpRoleMenu.class);
        example.createCriteria().andEqualTo("roleId", id);
        roleMenuService.deleteByExample(example);
        roleMenuService.insertList(rms);
        return new ResultBean<String>("");
    }

}