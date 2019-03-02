package com.chenyanwu.erp.erpframework.controller.rbac;

import com.chenyanwu.erp.erpframework.common.Constants;
import com.chenyanwu.erp.erpframework.common.PageResultBean;
import com.chenyanwu.erp.erpframework.common.ResultBean;
import com.chenyanwu.erp.erpframework.common.util.ToolUtil;
import com.chenyanwu.erp.erpframework.entity.rbac.ErpRole;
import com.chenyanwu.erp.erpframework.entity.rbac.ErpRoleUser;
import com.chenyanwu.erp.erpframework.exception.BusinessException;
import com.chenyanwu.erp.erpframework.exception.ExceptionEnum;
import com.chenyanwu.erp.erpframework.service.rbac.ErpRoleService;
import com.chenyanwu.erp.erpframework.service.rbac.ErpRoleUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.chenyanwu.erp.erpframework.service.rbac.ErpUserService;
import com.chenyanwu.erp.erpframework.entity.rbac.ErpUser;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
* <p>
    * </p>
*
* @author chenyanwu
* @date 2019-02-27 11:14:41
* @version
*/
@Controller

@RequestMapping(value = "/erpuser")
public class ErpUserController {

Logger logger = LoggerFactory.getLogger(this.getClass());
@Autowired
private ErpUserService service;

@Autowired
private ErpRoleService roleService;

@Autowired
private ErpRoleUserService roleUserService;


@RequestMapping(value = "/get",method = RequestMethod.GET)
@ResponseBody
public ResultBean<ErpUser> get(String id){
ErpUser item= (ErpUser) service.selectByPrimaryKey(id);
if(item!=null){
return new ResultBean<>(item);
}
else {
return new ResultBean<>(ExceptionEnum.RESOURCE_NOT_FOUND,null,"找不到该记录",null);
}
}


@RequestMapping(value = "/getlist",method = RequestMethod.POST)
@ResponseBody
public PageResultBean<List<ErpUser>> getList(int page,int limit, String searchKey, String searchValue){
List<ErpUser> list;
    PageHelper.startPage(page, limit);
    Example example = new Example(ErpUser.class);
    if(searchKey != null && !searchKey.trim().isEmpty() && searchValue != null && !searchValue.trim().isEmpty()) {
        example.createCriteria()
                .andEqualTo(searchKey, searchValue);
        list = service.selectByExample(example);
    } else {
        list = service.selectAll();
    }

    // 关联岗位
    List<String> userIds = new ArrayList<>();
    for(ErpUser one: list) {
        userIds.add(one.getId());
    }
    Example ruExample = new Example(ErpRoleUser.class);
    ruExample.createCriteria().andIn("userId", userIds);
    List<ErpRoleUser> rus = roleUserService.selectByExample(ruExample);
    List<ErpRole> roles = roleService.selectAll();
    for(ErpUser one: list) {
        List<ErpRole> temp = new ArrayList<>();
        for(ErpRoleUser ru: rus) {
            if(one.getId().equals(ru.getUserId())) {
                for(ErpRole p: roles) {
                    if(ru.getRoleId().equals(p.getId())) {
                        temp.add(p);
                    }
                }
            }
        }
        one.setRoles(temp);
    }
return new PageResultBean(list,page,limit, ((Page) list).getTotal());

}

@RequestMapping(value = "/create",method = RequestMethod.POST)
@ResponseBody
public ResultBean<String> create(@Validated ErpUser item, String roleIds){
    // 添加用户，需要设置对应的字段
    item.setPassword(Constants.DEFAULT_PASSWORD);
    ToolUtil.entryptPassword(item);
    item.setLocked(0);
    item.setEnabled(1);
    String[] split = roleIds.split(",");
    if(service.insertSelective(item) == 1) {
        List<ErpRoleUser> roleUsers = new ArrayList<>();
        for(String roleId: split) {
            ErpRoleUser roleUser = new ErpRoleUser();
            roleUser.setUserId(item.getId());
            roleUser.setRoleId(roleId);
            roleUsers.add(roleUser);
        }
        if(roleUserService.insertList(roleUsers) < 1) {
            throw new BusinessException("501", "添加失败");
        }
        return new ResultBean<String>("");
    }
    return  new ResultBean<String>(ExceptionEnum.BUSINESS_ERROR, "添加失败", "", "");
}

@RequestMapping(value = "/update",method = RequestMethod.POST)
@ResponseBody
public ResultBean<String> update(@Validated ErpUser item, String roleIds){
            service.updateByPrimaryKeySelective(item);
    String[] split = roleIds.split(",");
    if(service.updateByPrimaryKeySelective(item) == 1) {
        List<ErpRoleUser> roleUsers = new ArrayList<>();
        for(String roleId: split) {
            ErpRoleUser roleUser = new ErpRoleUser();
            roleUser.setUserId(item.getId());
            roleUser.setRoleId(roleId);
            roleUsers.add(roleUser);
        }
        Example ruExample = new Example(ErpRoleUser.class);
        ruExample.createCriteria().andEqualTo("userId", item.getId());
        roleUserService.deleteByExample(ruExample);
        if(roleUserService.insertList(roleUsers) < 1) {
            throw new BusinessException("501", "修改失败");
        }
        return new ResultBean<String>("");
    }
    return  new ResultBean<String>(ExceptionEnum.BUSINESS_ERROR, "修改失败", "", "");
}

@RequestMapping(value = "/deleteByID",method = RequestMethod.POST)
@ResponseBody
public ResultBean<Integer> delete(String id){
    int result= service.deleteByPrimaryKey(id);
    return new ResultBean<Integer>(result);
}

@RequestMapping(value = "/delete",method = RequestMethod.POST)
@ResponseBody
public ResultBean<Integer> delete( ErpUser item){
    int result= service.delete(item);
    return new ResultBean<Integer>(result);
}

