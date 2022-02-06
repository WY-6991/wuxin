package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.LoginLogMapper;
import com.wuxin.blog.pojo.log.LoginLog;
import com.wuxin.blog.service.LoginLogService;
import com.wuxin.blog.utils.ThrowUtils;
import com.wuxin.blog.utils.mapper.MapperUtils;
import com.wuxin.blog.utils.string.StringUtils;
import com.wuxin.blog.utils.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2022/01/06/23:29
 * @Description:
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public void add(LoginLog loginLog) {
        ThrowUtils.ops(loginLogMapper.insert(loginLog),"添加失败");
    }


    @Override
    public List<LoginLog> list() {
        return null;
    }

    @Override
    public void delete(Long id) {
        ThrowUtils.ops(loginLogMapper.deleteById(id),"日志不存在！");
    }

    @Override
    public void deleteAll() {
        loginLogMapper.delete(null);
    }

    @Override
    public void batchDelete() {
        ThrowUtils.ops(0,"该接口功能还未实现哦");
    }

    @Override
    public IPage<LoginLog> selectListByPage(Integer current, Integer limit, String keywords, String start, String end) {
        Page<LoginLog> page = new Page<>(current, limit);
        return MapperUtils.lambdaQueryWrapper(loginLogMapper)
                .orderByDesc(LoginLog::getCreateTime)
                .like(StringUtils.isNotNull(keywords), LoginLog::getByCreate, keywords)
                .le(StringUtils.isNotNull(end), LoginLog::getCreateTime, end)
                .ge(StringUtils.isNotNull(start), LoginLog::getCreateTime, start).page(page);
    }


    @Override
    public Integer selectTodayLoginLog() {
        // 获取当前时间
        String localTime = DateUtils.localTime();
        // 获取凌晨时间
        String todayStartTime = DateUtils.todayStartTime();
        MapperUtils.lambdaQueryWrapper(loginLogMapper)
                // 小于当前时间
                .le(LoginLog::getCreateTime, localTime)
                // 大于今日凌晨时间
                .ge(LoginLog::getCreateTime, todayStartTime)
                // .select("DISTINCT username").eq()
        ;



        return null;
    }
}
