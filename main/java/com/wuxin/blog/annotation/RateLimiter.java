package com.wuxin.blog.annotation;

/**
 * @Author: wuxin001
 * @Date: 2022/01/13/10:14
 * @Description:
 */

import com.wuxin.blog.constant.Constants;
import com.wuxin.blog.enums.LimitType;

import java.lang.annotation.*;


/**
 * @Author: wuxin001
 * @Date: 2022/01/13/10:14
 * @Description: 限流注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter
{
    /**
     * 限流key
     */
    public String key() default Constants.RATE_LIMIT_KEY;

    /**
     * 限流时间,单位秒
     */
    public int time() default 60;

    /**
     * 限流次数
     */
    public int count() default 100;

    /**
     * 限流类型
     */
    public LimitType limitType() default LimitType.DEFAULT;
}
