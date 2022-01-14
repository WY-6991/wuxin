package com.wuxin.blog.annotation;

/**
 * @Author: wuxin001
 * @Date: 2021/12/31/23:14
 * @Description: 防止一次性操作过多
 */
public @interface AccessLimit {

    /**
     * 限制周期(秒)
     */
    int seconds();

    /**
     * 规定周期内限制次数
     */
    int maxCount();

    /**
     * 触发限制时的消息提示
     */
    String msg() default "操作频率过高";
}
