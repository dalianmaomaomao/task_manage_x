package com.cj.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class PageInterceptorConfig {

    @Bean(name="pageHelperPageInterceptor")
    public com.github.pagehelper.PageInterceptor pageHelperPageInterceptor() {
        com.github.pagehelper.PageInterceptor pageInterceptor = new com.github.pagehelper.PageInterceptor();
        Properties p = new Properties();
        pageInterceptor.setProperties(p);
        return pageInterceptor;
    }
}