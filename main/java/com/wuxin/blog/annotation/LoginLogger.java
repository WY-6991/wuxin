package com.wuxin.blog.annotation;

import com.wuxin.blog.enums.BusinessType;

import java.lang.annotation.*;

/**
 * @Author: wuxin001
 * @Date: 2022/02/12/10:14
 * @Description:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginLogger {

    /**
     * 操作描述
     */
    String value() default "登录";


}
