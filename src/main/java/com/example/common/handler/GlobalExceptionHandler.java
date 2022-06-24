package com.example.common.handler;

import com.example.common.constants.CommonConstant;
import com.example.common.exception.GlobalException;
import com.example.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
       //处理 Handler 中用 @ExceptionHandler 注解定义的方法
    @ExceptionHandler(value = Exception.class)
    public R exception(Exception e) {
        e.printStackTrace();
        return new R(e);
    }

    @ExceptionHandler(value = GlobalException.class)
    public R globalExceptionHandle(GlobalException e) {
        e.printStackTrace();
        return new R<>(CommonConstant.ERROR, e.getMsg());
    }
}
