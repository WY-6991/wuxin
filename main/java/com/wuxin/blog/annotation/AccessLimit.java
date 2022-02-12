package com.wuxin.blog.annotation;

import java.lang.annotation.*;

/**
 * @Author: wuxin001
 * @Date: 2021/12/31/23:14
 * @Description: 表单限流
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {

    /**
     * 限制周期(秒)
     */
    int seconds() default 30;

    /**
     * 规定周期内限制次数
     */
    int limitCount() default 1;

    /**
     * 触发限制时的消息提示
     */
    String msg() default "操作频率过高";
}
