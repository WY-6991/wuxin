package com.wuxin.blog.controller.about;


import com.wuxin.blog.pojo.vo.AboutComment;
import com.wuxin.blog.pojo.vo.AboutCommentReply;
import com.wuxin.blog.service.AboutCommentService;
import com.wuxin.blog.service.CommentUserService;
import com.wuxin.blog.service.impl.AboutCommentServiceImpl;
import com.wuxin.blog.util.CommentUtil;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("/about/comment")
@RestController
public class AboutCommentController {

    @Autowired
    private AboutCommentService aboutCommentService;

    @Autowired
    private CommentUserService commentUserService;


    @PostMapping("/add")
    public Result addAboutComment(@RequestBody AboutComment aboutComment){
        log.info("addAboutComment aboutComment:{}",aboutComment);
        Long userId = commentUserService.getUserId(aboutComment.getUsername(), aboutComment.getEmail());
        if(userId == null){
            log.info("addAboutComment:{}","用户名或者邮箱输入错误");
            return R.error("请检查用户名或者邮箱!可能已被使用了哦!");
        }
        aboutComment.setCommentUserId(userId);
        aboutCommentService.addAboutComment(aboutComment);
        return R.ok(userId);
    }

    @PostMapping("/add/reply")
    public Result addAboutCommentReply(@RequestBody AboutCommentReply reply){
        log.info("addAboutCommentReply reply:{}",reply);
        Long userId = commentUserService.getUserId(reply.getReplyUsername(), reply.getReplyEmail());
        if(userId == null){
            log.info("addAboutCommentReply:{}","用户名或者邮箱输入错误");
            return R.error("请检查用户名或者邮箱!可能已被使用了哦!");
        }
        reply.setReplyUserId(userId);
        aboutCommentService.addAboutCommentReply(reply);
        return R.ok(userId);
    }
    
    @GetMapping("/list/{current}/{limit}")
    public Result findAboutCommentList(@PathVariable int current, @PathVariable int limit){
        return R.ok(aboutCommentService.findAboutComment(current,limit));
    }

    @GetMapping("/del/{id}")
    public Result delAboutComment(@PathVariable Long id){
        // 删除评论之前删除该评论下的所有回复
        aboutCommentService.delAboutCommentReplyByCommentId(id);
        // 删除评论
        return R.ok(aboutCommentService.delAboutComment(id));
    }

    @GetMapping("/del/reply/{id}")
    public Result delAboutCommentReply(@PathVariable Long id){

        return R.ok(aboutCommentService.delAboutCommentReply(id));
    }
}
