package com.wuxin.blog.controller.admin.comment;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.pojo.blog.Comment;
import com.wuxin.blog.pojo.blog.CommentReply;
import com.wuxin.blog.mode.PageVo;
import com.wuxin.blog.service.CommentService;
import com.wuxin.blog.enums.Message;
import com.wuxin.blog.utils.result.Result;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: wuxin001
 * @Date: 2021/09/01/15:51
 * @Description: 评论管理
 */
@RestController
@RequestMapping("/admin/comment")
public class AdminCommentController {

    @Resource
    private CommentService commentService;




    /**
     * 评论分页 附带搜索
     *
     * @param pageVo pagevo
     * @return ok
     */
    @OperationLogger("查看评论")
    @PostMapping("/list")
    public Result findBlogCommentByPage(@RequestBody PageVo pageVo) {
        return Result.ok(commentService.findBlogCommentByPage(pageVo.getCurrent(), pageVo.getLimit(), pageVo.getType(), pageVo.getKeywords()));
    }


    /**
     * 隐藏或者显示评论
     *
     * @param comment comment
     * @return 成功消息
     */
    @OperationLogger("隐藏评论权限")
    @RequiresRoles("root")
    @PutMapping("/update")
    public Result updateComment(@RequestBody Comment comment) {
        commentService.updateComment(comment);
        return Result.ok(Message.UPDATE_SUCCESS.getMessage());
    }



    /**
     * 隐藏或者显示评论
     *
     * @param reply comment
     * @return 成功消息
     */
    @OperationLogger("隐藏评论权限")
    @RequiresRoles("root")
    @PutMapping("/update/reply")
    public Result findBlogCommentByPage(@RequestBody CommentReply reply) {
        commentService.updateComment(reply);
        return Result.ok(Message.UPDATE_SUCCESS.getMessage());
    }

    /**
     * 评论分页 附带搜索
     *
     * @param pageVo pagevo
     * @return ok
     */
    @OperationLogger("获取文章回复")
    @PostMapping("/reply/list")
    public Result findBlogCommentReplyPage(@RequestBody PageVo pageVo) {
        return Result.ok(commentService.findBlogCommentReplyPage(pageVo.getCurrent(), pageVo.getLimit(), pageVo.getKeywords()));
    }

    /**
     * 评论删除
     */
    @OperationLogger("删除文章评论")
    @RequiresRoles("root")
    @DeleteMapping("/del")
    public Result delComment(@RequestParam("id") Long id) {
        // 删除回复
        commentService.delCommentReplyByCommentId(id);
        // 删除评论
        commentService.delComment(id);
        return Result.ok("评论删除成功！");
    }


    /**
     * 删除评论
     */
    @OperationLogger("删除文章回复")
    @RequiresRoles("root")
    @DeleteMapping("/reply/del")
    public Result delCommentReply(@RequestParam("replyId") Long replyId) {
        commentService.delReply(replyId);
        return Result.ok("回复删除成功！");
    }



}
