package com.wuxin.blog.controller.admin.about;


import com.wuxin.blog.pojo.vo.PageVo;
import com.wuxin.blog.service.AboutCommentService;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/admin/about/comment")
@RestController
public class AdminAboutCommentController {

    @Autowired
    private AboutCommentService aboutCommentService;

    @PostMapping("/list")
    public Result findAboutCommentList(@RequestBody PageVo pageVo){
        return R.ok(aboutCommentService.findAboutComment(pageVo.getCurrent(), pageVo.getLimit()));
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
