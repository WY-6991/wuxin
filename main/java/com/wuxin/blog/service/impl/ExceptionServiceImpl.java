package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.ExceptionLogMapper;
import com.wuxin.blog.pojo.ExceptionLog;
import com.wuxin.blog.service.ExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ExceptionServiceImpl implements ExceptionService {


    @Autowired
    private ExceptionLogMapper exceptionLogMapper;


    @Override
    public int addException(ExceptionLog exceptionLog) {
        return exceptionLogMapper.insert(exceptionLog);
    }

    @Override
    public int delException(Long id) {
        return exceptionLogMapper.deleteById(id);
    }

    @Override
    public IPage<ExceptionLog> findExceptionLog(int current, int limit) {
        Page<ExceptionLog> exceptionLogPage = new Page<>(current,limit);
        LambdaQueryChainWrapper<ExceptionLog> queryChainWrapper = new LambdaQueryChainWrapper<>(exceptionLogMapper);
        return queryChainWrapper.orderByDesc(ExceptionLog::getCreateTime).page(exceptionLogPage);
    }
}
