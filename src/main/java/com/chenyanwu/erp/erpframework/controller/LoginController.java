package com.chenyanwu.erp.erpframework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @GetMapping("/index")
    public ModelAndView index(ModelAndView modelAndView){
        return modelAndView;
    }

    @GetMapping("/index1")
    public ModelAndView index1(ModelAndView modelAndView){
        return modelAndView;
    }
}
