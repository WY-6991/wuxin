package com.wuxin.blog.controller.blog;

import com.wuxin.blog.pojo.vo.BlogComment;
import com.wuxin.blog.pojo.CommentUser;
import com.wuxin.blog.pojo.vo.BlogCommentReply;
import com.wuxin.blog.service.BlogCommentService;
import com.wuxin.blog.service.CommentUserService;
import com.wuxin.blog.service.UserService;
import com.wuxin.blog.util.CommentUtil;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wuxin001
 * @Date: 2021/08/27/16:10
 * @Description:
 */
@Slf4j
@RequestMapping("/blog/comment")
@RestController
public class BlogCommentController {

    @Autowired
    private BlogCommentService blogCommentService;

    @Autowired
    private CommentUserService commentUserService;

    // @Autowired
    // private CommentReplyMapper commentUserService;


    @Autowired
    private UserService userService;



    /**
     * 未登录用户添加评论
     *
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



    // 根据blogId 分页显示blog comment
    @GetMapping("/list/{current}/{size}/{blogId}")
    public Result findBlogCommentByBlogId(@PathVariable("current") Integer current,
                                          @PathVariable("size") Integer size,
                                          @PathVariable("blogId") Long blogId) {


        return R.ok(blogCommentService.findBlogCommentByBlogId(current,size,blogId));
    }


    /**
     * 添加blog评论
     * @param blogComment comment
     * @return comment
     */
    @PostMapping("/add")
    public Result addBlogComment(@RequestBody BlogComment blogComment) {

        log.info("/comment/blog/add comment:{}", blogComment);

        Long userId = commentUserService.getUserId(blogComment.getUsername(), blogComment.getEmail());

        log.info("blogComment userId:{},userId is null:{}",userId,userId==null);
        if(userId == null){
            log.info("blogComment:{}","用户名或者邮箱输入错误");
            return R.error("请检查用户名或者邮箱!可能已被使用了哦!");
        }
        blogComment.setCommentUserId(userId);
        blogCommentService.addComment(blogComment);
        return R.ok(userId);
    }


    /**
     * 添加回复
     */
    @PostMapping("/add/reply")
    public Result addReply(@RequestBody BlogCommentReply blogCommentReply){

        log.info("comment reply add 1:{}", blogCommentReply);

        Long userId = commentUserService.getUserId(blogCommentReply.getReplyUsername(),blogCommentReply.getReplyEmail());
        if(userId == null){
            log.info("checkUsernameAndEmail:{}","用户名或者邮箱输入错误");
            return R.error("请检查用户名或者邮箱!可能已被使用了哦!");
        }
        blogCommentReply.setReplyUserId(userId);
        blogCommentService.addReply(blogCommentReply);
        log.info("comment reply add 2:{}", blogCommentReply);
        return R.ok(userId);
    }



    /**
     * 删除评论
     *
     * @param cid cid
     * @return result
     */
    @GetMapping("/del/{cid}")
    public Result delComment(@PathVariable("cid") Long cid) {
        if (blogCommentService.delComment(cid) == 1) return R.ok("success");
        return R.error("error");
    }


    /**
     * 删除回复
     *
     * @param replyId replyId
     * @return result
     */
    @GetMapping("/del/reply/{replyId}")
    public Result delReply(@PathVariable("replyId") Long replyId) {
        if (blogCommentService.delReply(replyId) == 1) return R.ok("success");
        return R.error("error");
    }




}
