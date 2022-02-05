package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.FriendMapper;
import com.wuxin.blog.pojo.blog.Friend;
import com.wuxin.blog.redis.RedisKey;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.service.FriendService;
import com.wuxin.blog.utils.ThrowUtils;
import com.wuxin.blog.utils.mapper.MapperUtils;
import com.wuxin.blog.utils.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/10:57
 * @Description:
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private RedisService redisService;

    private static final String REDIS_KEY = RedisKey.FRIEND_LIST;

    @Override
    public void add(Friend friend) {
        ThrowUtils.ops(friendMapper.insert(friend), "友情链接添加失败！");
        // 从list中获取数据
        List<Friend> list = list();
        list.add(friend);
        // 重新设置redis中数据
        redisService.set(REDIS_KEY,list);
    }

    @Override
    public void update(Friend friend) {
        ThrowUtils.ops(friendMapper.updateById(friend), "该链接不存在！");
        // 修改redis中数据
        List<Friend> list = list();
        for (int i = 0; i < list.size(); i++) {
            if(friend.getFriendId().equals(list.get(i).getFriendId())){
                // 移除指定内容
                list.remove(i);
                // 将新的内容添加到list中
                list.add(friend);
                // 将修改后的内容添加到redis中
                redisService.set(REDIS_KEY,list);
            }
        }

    }

    @Override
    public void delete(Long friendId) {
        ThrowUtils.ops(friendMapper.deleteById(friendId), "该链接不存在！");
        // 删除redis中指定连接
        List<Friend> list = list();
        for (int i = 0; i < list.size(); i++) {
            if(friendId.equals(list.get(i).getFriendId())){
                // 移除redis中指定list
                list.remove(i);
                // 重新设置redis中数据
                redisService.set(REDIS_KEY,list);
            }
        }
    }

    @Override
    public IPage<Friend> selectListByPage(Integer current, Integer limit, String keywords) {
        return MapperUtils.lambdaQueryWrapper(friendMapper)
                .like(StringUtils.isNotNull(keywords), Friend::getUsername, keywords)
                .page(new Page<>(current, limit));
    }


    @Override
    public Friend find(Long id) {
        return null;
    }

    @Override
    public List<Friend> list() {
        boolean b = redisService.hasKey(REDIS_KEY);
        if (b) {
            List<Friend> friendList = (List<Friend>) redisService.get(REDIS_KEY);
            if (friendList.size() != 0) {
                return friendList;
            }
        }
        // 从数据库中查找
        List<Friend> friendList = friendMapper.selectList(null);
        System.out.println("===========================mysql list friend=============================");
        redisService.set(REDIS_KEY,friendList);
        return friendList;
    }

    @Override
    public IPage<Friend> selectListByPage(Integer current, Integer limit, String keywords, String start, String end) {
        return MapperUtils.lambdaQueryWrapper(friendMapper).orderByDesc(Friend::getCreateTime)
                .like(StringUtils.isNotNull(keywords), Friend::getUsername, keywords)
                .le(StringUtils.isNotNull(end), Friend::getCreateTime, end)
                .ge(StringUtils.isNotNull(start), Friend::getCreateTime, start).page(new Page<>(current, limit));
    }


    @Override
    public IPage<Friend> selectListByPage(Integer current, Integer limit) {
        return null;
    }


}
