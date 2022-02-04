package com.wuxin.blog.service;

import com.wuxin.blog.pojo.blog.Comment;
import com.wuxin.blog.pojo.blog.CommentReply;

import javax.servlet.http.HttpSession;


/**
 * @Author: wuxin001
 * @Date: 2021/10/03/1:35
 * @Description:
 */
public interface MailService {

    /**
     * 发送邮箱验证码
     *
     * @param email   邮箱
     */
    void sendMimeMail(String email);


    /**
     * 订阅回复内容
     * @param email 用户邮箱
     * @param nickname 评论用户昵称
     * @param message 评论消息内容
     */
    // void pubMessage(String replyUsername,String content,String email);


    /**
     * 评论消息发布
     *
     * @param comment 评论
     */
    void pubMessage(Comment comment);


    /**
     * 回复消息发布
     *
     * @param reply 回复内容
     */
    void pubMessage(CommentReply reply);


}
