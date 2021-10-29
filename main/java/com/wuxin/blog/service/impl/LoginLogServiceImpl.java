package com.wuxin.blog.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.service.LoginLogService;
import com.wuxin.blog.mapper.LoginLogMapper;
import com.wuxin.blog.pojo.LoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;


    @Override
    public int addLoginLog(LoginLog loginLog) {
        return loginLogMapper.insert(loginLog);
    }


    @Override
    public int delLoginLog(Long loginLogId) {
        return loginLogMapper.deleteById(loginLogId);
    }


    @Override
    public IPage<LoginLog> findLoginList(Integer current, Integer limit) {
        Page<LoginLog> loginLogPage = new Page<>(current,limit);
        LambdaQueryChainWrapper<LoginLog> queryChainWrapper = new LambdaQueryChainWrapper<>(loginLogMapper);
        return queryChainWrapper.orderByDesc(LoginLog::getCreateTime).page(loginLogPage);

    }



}
