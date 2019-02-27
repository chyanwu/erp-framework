package com.chenyanwu.erp.erpframework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan(basePackages="com.chenyanwu.erp.erpframework.dao")
public class ErpFrameworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErpFrameworkApplication.class, args);
    }

}

