package com.wuxin.blog.controller;

import com.sun.jna.platform.unix.Resource;
import com.wuxin.blog.pojo.AboutComment;
import com.wuxin.blog.service.AboutCommentService;
import com.wuxin.blog.service.AboutService;
import com.wuxin.blog.util.GlobalConstant;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:24
 * @Description: 我的关于页面内容
 */
@RequestMapping("/about")
@RestController
public class AboutController {

    @Autowired
    private AboutService aboutService;

    @Autowired
    private AboutCommentService aboutCommentService;

    /**
     * 显示关于我的内容
     *
     * @return
     */
    @GetMapping("/detail")
    public Result findAbout() {
        return R.ok(aboutService.findAbout(GlobalConstant.aboutId));
    }


    /**
     * 显示关于我的内容的评论
     *
     * @return commentList
     */
    @GetMapping("/comment/list")
    public Result findAboutComment(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "10") int limit) {
        return R.ok(aboutCommentService.findAboutComment(current, limit));
    }


    /**
     * 删除评论
     *
     * @return commentList
     */
    @GetMapping("/comment/del")
    public Result updateAboutComment(@RequestParam Long id) {
        return R.ok(aboutCommentService.delAboutComment(id));
    }


    /**
     * 添加评论
     *
     * @return commentList
     */
    @GetMapping("/comment/add")
    public Result addAboutComment(@RequestBody AboutComment aboutComment) {
        return R.ok(aboutCommentService.addAboutComment(aboutComment));
    }
}
