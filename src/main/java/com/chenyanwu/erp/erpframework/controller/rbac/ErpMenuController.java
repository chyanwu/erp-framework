package com.chenyanwu.erp.erpframework.controller.rbac;

import com.chenyanwu.erp.erpframework.common.PageResultBean;
import com.chenyanwu.erp.erpframework.common.ResultBean;
import com.chenyanwu.erp.erpframework.entity.vo.ShowMenu;
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
import com.chenyanwu.erp.erpframework.service.rbac.ErpMenuService;
import com.chenyanwu.erp.erpframework.entity.rbac.ErpMenu;

import java.util.List;

/**
* <p>
    * </p>
*
* @author chenyanwu
* @date 2019-02-27 11:15:03
* @version
*/
@Controller

@RequestMapping(value = "/erpmenu")
public class ErpMenuController {

Logger logger = LoggerFactory.getLogger(this.getClass());
@Autowired
private ErpMenuService service;

@RequestMapping(value = "/get",method = RequestMethod.GET)
@ResponseBody
public ResultBean<ErpMenu> get(String id){
ErpMenu item= (ErpMenu) service.selectByPrimaryKey(id);
if(item!=null){
return new ResultBean<>(item);
}
else {
return new ResultBean<>(ExceptionEnum.RESOURCE_NOT_FOUND,null,"找不到该记录",null);
}
}


@RequestMapping(value = "/getlist",method = RequestMethod.POST)
@ResponseBody
public ResultBean<List<ErpMenu>> getList(){
List<ErpMenu> list = service.selectAll();
return new ResultBean<List<ErpMenu>>(list);

}

@RequestMapping(value = "/create",method = RequestMethod.POST)
@ResponseBody
public ResultBean<String> create(@Validated ErpMenu item){
    if("".equals(item.getParentId())) {
        item.setParentId("0");
    }
    service.insertSelective(item);
    return new ResultBean<String>("");
}

@RequestMapping(value = "/update",method = RequestMethod.POST)
@ResponseBody
public ResultBean<String> update(@Validated ErpMenu item){
            service.updateByPrimaryKeySelective(item);
    return new ResultBean<String>("");
}

@RequestMapping(value = "/deleteByID",method = RequestMethod.POST)
@ResponseBody
public ResultBean<Integer> delete(String id){
    int result= service.deleteByPrimaryKey(id);
    return new ResultBean<Integer>(result);
}

@RequestMapping(value = "/delete",method = RequestMethod.POST)
@ResponseBody
public ResultBean<Integer> delete(ErpMenu item){
    int result= service.delete(item);
    return new ResultBean<Integer>(result);
}

    @GetMapping("index")
    public ModelAndView index(ModelAndView modelAndView){
        modelAndView.setViewName("/rbac/menu/index");
        return modelAndView;
    }

@GetMapping("add")
public ModelAndView add(){
return new ModelAndView();
}

@GetMapping("edit")
public ModelAndView edit(String id){
ModelAndView modelAndView = new ModelAndView();
ErpMenu item=(ErpMenu)service.selectByPrimaryKey(id);
modelAndView.addObject("entity",item );
return modelAndView;
}

    @PostMapping(value = "/gettree")
    @ResponseBody
    public String getTreeMenuList(String id) {
        return service.getTreeMenuList(id);
    }

    /***
     * 获得用户所拥有的菜单列表
     * @return
     */
    @GetMapping("/getUserMenu")
    @ResponseBody
    public ResultBean<List<ShowMenu>> getUserMenu(){
//        String userId = MySysUser.id();//shiro有一个Util是可以拿到新用户信息的，不需要MySysUser
        List<ShowMenu> list = service.getShowMenuByUser("1");
        return new ResultBean<List<ShowMenu>>(list);
    }

}