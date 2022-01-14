package com.wuxin.blog.annotation;

import java.lang.annotation.*;

/**
 * @Author: wuxin001
 * @Date: 2022/01/13/10:14
 * @Description: 防止重复提交注解
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {
    /**
     * 间隔时间(ms)，小于此时间视为重复提交
     */
    public int interval() default 5000;

    /**
     * 提示消息
     */
    public String message() default "不允许重复提交，请稍候再试";
}
