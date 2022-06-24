package com.example.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.biz.dao")
public class MybatisPlusConfig {

    /**
     * Mybatis-Plus 分页插件  先配置一个拦截器组件
     * 导入即可直接使用
     * 内置了Page对象，直接使用page对象实现分页
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