    @GetMapping("index")
    public ModelAndView index(ModelAndView modelAndView){
        modelAndView.setViewName("/rbac/user/index");
        return modelAndView;
    }

@GetMapping("add")
public ModelAndView add(){
return new ModelAndView();
}

@GetMapping("edit")
public ModelAndView edit(String id){
ModelAndView modelAndView = new ModelAndView();
ErpUser item=(ErpUser)service.selectByPrimaryKey(id);
modelAndView.addObject("entity",item );
return modelAndView;
}

    @RequestMapping(value = "/state",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> updateState(String id, Integer state) {
        ErpUser user = service.selectByPrimaryKey(id);
        user.setEnabled(state);
        service.updateByPrimaryKey(user);
        return new ResultBean<String>("");
    }

    @RequestMapping(value = "/psw",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> updatePsw(String id) {
        ErpUser user = service.selectByPrimaryKey(id);
        user.setPassword(Constants.DEFAULT_PASSWORD);
        ToolUtil.entryptPassword(user);
        service.updateByPrimaryKey(user);
        return new ResultBean<String>("");
    }

    @RequestMapping(value = "/updatepsw",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> updatePassword(String oldPsw, String newPsw) {
//        User user = mapper.selectByPrimaryKey(MySysUser.id());

        ErpUser user = new ErpUser();
        //旧密码不能为空
        String pw = ToolUtil.entryptPassword(oldPsw, user.getSalt()).split(",")[0];
        if(!user.getPassword().equals(pw)){
            return  new ResultBean<String>(ExceptionEnum.BUSINESS_ERROR, "原密码不正确", "原密码不正确", "");
        }
        user.setPassword(newPsw);
        ToolUtil.entryptPassword(user);
        if (service.updateByPrimaryKey(user) == 1) {
            return new ResultBean<String>("");
        }
        return  new ResultBean<String>(ExceptionEnum.BUSINESS_ERROR, "修改密码失败", "修改密码失败", "");
    }

}