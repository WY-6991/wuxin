package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.MomentMapper;
import com.wuxin.blog.mapper.UserMapper;
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

    private final static String MESSAGE = "操作失败！该动态不存在";

    private final static String MOMENT_LIST = RedisKey.MOMENT_LIST;


    @Autowired
    private MomentMapper momentMapper;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private RedisService redisService;


    @Override
    public void add(Moment moment) {
        moment.setLikes(0);
        ThrowUtils.ops(momentMapper.insert(moment), "动态添加失败！");
        deleteMomentCache();

    }


    @Override
    public void update(Moment moment) {
        ThrowUtils.ops(momentMapper.updateById(moment), MESSAGE);
        deleteMomentCache();
    }

    @Override
    public void delete(Long momentId) {
        ThrowUtils.ops(momentMapper.deleteById(momentId), MESSAGE);
        deleteMomentCache();
    }

    @Override
    public IPage<Moment> selectListByPage(Integer current, Integer limit) {
        String hk = RedisKey.getKey(current, MOMENT_LIST);
        boolean b = redisService.hHasKey(MOMENT_LIST, hk);
        if (b) {
            IPage<Moment> page = (IPage<Moment>) redisService.hget(MOMENT_LIST, hk);
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
        redisService.hset(MOMENT_LIST, hk, page);
        return page;
    }


    @Override
    public Moment find(Long momentId) {
        Moment moment = momentMapper.selectById(momentId);
        ThrowUtils.isNull(moment, "该动态不存在！");
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
                .like(StringUtils.isNotEmpty(keywords), Moment::getContent, keywords)
                .le(StringUtils.isNotEmpty(end), Moment::getCreateTime, end)
                .ge(StringUtils.isNotEmpty(start), Moment::getCreateTime, start).page(page);
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
            User user = MapperUtils.lambdaQueryWrapper(userMapper)
                    .eq(User::getUserId, moment.getUserId()).select(
                            User::getUserId, User::getNickname, User::getAvatar).one();
            moment.setUsername(user.getNickname());
            moment.setAvatar(user.getAvatar());
        });
    }

    /**
     * 删除动态缓存
     */
    public void deleteMomentCache() {
        int count = momentMapper.selectCount(null);
        int size = 5;
        int delCount = count / size + 1;
        for (int i = 1; i < delCount; i++) {
            String hk = RedisKey.getKey(i, MOMENT_LIST);
            boolean b = redisService.hHasKey(RedisKey.MOMENT_LIST, hk);
            if (b) {
                redisService.hdel(RedisKey.MOMENT_LIST, hk);
            }

        }

    }

}
