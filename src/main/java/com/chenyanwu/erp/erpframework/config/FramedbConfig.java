package com.chenyanwu.erp.erpframework.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * @Auther: chenyanwu
 * @Date: 2019/2/27 23:50
 * @Description:
 * @Version 1.0
 */
@Configuration
@MapperScan(basePackages = "com.chenyanwu.erp.erpframework.dao", sqlSessionTemplateRef = "framedbSqlSessionTemplate")
public class FramedbConfig {
    @Autowired
    private SqlInterceptor sqlInterceptor;

    @Bean(name = "framedbDataSource")
    @ConfigurationProperties(prefix = "first.spring.datasource")

    public DataSource framedbDataSource() {
        return new DruidDataSource();
    }


    @Bean(name = "framedbSqlSessionFactory")

    public SqlSessionFactory testSqlSessionFactory(@Qualifier("framedbDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setPlugins(new Interceptor[]{ sqlInterceptor});

        bean.setDataSource(dataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        Resource[] res = resolver.getResources("classpath:mapper/**");
        for (int i =0;i< res.length;i++) {
            if(res[i].getFilename().indexOf("xml")==-1){
                res[i] = null;
            }
        }
        bean.setMapperLocations(res);

        return bean.getObject();
    }


    @Bean(name = "framedbTransactionManager")

    public DataSourceTransactionManager testTransactionManager(@Qualifier("framedbDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "framedbSqlSessionTemplate")

    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("framedbSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
