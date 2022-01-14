package com.wuxin.blog.controller.front.comment;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.pojo.blog.Comment;
import com.wuxin.blog.pojo.blog.CommentReply;
import com.wuxin.blog.service.CommentService;
import com.wuxin.blog.service.UserService;
import com.wuxin.blog.enums.Message;
import com.wuxin.blog.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
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
    CommentService commentService;

    @Resource
    UserService userService;


    @OperationLogger("获取文章评论")
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
    @OperationLogger("添加评论")
    @PostMapping("/add")
    public Result addComment(@RequestBody Comment comment) {
        if (!((comment.getType().equals(Comment.BLOG_COMMENT))
                || (comment.getType().equals(Comment.ABOUT_COMMENT))
                || (comment.getType().equals(Comment.FRIEND_COMMENT))
        )) {
            return Result.error("获取添加失败！获取不到评论类型");
        }

        Long userId = userService.getUserId(comment.getUsername(), comment.getEmail());
        if (userId == null) {
            return Result.error("请检查用户名或者邮箱是否输入正确!可能已被使用了哦!");
        }
        comment.setCommentUserId(userId);
        commentService.addComment(comment);
        return Result.ok(userId);
    }


    /**
     * 博客评论回复
     *
     * @param commentReply reply
     * @return success
     */
    @OperationLogger("回复一条评论")
    @PostMapping("/reply/add")
    public Result addReply(@RequestBody CommentReply commentReply) {
        if (!((commentReply.getType().equals(Comment.BLOG_COMMENT))
                || (commentReply.getType().equals(Comment.ABOUT_COMMENT))
                || (commentReply.getType().equals(Comment.FRIEND_COMMENT))
        )) {
            return Result.error("添加添加失败！获取不到评论类型");
        }
        log.info("add reply:{}", commentReply);

        Long userId = userService.getUserId(commentReply.getReplyUsername(), commentReply.getReplyEmail());
        if (userId == null) {
            return Result.error("请检查用户名或者邮箱是否输入正确!可能已被使用了哦!");
        }
        if (commentReply.getCommentUserId() == null) {
            return Result.error("评论失败!获取不到评论用户哦");
        }
        commentReply.setReplyUserId(userId);
        commentService.addReply(commentReply);
        return Result.ok(userId);
    }

    /**
     * 删除评
     *
     * @param cid cid
     * @return result
     */
    @OperationLogger("删除评论")
    @DeleteMapping("/del")
    public Result delComment(@RequestParam("cid") Long cid) {
        commentService.delComment(cid);
        return Result.ok(Message.DEL_SUCCESS.getMessage());
    }


    /**
     * 删除回复
     *
     * @param replyId replyId
     * @return result
     */
    @OperationLogger("删除回复")
    @DeleteMapping("/del/reply")
    public Result delReply(@RequestParam("replyId") Long replyId) {
        commentService.delReply(replyId);
        return Result.ok("删除成功");
    }

    @OperationLogger("获取文章评论")
    @DeleteMapping("/all")
    public Result allBlogComment(@RequestParam("blogId") Long blogId) {
        log.info("all blogCommentList:{}", commentService.allBlogComment(blogId));
        return Result.ok(commentService.allBlogComment(blogId));
    }


}
