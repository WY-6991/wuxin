package com.wuxin.blog.util;


import com.wuxin.blog.pojo.CommentUser;
import com.wuxin.blog.service.CommentUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class CommentUtil {


    @Autowired
    private static CommentUserService commentUserService;

    @Value("${user.avatar.location}")
    private String avatar;


    public static Long getUserId(String username ,String email) {
        // CommentUserServiceImpl commentUserService = new CommentUserServiceImpl();
        log.info("username:{},email:{}",username,email);
        CommentUser commentUserByUsernameAndEmail = commentUserService.findCommentUserByUsernameOrEmail(username, email);
        log.info("commentUserByUsernameAndEmail:{}",commentUserByUsernameAndEmail==null);
        Long userId = null;
        // 判断用户是否存在
        if (commentUserByUsernameAndEmail == null) {
            CommentUser commentUser = new CommentUser();
            commentUser.setUsername(username);
            commentUser.setEmail(email);
            commentUser.setAvatar("https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202110/20211011221540.png");
            // 随机获取头像

            // 获取userId
            userId = commentUserService.addUser(commentUser);
            log.info("newUser userId:{}",userId);

        } else {
            // 判断用户名和邮箱是否输入正确
            CommentUser checkUsernameAndEmail = commentUserService.checkUsernameAndEmail(username, email);
            if (checkUsernameAndEmail != null) {
                userId = checkUsernameAndEmail.getUserId();
                log.info("getUser userId:{}",userId);
            }
        }

        log.info("return userId:{}",userId);
        return userId;
    }
}
