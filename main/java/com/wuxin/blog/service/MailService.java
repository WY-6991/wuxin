package com.wuxin.blog.service;

import javax.servlet.http.HttpSession;

public interface MailService {

    /**
     * 发送邮箱
     * @param email 用户有邮箱
     * @param session session
     * @return 是否发送
     */
    boolean sendMimeMail(String email, HttpSession session);
}
