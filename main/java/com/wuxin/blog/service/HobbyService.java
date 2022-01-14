package com.wuxin.blog.service;

import com.wuxin.blog.pojo.blog.Hobby;
import com.wuxin.blog.mode.Base.BaseService;

import java.util.List;


/**
 * @Author: wuxin001
 * @Date: 2021/10/01/9:24
 * @Description:
 */
public interface HobbyService extends BaseService<Hobby> {

    /**
     * 查找用户兴趣爱好
     * @param userId userId
     * @return list
     */
    List<Hobby> selectListByUserId(Long userId);
}
