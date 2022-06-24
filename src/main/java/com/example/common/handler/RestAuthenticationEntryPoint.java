package com.example.common.handler;

import cn.hutool.json.JSONUtil;
import com.example.common.constants.CommonConstant;
import com.example.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义未登录或Token过期时经过该处理器
 * 验证失败或token过期就返回403，请求头中没有token也返回403和用户未登录信息
 */
@Slf4j
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSONUtil.toJsonStr(new R<>(403, CommonConstant.NO_LOGIN, e.getMessage())));
    }
}
