package com.wuxin.blog.controller.admin.friend;


import com.wuxin.blog.pojo.vo.PageVo;
import com.wuxin.blog.service.FriendCommentService;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/admin/friend/comment")
@RestController
public class AdminFriendCommentController {

    @Autowired
    private FriendCommentService friendCommentService;


    @PostMapping("/list")
    public Result findFriendCommentList(@RequestBody PageVo pageVo){
        return R.ok(friendCommentService.findFriendComment(pageVo.getCurrent(), pageVo.getLimit(), pageVo.getKeywords()));
    }

    @GetMapping("/del/{id}")
    public Result delFriendComment(@PathVariable Long id){
        // 删除评论之前删除该评论下的所有回复
        friendCommentService.delFriendCommentReplyByCommentId(id);
        friendCommentService.delFriendComment(id);
        // 删除评论
        return R.ok("删除成功！");
    }

    @GetMapping("/del/reply/{id}")
    public Result delFriendCommentReply(@PathVariable Long id){
        friendCommentService.delFriendCommentReply(id);
        return R.ok("删除成功！");
    }
}
