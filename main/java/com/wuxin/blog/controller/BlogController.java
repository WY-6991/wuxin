package com.wuxin.blog.controller;

import com.wuxin.blog.service.BlogService;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wuxin001
 * @Date: 2021/08/27/14:17
 * @Description:
 */

@Slf4j
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;


    @GetMapping("/list/{current}/{size}")
    public Result findBlog(@PathVariable("current") Integer current,
                           @PathVariable("size") Integer size) {
        log.info("blogList信息 current = {},size = {}", current, size);
        return R.ok(blogService.findBlog(current, size));

    }


    /**
     * 博客详情
     * @return blog
     */
    @GetMapping("/detail/{blogId}")
    public Result findBlogByBlogId(@PathVariable("blogId") Long blogId) {
        log.info("博客详情信息 blog detail  blogId={}", blogId);
        return R.ok(blogService.findBlogByBlogId(blogId));
    }


    @GetMapping("/user/list/{userId}/{current}/{size}")
    public Result findBlogByUserId(
            @PathVariable("userId") Long userId,
            @PathVariable("current") Integer current,
            @PathVariable("size") Integer size) {
        log.info("根据用户id查询blog信息 userId={},current = {},size = {}", userId, current, size);
        return R.ok(blogService.findBlogByUserId(userId, current, size));
    }


    /**
     * 随机获取几条数据
     * @return blogList
     */
    @GetMapping("/random")
    public Result getRandomBlog() {
        return R.ok(blogService.randomBlog());
    }
}
