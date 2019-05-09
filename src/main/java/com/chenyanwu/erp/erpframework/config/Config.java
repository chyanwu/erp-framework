package com.chenyanwu.erp.erpframework.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;

/**
 * @ClassName Config
 * @Description TODO
 * @Author chenyanwu
 * @Date 2019/1/15 18:43
 * @Version 1.0
 */
@Configuration
@ComponentScan(basePackages = {"com.chenyanwu.erp.erpframework"})
public class Config {
//    @Bean
//    public DataSource dataSource() {
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        druidDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/erp-framework?useUnicode=true&characterEncoding=utf8");
//        druidDataSource.setUsername("root");
//        druidDataSource.setPassword("mysql");
//        return druidDataSource;
//    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize("102400KB");
        /// 总上传数据大小
        factory.setMaxRequestSize("512000KB");
        return factory.createMultipartConfig();
    }
}
