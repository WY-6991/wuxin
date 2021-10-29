package com.wuxin.blog.controller;

import com.wuxin.blog.pojo.Comment;
import com.wuxin.blog.pojo.CommentUser;
import com.wuxin.blog.pojo.User;
import com.wuxin.blog.pojo.vo.CommentReply;
import com.wuxin.blog.service.CommentService;
import com.wuxin.blog.service.CommentUserService;
import com.wuxin.blog.service.UserService;
import com.wuxin.blog.util.GlobalConstant;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @Author: wuxin001
 * @Date: 2021/08/27/16:10
 * @Description:
 */
@Slf4j
@RequestMapping("/comment")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentUserService commentUserService;

    // @Autowired
    // private CommentReplyMapper commentUserService;


    @Autowired
    private UserService userService;

    @Value("${user.avatar.location}")
    private String avatar;


    /**
     * 未登录用户添加评论
     *
     * @param comment 评论内容
     * @return result
     */
    // @PostMapping("/add/by/email")
    // public Result addComment(@RequestBody Comment comment) {
    //     // 判断邮箱是否存在
    //     User userByEmail = userService.findUserByEmail(comment.getEmail());
    //     // 如果为空 说明该用户还未注册 注册该用户
    //     User user = new User();
    //     if (userByEmail == null) {
    //         user.setEmail(comment.getEmail());
    //         user.setUsername(comment.getUsername());
    //         user.setPassword(comment.getUsername());
    //         userService.addUser(user); // 密码默认为用户名
    //         // 添加评论
    //         comment.setAvatar(avatar); // 默认用户头像
    //         commentService.addComment(comment);
    //         if (commentService.addComment(comment) == 1) return R.ok("评论添加成功");
    //         return R.error("error");
    //     } else {
    //         // 如果不为空，说明该有户已经注册了
    //         comment.setAvatar(userByEmail.getAvatar());
    //         commentService.addComment(comment);
    //         if (commentService.addComment(comment) == 1) return R.ok("评论添加成功");
    //         return R.error("error");
    //     }
    //
    //
    // }

    // /**
    //  * 已经登录了的用户
    //  *
    //  * @param comment comment
    //  * @return
    //  */
    // @PostMapping("/add/by/pass")
    // public Result addCommentPass(@RequestBody Comment comment, HttpSession session) {
    //     User user = (User) session.getAttribute(GlobalConstant.USER_LOGIN_SESSION);
    //     comment.setUsername(user.getNickname());
    //     comment.setEmail(user.getEmail());
    //     comment.setAvatar(user.getAvatar());
    //     if (commentService.addComment(comment) == 1) return R.ok("success");
    //     return R.error("error");
    // }

    // 判断是不是博主回复的内容

    /**
     * 删除评论
     *
     * @param cid cid
     * @return result
     */
    @PostMapping("/del/{cid}")
    public Result delComment(@PathVariable("cid") Long cid) {
        if (commentService.delComment(cid) == 1) return R.ok("success");
        return R.error("error");
    }

    // 根据blogId 分页显示blog comment
    @GetMapping("/blog/list/{current}/{size}/{blogId}")
    public Result findBlogCommentByBlogId(@PathVariable("current") Integer current,
                                          @PathVariable("size") Integer size,
                                          @PathVariable("blogId") Long blogId) {


        return R.ok(commentService.findBlogCommentByBlogId(current,size,blogId));
    }


    /**
     * 添加blog评论
     * @param comment comment
     * @return comment
     */
    @PostMapping("/blog/add")
    public Result addBlogComment(@RequestBody Comment comment) {

        log.info("/comment/blog/add comment:{}",comment);
        // 检查用户是否注册了
        CommentUser commentUser = commentUserService.findCommentUserByUsernameAndEmail(comment.getUsername(), comment.getEmail());

        log.info("commentUser:{}",commentUser);
        Long userId = null;
        if(commentUser == null){
            // 添加用户
            CommentUser newUser = new CommentUser();
            newUser.setUsername(comment.getUsername());
            newUser.setEmail(comment.getEmail());
            // 随机获取用户头像
            newUser.setAvatar(avatar);
            userId = commentUserService.addUser(newUser);
            // 添加用户id
            comment.setCommentUserId(userId);
        }else {
            CommentUser checkUsernameAndEmail = commentUserService.checkUsernameAndEmail(comment.getUsername(), comment.getEmail());
            if(checkUsernameAndEmail!=null){
                log.info("checkUsernameAndEmail:{}",checkUsernameAndEmail);
                comment.setCommentUserId(checkUsernameAndEmail.getUserId());
            }
            else {
                log.info("checkUsernameAndEmail:{}","用户名或者邮箱输入错误");
                return R.error("请重新检查用户或者邮箱!用户名或邮箱已被使用了哦!换个试试吧!");
            }

        }


        return R.ok(commentService.addComment(comment));
    }


    /**
     * 添加回复
     */
    @PostMapping("/blog/reply/add")
    public Result addReply(@RequestBody CommentReply commentReply){

        log.info("comment reply add 1:{}",commentReply);

        // 根据用户名和邮箱其中一个条件查找用户
        CommentUser commentUser = commentUserService.findCommentUserByUsernameAndEmail(commentReply.getReplyNickName(),commentReply.getReplyEmail());

        log.info("commentUser:{}",commentUser);
        Long userId = null;
        // 如果找不到 说明用户还未注册
        if(commentUser == null){
            // 添加用户
            CommentUser newUser = new CommentUser();
            newUser.setUsername(commentReply.getReplyNickName());
            newUser.setEmail(commentReply.getReplyEmail());
            // 随机获取用户头像
            newUser.setAvatar(avatar);
            userId = commentUserService.addUser(newUser);
            commentReply.setReplyUserId(userId);
        }else {
            // 如果能够找到一个用户,
            CommentUser checkUsernameAndEmail = commentUserService.checkUsernameAndEmail(commentReply.getReplyNickName(), commentReply.getReplyEmail());
            // 根据两个条件找到用户
            if(checkUsernameAndEmail!=null){
                log.info("checkUsernameAndEmail:{}",checkUsernameAndEmail);
                commentReply.setReplyUserId(checkUsernameAndEmail.getUserId());
            }else{   // 根据找不到用户说明输入错误
                log.info("checkUsernameAndEmail:{}","用户名或者邮箱输入错误");
                return  R.error("请重新检查用户或者邮箱!用户名或邮箱已被使用了哦!换个试试吧!");
            }


        }

        log.info("comment reply add 2:{}",commentReply);
        return R.ok(commentService.addReply(commentReply));
    }





}
