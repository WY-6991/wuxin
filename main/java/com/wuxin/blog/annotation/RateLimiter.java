package com.wuxin.blog.annotation;

/**
 * @Author: wuxin001
 * @Date: 2022/01/13/10:14
 * @Description:
 */

import com.wuxin.blog.enums.LimitType;

import java.lang.annotation.*;


/**
 * @Author: wuxin001
 * @Date: 2022/01/13/10:14
 * @Description: 访问限流
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    /**
     * 限流key
     */
    String key() default "rate_limit";

    /**
     * 限流时间,单位秒
     */
    int time() default 2;

    /**
     * 限流次数
     */
    int count() default 100;

    /**
     * 限流类型
     */
    LimitType limitType() default LimitType.DEFAULT;
}
