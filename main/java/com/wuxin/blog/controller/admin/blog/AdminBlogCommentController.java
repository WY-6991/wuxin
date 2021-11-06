package com.wuxin.blog.controller.admin.blog;

import com.wuxin.blog.pojo.vo.PageVo;
import com.wuxin.blog.service.BlogCommentService;
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
@RequestMapping("/admin/blog/comment")
public class AdminBlogCommentController {

    @Autowired
    private BlogCommentService blogCommentService;


    /**
     * 后台首页统计评论数
     * @return count
     */
    @GetMapping("/count")
    public Result getCommentCount(){
        return R.ok(blogCommentService.findCommentCount());
    }

    /**
     * 评论分页 附带搜索
     * @param pageVo pagevo
     * @return ok
     */
    @PostMapping("/list")
    public Result findBlogCommentByPage(@RequestBody PageVo pageVo){
        return R.ok(blogCommentService.findBlogCommentByPage(pageVo.getCurrent(),pageVo.getLimit(),pageVo.getKeywords()));
    }

    /**
     * 评论分页 附带搜索
     * @param pageVo pagevo
     * @return ok
     */
    @PostMapping("/reply/list")
    public  Result findBlogCommentReplyPage(@RequestBody PageVo pageVo){
        return R.ok(blogCommentService.findBlogCommentReplyPage(pageVo.getCurrent(),pageVo.getLimit(),pageVo.getKeywords()));
    }

    /**
     * 评论删除
     */
    @GetMapping("/del")
    public Result delComment(@RequestParam("cid") Long cid){
        // 删除回复
        blogCommentService.delCommentReplyByCommentId(cid);
        // 删除评论
        return R.ok(blogCommentService.delComment(cid));
    }


    /**
     * 删除评论
     */
    @GetMapping("/reply/del")
    public Result delCommentReply(@RequestParam("replyId") Long replyId){
        // 删除回复
        return R.ok(blogCommentService.delReply(replyId));
    }


    @GetMapping("/this/{current}/{size}/{blogId}")
    public Result findBlogCommentByBlogId(@PathVariable("current") Integer current,
                                          @PathVariable("size") Integer size,
                                          @PathVariable("blogId") Long blogId) {


        return R.ok(blogCommentService.findBlogCommentByBlogId(current,size,blogId));
    }


}
