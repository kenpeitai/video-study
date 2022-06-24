package com.example.common.filter;

import com.example.biz.entity.SysAccessToken;
import com.example.biz.service.AccessTokenService;
import com.example.common.constants.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器中通过解析token判断是否已经登录，若登录，就放行，否则响应状态码
 * Component配置过滤器起作用
 */
@Slf4j
@Component
public class AuthorizationFilter extends OncePerRequestFilter {
    //OncePerRequestFilter能够确保在一次请求中只通过一次filter，而不需要重复的执行。
    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        //查询本HTTP请求Header中时候携带了Authorization参数，CommonConstant中存放了项目公共常量数据
        String token = httpServletRequest.getHeader(CommonConstant.AUTHORIZATION);
        if (token != null) {
            //查询这个Authorization参数的值是否是对应了一个已经登录用户的Token值
            SysAccessToken accessToken = accessTokenService.findByToken(token);
            if (accessToken != null) {
                //认证类获取当前用户名，从数据库当中查出用户名和密码，判断两个用户名是否相等，new一个认证器，把身份信息和凭证信息和当前用户名传入，，认证器内部判断
                UserDetails userDetails = userDetailsService.loadUserByUsername(accessToken.getUserName());
                // 获取到请求中的用户名和密码后，构造UsernamePasswordAuthenticationToken对象，进行登录的密码认证过程
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                //给details属性赋值
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); //更新用户信息
                //将身份认证通过后的账户信息存储大到Security的SecurityContextHolder对象中
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        //如果存在这个，那么Spring Security会认为该接口是已经认证过的，不会拦截该请求。放行
    }
}
