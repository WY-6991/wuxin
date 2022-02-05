package com.wuxin.blog.service.impl;

import com.sun.istack.internal.NotNull;
import com.wuxin.blog.constant.Constants;
import com.wuxin.blog.constant.GlobalConstant;
import com.wuxin.blog.pojo.blog.Comment;
import com.wuxin.blog.pojo.blog.CommentReply;
import com.wuxin.blog.redis.RedisKey;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.service.BlogService;
import com.wuxin.blog.service.CommentService;
import com.wuxin.blog.service.MailService;
import com.wuxin.blog.service.UserService;
import com.wuxin.blog.utils.KeyUtil;
import com.wuxin.blog.utils.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:08
 * @Description:
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender mailSender;


    @Autowired
    private TemplateEngine templateEngine;


    @Autowired
    private RedisService redisService;


    @Value("${spring.mail.username}")
    private String from;


    @Value("${email.valid.time}")
    private Long time;


    @Autowired
    private CommentService commentService;


    @Autowired
    private UserService userService;


    @Autowired
    private BlogService blogService;


    @Override
    public void sendMimeMail(String email) {
        String code = KeyUtil.keyUtils();
        System.out.println("code=>" + code);
        // 使用redis作为缓存 验证码有效时间我10分钟！
        redisService.hset(RedisKey.EMAIL_CODE, email, code, time);
        String subject = "验证码邮件";
        String text = "您收到的验证码是: " + code + " ,有效时间为10分钟,如果非本人操作,请忽略!";
        simpleMail(email, subject, text);
    }


    @Override
    public void pubMessage(Comment comment) {
        // 自己的评论不需要推送
        if (comment.getCommentUserId().equals(GlobalConstant.ADMIN_USER_ID)) {
            return;
        }
        String subject = "文章新评论";
        String templatePath = "mine.html";
        String urlPrefix = "https://localhost:8080";
        String path = null;
        String title = null;
        String date = DateUtils.localTime();
        if (comment.getType().equals(Comment.BLOG_COMMENT)) {
            path = "/blog/" + comment.getBlogId();
            // 获取文章标题
            title = blogService.findCommentBlogTitle(comment.getBlogId());
        } else if (comment.getType().equals(Comment.ABOUT_COMMENT)) {
            path = "/about";
            title = "关于我";
        } else if (comment.getType().equals(Comment.FRIEND_COMMENT)) {
            path = "/friend";
            title = "友情链接";
        }

        Map<String, Object> map = new HashMap<>(Constants.HASH_MAP_INIT);
        map.put("date", date);
        map.put("title", title);
        map.put("url", urlPrefix + path);
        map.put("adminUrl", urlPrefix + "/admin/" + path);
        map.put("username", comment.getUsername());
        map.put("content", comment.getContent());
        // 邮件发送
        mailTemplateEngine("1019395329@qq.com", subject, map, templatePath);

    }

    @Override
    public void pubMessage(CommentReply reply) {
        // 如果是自己@自己，该条评论回复不需要推送
        if (reply.getReplyUserId().equals(reply.getCommentUserId())) {
            return;
        }
        String subject = "评论回复邮件";
        String templatePath = "email.html";
        String urlPrefix = "https://localhost:8080";
        String path = null;
        String title = null;
        String date = DateUtils.localTime();
        // 文章
        if (reply.getType().equals(Comment.BLOG_COMMENT)) {
            path = "/blog/" + reply.getBlogId();
            title = blogService.findCommentBlogTitle(reply.getBlogId());
            //
        } else if (reply.getType().equals(Comment.ABOUT_COMMENT)) {
            //
            path = "/about";
            title = "关于我";

        } else if (reply.getType().equals(Comment.FRIEND_COMMENT)) {
            path = "/friend";
            title = "友情链接";
        } else {
            return;
        }


        Map<String, Object> map = new HashMap<>(Constants.HASH_MAP_INIT);
        // 文章链接
        map.put("url", urlPrefix + path);
        // 标题
        map.put("title", title);
        // 评论内容
        map.put("comment", title);
        // 被回复用户
        map.put("nickname", userService.findUserDetail(GlobalConstant.ADMIN_USER_ID).getNickname());
        // 评论用户
        map.put("commentUsername", reply.getReplyUsername());
        // 回复内容
        map.put("reply", reply.getReplyContent());
        // 日期
        map.put("date", date);
        mailTemplateEngine("1019395329@qq.com", subject, map, templatePath);
    }


    /**
     * 发送简单邮箱
     */
    public void simpleMail(@NotNull String email, @NotNull String subject, @NotNull String content) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject(subject);
            mailMessage.setText(content);
            mailMessage.setFrom(from);
            mailMessage.setTo(email);
            mailSender.send(mailMessage);
        } catch (MailException e) {
            e.printStackTrace();
        }

    }


    /**
     * 使用模板引擎发送html模板邮件信息
     */
    public void mailTemplateEngine(@NotNull String email, @NotNull String subject, @NotNull Map<String, Object> map, @NotNull String templatePath) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            Context context = new Context();
            context.setVariables(map);
            String text = templateEngine.process(templatePath, context);
            messageHelper.setFrom(from);
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            messageHelper.setText(text, true);
            // 邮件发送
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
