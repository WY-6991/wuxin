package com.wuxin.blog.enums;

/**
 * @Author: wuxin001
 * @Date: 2022/01/13/10:27
 * @Description:
 */
public enum LimitType {

    /**
     * 默认策略全局限流
     */
    DEFAULT,

    /**
     * 根据请求者IP进行限流
     */
    IP
}
