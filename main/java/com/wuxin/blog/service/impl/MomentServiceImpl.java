package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.MomentMapper;
import com.wuxin.blog.mapper.UserMapper;
import com.wuxin.blog.pojo.Moment;
import com.wuxin.blog.pojo.User;
import com.wuxin.blog.service.MomentService;
import com.wuxin.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: wuxin001
 * @Date: 2021/10/03/1:35
 * @Description:
 */
@Service
public class MomentServiceImpl implements MomentService {


    @Autowired
    private MomentMapper momentMapper;

    @Autowired
    private UserService userService;

    @Transactional
    @Override
    public int addMoment(Moment moment) {
        moment.setLikes(0);
        return momentMapper.insert(moment);
    }

    @Transactional
    @Override
    public int updateMoment(Moment moment) {
        return momentMapper.updateById(moment);
    }

    @Transactional
    @Override
    public int delMoment(Long momentId) {
        return momentMapper.deleteById(momentId);
    }

    @Transactional
    @Override
    public IPage<Moment> findMoment(int current, int limit) {
        LambdaQueryChainWrapper<Moment> wrapper = new LambdaQueryChainWrapper<>(momentMapper);
        Page<Moment> momentPage = new Page<>(current,limit);
        Page<Moment> page = wrapper.orderByDesc(Moment::getCreateTime).page(momentPage);
        // 获取用户名 用户头像等信息
        page.getRecords().forEach(moment->{
            User user = userService.finUserById(moment.getUserId());
            moment.setUsername(user.getNickname());
            moment.setAvatar(user.getAvatar());
        });
        return page;
    }


    @Transactional
    @Override
    public Moment momentDetail(Long momentId) {
        return momentMapper.selectById(momentId);
    }
}
