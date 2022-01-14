package com.wuxin.blog.annotation;

import java.lang.annotation.*;

/**
 * @Author: wuxin001
 * @Date: 2022/01/12/17:57
 * @Description: 访问日志注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VisitLogger {

    String value() default "";
}
