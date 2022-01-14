package com.wuxin.blog.service;

import com.wuxin.blog.pojo.log.AccessLog;
import com.wuxin.blog.mode.Base.LogService;

/**
 * @Author: wuxin001
 * @Date: 2022/01/06/21:49
 * @Description:
 */
public interface AccessLogService extends LogService<AccessLog> {


    /**
     * 实现今日访客统计
     * @return 统计数量
     */
    Integer selectTodayAccessLog();
}
