package com.wuxin.blog.service;

import com.wuxin.blog.pojo.log.LoginLog;
import com.wuxin.blog.mode.Base.LogService;

/**
 * @Author: wuxin001
 * @Date: 2022/01/06/21:48
 * @Description:
 */
public interface LoginLogService extends LogService<LoginLog> {


    /**
     * 统计今日访客
     * @return int
     */
    public Integer selectTodayLoginLog();



}
