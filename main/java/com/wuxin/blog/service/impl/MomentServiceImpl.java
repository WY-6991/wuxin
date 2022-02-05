package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.MomentMapper;
import com.wuxin.blog.pojo.blog.Moment;
import com.wuxin.blog.pojo.blog.User;
import com.wuxin.blog.redis.RedisKey;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.service.MomentService;
import com.wuxin.blog.service.UserService;
import com.wuxin.blog.utils.ThrowUtils;
import com.wuxin.blog.utils.mapper.MapperUtils;
import com.wuxin.blog.utils.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2021/10/03/1:35
 * @Description:
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class MomentServiceImpl implements MomentService {

    private static final String MESSAGE = "操作失败！该动态不存在";

    @Autowired
    private MomentMapper momentMapper;

    @Autowired
    private UserService userService;


    @Autowired
    private RedisService redisService;


    @Override
    public void add(Moment moment) {
        deleteMomentCache();
        moment.setLikes(0);
        ThrowUtils.ops(momentMapper.insert(moment), "动态添加失败！");

    }


    @Override
    public void update(Moment moment) {
        deleteMomentCache();
        ThrowUtils.ops(momentMapper.updateById(moment), MESSAGE);
    }

    @Override
    public void delete(Long momentId) {
        deleteMomentCache();
        ThrowUtils.ops(momentMapper.deleteById(momentId), MESSAGE);
    }

    @Override
    public IPage<Moment> selectListByPage(Integer current, Integer limit) {
        boolean b = redisService.hHasKey(RedisKey.MOMENT_LIST, current + "");
        if (b) {
            IPage<Moment> page = (IPage<Moment>) redisService.hget(RedisKey.MOMENT_LIST, current + "");
            if (StringUtils.isNotNull(page) && page.getRecords().size() != 0) {
                return page;
            }
        }
        // 从数据库获取动态信息同时存入到redis中
        LambdaQueryChainWrapper<Moment> wrapper = new LambdaQueryChainWrapper<>(momentMapper);
        Page<Moment> page = new Page<>(current, limit);
        Page<Moment> momentPage = wrapper.orderByDesc(Moment::getCreateTime).page(page);
        // 获取用户名 用户头像等信息
        getUserNameAndAvatar(momentPage);
        // 存入redis
        redisService.hset(RedisKey.MOMENT_LIST, current, page);
        return page;
    }


    @Override
    public Moment find(Long momentId) {
        Moment moment = momentMapper.selectById(momentId);
        ThrowUtils.isNull(moment, "该动态不存在！");
        redisService.hdel(RedisKey.MOMENT_LIST, 1);
        return moment;
    }

    @Override
    public List<Moment> list() {
        ThrowUtils.ops(0, "该功能还未实现哦");
        return null;
    }


    @Override
    public IPage<Moment> selectListByPage(Integer current, Integer limit, String keywords) {
        ThrowUtils.ops(0, "该功能还未实现哦");
        return null;
    }


    @Override
    public IPage<Moment> selectListByPage(Integer current, Integer limit, String keywords, String start, String end) {
        Page<Moment> page = new Page<>(current, limit);
        Page<Moment> momentPage = MapperUtils.lambdaQueryWrapper(momentMapper)
                .orderByDesc(Moment::getCreateTime)
                .like(StringUtils.isNotNull(keywords), Moment::getContent, keywords)
                .le(StringUtils.isNotNull(end), Moment::getCreateTime, end)
                .ge(StringUtils.isNotNull(start), Moment::getCreateTime, start).page(page);
        getUserNameAndAvatar(momentPage);
        return momentPage;
    }


    /**
     * 获取用户信息
     *
     * @param page momentPage
     */
    public void getUserNameAndAvatar(Page<Moment> page) {
        page.getRecords().forEach(moment -> {
            User user = userService.finUserById(moment.getUserId());
            ThrowUtils.userNull(user);
            moment.setUsername(user.getNickname());
            moment.setAvatar(user.getAvatar());
        });
    }

    /**
     * 删除动态缓存 todo 这个方法需要优化！
     */
    public void deleteMomentCache() {
        Integer count = momentMapper.selectCount(null);
        Integer size = 5;
        int total = count / size + 1;
        for (Integer i = 1; i < total; i++) {
            boolean b = redisService.hHasKey(RedisKey.MOMENT_LIST, i);
            if (b) {
                redisService.hdel(RedisKey.MOMENT_LIST, i);
            }
        }
    }

}
