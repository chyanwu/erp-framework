package com.chenyanwu.erp.erpweb.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @ClassName Config
 * @Description TODO
 * @Author chenyanwu
 * @Date 2019/1/15 18:43
 * @Version 1.0
 */
@Configuration
@ComponentScan(basePackages = {"com.chenyanwu.erp.erpservice", "com.chenyanwu.erp.erpdao"})
public class Config {
    @Bean
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/erp-framework?useUnicode=true&characterEncoding=utf8");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("mysql");
        return druidDataSource;
    }
}
