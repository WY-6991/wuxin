package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.wuxin.blog.mapper.ChatUrlMapper;
import com.wuxin.blog.mapper.UserMapper;
import com.wuxin.blog.pojo.blog.ChatUrl;
import com.wuxin.blog.service.ChatUrlService;
import com.wuxin.blog.utils.ThrowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:08
 * @Description:
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class ChatUrlServiceImpl implements ChatUrlService {

    @Autowired
    private ChatUrlMapper chatUrlMapper;


    @Autowired
    private UserMapper userMapper;


    @Override
    public void updateChatUrl(ChatUrl chatUrl) {
        ThrowUtils.isNull( chatUrl.getUserId(),"获取不到用户Id！");
        ThrowUtils.isNull( userMapper.selectById(chatUrl.getUserId()),"用户不存在！");
        ThrowUtils.ops( chatUrlMapper.updateById(chatUrl),"修改失败！");
    }


    @Override
    public  ChatUrl findChatUrlByUserId(Long userId) {
        LambdaQueryChainWrapper<ChatUrl> chatUrlLambdaQueryChainWrapper = new LambdaQueryChainWrapper<>(chatUrlMapper);
        ChatUrl chatUrl = chatUrlLambdaQueryChainWrapper.eq(ChatUrl::getUserId, userId).one();
        ThrowUtils.isNull(chatUrl,"用户拓展信息不存在,用户可能不存在！");
        return chatUrl;
    }
}
