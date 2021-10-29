package com.wuxin.blog.controller.admin;

import com.wuxin.blog.pojo.vo.PageVo;
import com.wuxin.blog.service.CommentService;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wuxin001
 * @Date: 2021/09/01/15:51
 * @Description: 评论管理
 */
@RestController
@RequestMapping("/admin/comment")
public class AdminCommentController {

    @Autowired
    private CommentService commentService;


    /**
     * 后台首页统计评论数
     * @return count
     */
    @GetMapping("/count")
    public Result getCommentCount(){
        return R.ok(commentService.findCommentCount());
    }

    /**
     * 评论分页 附带搜索
     * @param pageVo pagevo
     * @return ok
     */
    @PostMapping("/list")
    public Result findBlogCommentByPage(@RequestBody PageVo pageVo){
        return R.ok(commentService.findBlogCommentByPage(pageVo.getCurrent(),pageVo.getLimit(),pageVo.getKeywords()));
    }

    /**
     * 评论分页 附带搜索
     * @param pageVo pagevo
     * @return ok
     */
    @PostMapping("/reply/list")
    public  Result findBlogCommentReplyPage(@RequestBody PageVo pageVo){
        return R.ok(commentService.findBlogCommentReplyPage(pageVo.getCurrent(),pageVo.getLimit(),pageVo.getKeywords()));
    }

    /**
     * 评论删除
     */
    @GetMapping("/del")
    public Result delComment(@RequestParam("cid") Long cid){
        // 删除回复
        commentService.delCommentReplyByCommentId(cid);
        // 删除评论
        return R.ok(commentService.delComment(cid));
    }


    /**
     * 删除评论
     */
    @GetMapping("/reply/del")
    public Result delCommentReply(@RequestParam("replyId") Long replyId){
        // 删除回复
        return R.ok(commentService.delReply(replyId));
    }


}
