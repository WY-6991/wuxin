package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.FriendMapper;
import com.wuxin.blog.pojo.Friend;
import com.wuxin.blog.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/10:57
 * @Description:
 */
@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendMapper friendMapper;

    @Override
    public void addFriend(Friend friend) {
        friendMapper.insert(friend);
    }

    @Override
    public void updateFriend(Friend friend) {
        new LambdaUpdateChainWrapper<Friend>(friendMapper).eq(Friend::getFriendId, friend.getFriendId()).update(friend);
    }

    @Override
    public void delFriend(int friendId) {
        friendMapper.deleteById(friendId);
    }

    @Override
    public IPage<Friend> findFriend(Integer current, Integer limit,String keywords) {
        LambdaQueryChainWrapper<Friend> queryChainWrapper = new LambdaQueryChainWrapper<>(friendMapper);
        Page<Friend> friendPage = new Page<>(current,limit);
        // 添加搜索用户用户名条件或者添加时间
        // Page<Friend> page = queryChainWrapper.like(!keywords.isEmpty(), Friend::getUsername, keywords).page(friendPage);
        return queryChainWrapper.like(!keywords.isEmpty(),Friend::getUsername,keywords).page(friendPage);
    }


}
