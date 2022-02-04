package com.wuxin.blog.controller.admin.dashboard;

import com.wuxin.blog.service.*;
import com.wuxin.blog.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wuxin001
 * @Date: 2022/01/09/15:52
 * @Description:
 */

@RequestMapping("/dashboard")
@RestController
public class DashboardController {


    @Autowired
    private CategoryService categoryService;


    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;
    @Autowired
    private LoginLogService loginLogService;


    @Autowired
    private AccessLogService accessLogService;




    /**
     * 文章统计
     */
    @GetMapping("/count")
    public Result count() {
        Result result = Result.ok();
        result.put("blog", blogService.blogCount());
        result.put("comment", commentService.commentCount());
        result.put("access", accessLogService.selectTodayAccessLog());
        result.put("login", loginLogService.selectTodayLoginLog());
        return result;
    }


    /**
     * 类型分类展示
     *
     * @return category
     */
    @GetMapping("/category")
    public Result blogCountByCategoryName() {
        return Result.ok(categoryService.blogCountByCategoryName());
    }


    /**
     * 标签分类展示
     *
     * @return tag
     */
    @GetMapping("/tag")
    public Result blogCountByTagName() {
        return Result.ok(tagService.blogCountByTagName());
    }
}
