package com.chenyanwu.erp.erpweb.controller.test;

import com.chenyanwu.erp.erpweb.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author chenyanwu
 * @Date 2019/1/14 10:36
 * @Version 1.0
 */
@RestController
@RequestMapping("/test")
public class TestController implements BaseController {

    @GetMapping("/test")
    public String test() {
        return "Hello, SpringBoot!";
    }
}
