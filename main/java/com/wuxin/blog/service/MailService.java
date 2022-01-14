package com.wuxin.blog.service;

import javax.servlet.http.HttpSession;


/**
 * @Author: wuxin001
 * @Date: 2021/10/03/1:35
 * @Description:
 */
public interface MailService {

    /**
     * 发送邮箱验证码
     * @param email 邮箱
     * @param session session
     */
    void sendMimeMail(String email, HttpSession session);
}
