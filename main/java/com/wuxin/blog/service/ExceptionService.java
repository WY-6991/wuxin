package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.ExceptionLog;

public interface ExceptionService {

    /**
     * 添加异常
     * @param exceptionLog
     * @return
     */
    int addException(ExceptionLog exceptionLog);

    /**
     * 删除
     * @param id
     * @return
     */
    int delException(Long id);

    /**
     * 显示日志
     * @param current
     * @param limit
     * @return
     */
    IPage<ExceptionLog> findExceptionLog(int current,int limit);
}
