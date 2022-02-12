package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.ExceptionLogMapper;
import com.wuxin.blog.pojo.log.ExceptionLog;
import com.wuxin.blog.service.ExceptionService;
import com.wuxin.blog.utils.ThrowUtils;
import com.wuxin.blog.utils.mapper.MapperUtils;
import com.wuxin.blog.utils.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:08
 * @Description:
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class ExceptionLogServiceImpl implements ExceptionService {


    @Autowired
    private ExceptionLogMapper exceptionLogMapper;


    @Override
    public void add(ExceptionLog exceptionLog) {
        ThrowUtils.ops(exceptionLogMapper.insert(exceptionLog),"异常日志添加失败！");
    }


    @Override
    public void delete(Long id) {
        ThrowUtils.ops(exceptionLogMapper.deleteById(id) ,"日志不存在！");
    }

    @Override
    public void deleteAll() {
        exceptionLogMapper.delete(null);
    }




    @Override
    public void batchDelete() {
        ThrowUtils.isNull(0,"该功能还未实现哦！");
    }


    @Override
    public List<ExceptionLog> list() {
        return null;
    }

    @Override
    public IPage<ExceptionLog> selectListByPage(Integer current, Integer limit, String keywords, String start, String end) {
        Page<ExceptionLog> page = new Page<>(current, limit);
        return MapperUtils.lambdaQueryWrapper(exceptionLogMapper)
                .orderByDesc(ExceptionLog::getCreateTime)
                .eq(StringUtils.isNotEmpty(keywords), ExceptionLog::getByCreate, keywords)
                .le(StringUtils.isNotEmpty(end), ExceptionLog::getCreateTime, end)
                .ge(StringUtils.isNotEmpty(start), ExceptionLog::getCreateTime, start).page(page);
    }
}
