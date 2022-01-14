package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.OperationLogMapper;
import com.wuxin.blog.pojo.log.OperationLog;
import com.wuxin.blog.service.OperationLogService;
import com.wuxin.blog.utils.ThrowUtils;
import com.wuxin.blog.utils.mapper.MapperUtils;
import com.wuxin.blog.utils.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2022/01/06/23:40
 * @Description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public void add(OperationLog operationLog) {
        ThrowUtils.ops(operationLogMapper.insert(operationLog),"添加失败");
    }



    @Override
    public List<OperationLog> list() {
        ThrowUtils.ops(0,"该功能未实现");
        return null;
    }

    @Override
    public void delete(Long id) {
        ThrowUtils.ops(operationLogMapper.deleteById(id),"日志不存在");
    }

    @Override
    public void deleteAll() {
        operationLogMapper.delete(null);
    }

    @Override
    public void batchDelete() {
        ThrowUtils.ops(0,"该功能未实现");
    }

    @Override
    public IPage<OperationLog> selectListByPage(Integer current, Integer limit, String keywords, String start, String end) {
        Page<OperationLog> page = new Page<>(current, limit);
        return MapperUtils.lambdaQueryWrapper(operationLogMapper)
                .orderByDesc(OperationLog::getCreateTime)
                .like(StringUtils.isNotNull(keywords), OperationLog::getByCreate, keywords)
                .le(StringUtils.isNotNull(end), OperationLog::getCreateTime, end)
                .ge(StringUtils.isNotNull(start), OperationLog::getCreateTime, start).page(page);
    }
}
