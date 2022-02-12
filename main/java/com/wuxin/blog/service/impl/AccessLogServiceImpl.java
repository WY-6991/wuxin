package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.AccessLogMapper;
import com.wuxin.blog.pojo.log.AccessLog;
import com.wuxin.blog.service.AccessLogService;
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
 * @Date: 2022/01/06/21:51
 * @Description:
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class AccessLogServiceImpl implements AccessLogService {

    @Autowired
    AccessLogMapper accessLogMapper;

    @Override
    public void add(AccessLog accessLog) {
        ThrowUtils.ops(accessLogMapper.insert(accessLog), "访问日志添加失败");
    }


    @Override
    public List<AccessLog> list() {
        ThrowUtils.ops(0, "该功能还未实现哦！");
        return null;
    }

    @Override
    public void deleteAll() {
        accessLogMapper.delete(null);
    }

    @Override
    public void batchDelete() {
        ThrowUtils.ops(0, "该功能还未实现哦！");
    }

    @Override
    public void delete(Long id) {
        ThrowUtils.ops(accessLogMapper.deleteById(id), "日志不存在！");
    }


    @Override
    public IPage<AccessLog> selectListByPage(Integer current, Integer limit, String keywords, String start, String end) {
        Page<AccessLog> page = new Page<>(current, limit);
        return MapperUtils.lambdaQueryWrapper(accessLogMapper)
                .orderByDesc(AccessLog::getCreateTime)
                .eq(StringUtils.isNotEmpty(keywords), AccessLog::getByCreate, keywords)
                .le(StringUtils.isNotEmpty(end), AccessLog::getCreateTime, end)
                .ge(StringUtils.isNotEmpty(start), AccessLog::getCreateTime, start).page(page);
    }


    @Override
    public Integer selectTodayAccessLog() {
        // 获取当前时间
        String localTime = DateUtils.localTime();
        // 获取凌晨时间
        String todayStartTime = DateUtils.todayStartTime();
        MapperUtils.lambdaQueryWrapper(accessLogMapper)
                // 小于当前时间
                .le(AccessLog::getCreateTime, localTime)
                // 大于今日凌晨时间
                .ge(AccessLog::getCreateTime, todayStartTime);
                // 指定字段去重处理



        return null;
    }
}
