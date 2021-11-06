package com.wuxin.blog.controller.friend;


import com.wuxin.blog.pojo.vo.FriendComment;
import com.wuxin.blog.pojo.vo.FriendCommentReply;
import com.wuxin.blog.service.AboutCommentService;
import com.wuxin.blog.service.CommentUserService;
import com.wuxin.blog.service.FriendCommentService;
import com.wuxin.blog.util.CommentUtil;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/friend/comment")
@RestController
public class FriendCommentController {

    @Autowired
    private FriendCommentService friendCommentService;

    @Autowired
    private CommentUserService commentUserService;

    @PostMapping("/add")
    public Result addFriendComment(@RequestBody FriendComment comment){
        Long userId = commentUserService.getUserId(comment.getUsername(), comment.getEmail());
        if(userId == null){
            log.info("addFriendComment:{}","用户名或者邮箱输入错误");
            return R.error("请检查用户名或者邮箱!可能已被使用了哦!");
        }
        comment.setCommentUserId(userId);
        friendCommentService.addFriendComment(comment);
        return R.ok(userId);
    }

    @PostMapping("/add/reply")
    public Result addFriendCommentReply(@RequestBody FriendCommentReply reply){
        Long userId = commentUserService.getUserId(reply.getReplyUsername(), reply.getReplyEmail());
        if(userId == null){
            log.info("addFriendCommentReply:{}","用户名或者邮箱输入错误");
            return R.error("请检查用户名或者邮箱!可能已被使用了哦!");
        }
        reply.setReplyUserId(userId);
        friendCommentService.addFriendCommentReply(reply);
        return R.ok(userId);
    }

    @GetMapping("/list/{current}/{limit}")
    public Result findFriendCommentList(@PathVariable int current, @PathVariable int limit){
        return R.ok(friendCommentService.findFriendComment(current,limit,""));
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
