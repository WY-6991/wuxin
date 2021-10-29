package com.wuxin.blog.service.impl;

import com.wuxin.blog.service.MailService;
import com.wuxin.blog.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;


@Service
public class MailServiceImpl implements MailService {


    @Autowired
    JavaMailSender mailSender;

    //application.properties中已配置的值
    @Value("${spring.mail.username}")
    private String from;

    @Value("${email.valid.time}")
    private String time;

    @Override
    public boolean sendMimeMail(String email, HttpSession session) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setSubject("验证码邮件");//主题
            //生成随机数
            String code = KeyUtil.keyUtils();
            System.out.println("code=>" + code);

            //将随机数放置到session中
            session.setAttribute("email", email);
            session.setAttribute("code", code);

            // 设置这个session有效时间 设置有效时间为10分钟
            session.setMaxInactiveInterval(Integer.parseInt(time));

            mailMessage.setText("您收到的验证码是: " + code + " ,有效时间为10分钟,如果非本人操作,请忽略!");//内容

            mailMessage.setTo(email);//发给谁

            mailMessage.setFrom(from);//你自己的邮箱

            mailSender.send(mailMessage);//发送
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
