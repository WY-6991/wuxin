package com.wuxin.blog.service.impl;

import com.wuxin.blog.constant.GlobalConstant;
import com.wuxin.blog.exception.CustomException;
import com.wuxin.blog.service.MailService;
import com.wuxin.blog.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;


/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:08
 * @Description:
 */
@Service
public class MailServiceImpl implements MailService {


    @Autowired
    JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${email.valid.time}")
    private String time;

    @Override
    public void sendMimeMail(String email, HttpSession session) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            String code = KeyUtil.keyUtils();
            System.out.println("code=>" + code);
            //主题
            mailMessage.setSubject("验证码邮件");
            //将随机数放置到session中
            session.setAttribute(GlobalConstant.USER_VALID_EMAIL, email);
            session.setAttribute(GlobalConstant.USER_VALID_CODE, code);
            // 设置这个session有效时间 设置有效时间为10分钟
            session.setMaxInactiveInterval(Integer.parseInt(time));
            mailMessage.setText("您收到的验证码是: " + code + " ,有效时间为10分钟,如果非本人操作,请忽略!");
                //发给谁
            mailMessage.setTo(email);
                //你自己的邮箱
            mailMessage.setFrom(from);
                //发送
            mailSender.send(mailMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("邮箱发送失败~请重新尝试！");
        }
    }
}
