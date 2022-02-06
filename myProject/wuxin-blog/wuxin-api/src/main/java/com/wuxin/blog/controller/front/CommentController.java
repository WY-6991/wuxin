package com.wuxin.blog.controller.front;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.enums.BusinessType;
import com.wuxin.blog.mode.UserComment;
import com.wuxin.blog.pojo.blog.Comment;
import com.wuxin.blog.pojo.blog.CommentReply;
import com.wuxin.blog.pojo.blog.User;
import com.wuxin.blog.service.CommentService;
import com.wuxin.blog.service.MailService;
import com.wuxin.blog.service.UserService;
import com.wuxin.blog.utils.result.Result;
import com.wuxin.blog.utils.string.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: wuxin001
 * @Date: 2022/01/02/0:00
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {


    @Resource
    private CommentService commentService;

    @Resource
    private UserService userService;


    @Autowired
    private MailService mailService;


    @GetMapping("/list")
    public Result findBlogComment(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                  @RequestParam(value = "limit", defaultValue = "5") Integer limit,
                                  @RequestParam(value = "blogId", defaultValue = "") Long blogId,
                                  @RequestParam(value = "type", defaultValue = "") Integer type) {


        if (!(type.equals(Comment.BLOG_COMMENT) || type.equals(Comment.ABOUT_COMMENT) || type.equals(Comment.FRIEND_COMMENT))) {
            return Result.error("获取不到评论类型");
        }


        return Result.ok(commentService.findCommentList(current, limit, blogId, type));
    }


    /**
     * 添加blog评论
     *
     * @param comment comment
     * @return comment
     */
    @OperationLogger(value = "添加评论", type = BusinessType.INSERT)
    @PostMapping("/add")
    public Result addComment(@RequestBody Comment comment) {
        if (!((comment.getType().equals(Comment.BLOG_COMMENT))
                || (comment.getType().equals(Comment.ABOUT_COMMENT))
                || (comment.getType().equals(Comment.FRIEND_COMMENT))
        )) {
            return Result.error("添加失败，获取不到评论类型！");
        }

        Long userId = userService.getCommentUserId(comment.getUsername(), comment.getEmail(), comment.isSubscription());
        if (StringUtils.isNull(userId)) {
            return Result.error("请检查用户名或者邮箱是否输入正确!可能已被使用了哦!");
        }
        comment.setCommentUserId(userId);
        // 添加评论
        commentService.addComment(comment);
        // 评论推送给我
        mailService.pubMessage(comment);
        return Result.ok("评论发布成功！");
    }


    /**
     * 博客评论回复
     *
     * @param commentReply reply
     * @return success
     */
    @OperationLogger(value = "添加回复", type = BusinessType.INSERT)
    @PostMapping("/reply/add")
    public Result addReply(@RequestBody CommentReply commentReply) {
        if (!((commentReply.getType().equals(Comment.BLOG_COMMENT))
                || (commentReply.getType().equals(Comment.ABOUT_COMMENT))
                || (commentReply.getType().equals(Comment.FRIEND_COMMENT))
        )) {
            return Result.error("添加添加失败！获取不到评论类型");
        }
        log.info("add reply:{}", commentReply);

        UserComment userComment = userService.getReplyCommentUser(commentReply.getReplyUsername(), commentReply.getReplyEmail(), commentReply.isSubscription(), UserComment.REPLY_TYPE);
        if (StringUtils.isNull(userComment.getUserId())) {
            return Result.error("请检查用户名或者邮箱是否输入正确!可能已被使用了哦!");
        }
        if (StringUtils.isNull(commentReply.getCommentUserId())) {
            return Result.error("评论失败!获取不到评论用户哦");
        }
        // 设置回复用户
        commentReply.setReplyUserId(userComment.getUserId());
        commentService.addReply(commentReply);


        User user = userService.selectCommentUserByUserId(commentReply.getCommentUserId());
        // 被回复用户是否需要发布消息
        if (user.isSubscription()) {
            // 发布给订阅用户
            mailService.pubMessage(commentReply);
            // TODO 是否需要将消息发布给该楼层楼主
        }

        // 评论消息发布给我
        // 你的文章有了信息评论
        // mailService.pubMessage(commentReply);

        return Result.ok("评论发布成功");
    }


}
