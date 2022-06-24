package com.example.common.config;

import com.example.common.config.service.UserDetailsServiceImpl;
import com.example.common.filter.AuthorizationFilter;
import com.example.common.handler.RestAuthenticationEntryPoint;
import com.example.common.handler.RestfulAccessDeniedHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * 继承自定义Security策略
 * 配置相关权限
 * 放行的资源 放行的请求  配置拦截器拦截
 */
@Slf4j
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    @Override
    //UserDetailsService用户信息获取逻辑
    protected UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthorizationFilter authorizationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭csrf功能，默认开启
        http.csrf().disable()  //
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests() //下面这些都是授权的配置
                .antMatchers(HttpMethod.GET, "/cache/**")
                .permitAll()
                .antMatchers("/api/*/anon/**", "/api/user/register", "/api/user/login", "/api/user/forgot")
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .anyRequest()   //任何请求
                .authenticated(); //需要身份认证

        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        http.headers().cacheControl();

        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    //处理密码加密
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置数据库认证
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());  //passwordEncoder密码加密
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);  //持久化token，保存到数据库
        return tokenRepository;
    }
}
