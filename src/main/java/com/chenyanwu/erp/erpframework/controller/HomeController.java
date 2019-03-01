package com.chenyanwu.erp.erpframework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @GetMapping("/home/index")
    public ModelAndView index1(ModelAndView modelAndView){
        return modelAndView;
    }
}
