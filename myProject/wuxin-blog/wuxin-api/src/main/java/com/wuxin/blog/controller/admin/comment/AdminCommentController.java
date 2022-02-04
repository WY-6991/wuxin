package com.wuxin.blog.controller.admin.comment;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.enums.BusinessType;
import com.wuxin.blog.mode.PageVo;
import com.wuxin.blog.pojo.blog.Comment;
import com.wuxin.blog.pojo.blog.CommentReply;
import com.wuxin.blog.service.CommentService;
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
    @OperationLogger(value = "查看评论",type = BusinessType.SELECT)
    @PostMapping("/list")
    public Result findBlogCommentByPage(@RequestBody PageVo pageVo) {
        return Result.ok(commentService.findBlogCommentByPage(pageVo.getCurrent(), pageVo.getLimit(), pageVo.getType(), pageVo.getKeywords(),pageVo.getId(), pageVo.getStart(), pageVo.getEnd()));
    }


    /**
     * 隐藏或者显示评论
     *
     * @param comment comment
     * @return 成功消息
     */
    @OperationLogger(value = "隐藏评论权限",type = BusinessType.UPDATE)
    @RequiresRoles("root")
    @PutMapping("/update")
    public Result updateComment(@RequestBody Comment comment) {
        commentService.updateComment(comment);
        return Result.ok("操作成功！");
    }



    /**
     * 隐藏或者显示评论
     *
     * @param reply comment
     * @return 成功消息
     */
    @OperationLogger(value = "隐藏评论权限",type = BusinessType.UPDATE)
    @RequiresRoles("root")
    @PutMapping("/update/reply")
    public Result findBlogCommentByPage(@RequestBody CommentReply reply) {
        commentService.updateComment(reply);
        return Result.ok("操作成功");
    }

    /**
     * 评论分页 附带搜索
     *
     * @param pageVo pagevo
     * @return ok
     */
    @OperationLogger(value = "获取文章回复",type = BusinessType.SELECT)
    @PostMapping("/reply/list")
    public Result findBlogCommentReplyPage(@RequestBody PageVo pageVo) {
        return Result.ok(commentService.findBlogCommentReplyPage(pageVo.getCurrent(), pageVo.getLimit(), pageVo.getKeywords()));
    }

    /**
     * 评论删除
     */
    @OperationLogger(value = "删除评论",type = BusinessType.DELETE)
    @RequiresRoles("root")
    @DeleteMapping("/del")
    public Result delComment(@RequestParam("id") Long id) {
        // 删除所有该评论下的回复
        commentService.delCommentReplyByCommentId(id);
        // 删除该评论
        commentService.delComment(id);
        return Result.ok("评论删除成功！");
    }


    /**
     * 删除评论
     */
    @OperationLogger(value = "删除回复",type = BusinessType.DELETE)
    @RequiresRoles("root")
    @DeleteMapping("/reply/del")
    public Result delCommentReply(@RequestParam("replyId") Long replyId) {
        commentService.delReply(replyId);
        return Result.ok("回复删除成功！");
    }




}
