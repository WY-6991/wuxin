package com.wuxin.blog.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Author: wuxin001
 * @Date: 2021/12/31/23:12
 * @Description: 自定义注解 记录操作日志
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLogger {

    /**
     * 操作描述
     */
    String value() default "";
}
