package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.Friend;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/10:57
 * @Description:
 */
public interface FriendService {

    void addFriend(Friend friend);

    void updateFriend(Friend friend);

    void delFriend(int friendId);

    IPage<Friend> findFriend(Integer current,Integer limit,String keywords);

    List<Friend> findAllFriendList();
}
