package com.wuxin.blog.service;

import com.wuxin.blog.pojo.blog.ChatUrl;


/**
 * @Author: wuxin001
 * @Date: 2021/10/01/9:24
 * @Description:
 */
public interface ChatUrlService {


    /**
     * 我的相关信息
     * @param chatUrl DTO
     */
    void updateChatUrl(ChatUrl chatUrl);


    /**
     *  根据用户id查询我的信息
     * @param userId id
     * @return DTO
     */
    ChatUrl findChatUrlByUserId(Long userId);
}
