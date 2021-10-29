package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.LoginLog;

public interface LoginLogService {

    /**
     * 显示登录信息
     * @param current
     * @param limit
     * @return
     */
    IPage<LoginLog> findLoginList(Integer current, Integer limit);

    /**
     * 添加登录信息
     */
    int addLoginLog(LoginLog loginLog);


    /**
     * 删除
     * @param loginLogId
     * @return
     */
    int delLoginLog(Long loginLogId);
}
