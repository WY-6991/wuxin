package com.wuxin.blog.controller.front.blog;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.pojo.blog.Blog;
import com.wuxin.blog.service.BlogService;
import com.wuxin.blog.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: wuxin001
 * @Date: 2021/08/27/14:17
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private BlogService blogService;


    @OperationLogger("获取文章列表")
    @GetMapping("/list")
    public Result findBlog(@RequestParam(value = "current",defaultValue = "1") Integer current,
                           @RequestParam(value = "size",defaultValue = "5") Integer size) {
        log.info("blogList信息 current = {},size = {}", current, size);
        return Result.ok(blogService.findBlog(current, size));

    }


    /**
     * 博客详情
     * @return blog
     */
    @OperationLogger("查看文章详情")
    @GetMapping("/detail")
    public Result findBlogByBlogId(@RequestParam(value = "blogId",required = true) Long blogId) {
        return Result.ok(blogService.findBlogByBlogId(blogId));
    }

    @OperationLogger("获取用户文章")
    @GetMapping("/user/list")
    public Result findBlogByUserId(
            @RequestParam(value = "userId",required = true) Long userId,
            @RequestParam(value = "current",defaultValue = "1") Integer current,
            @RequestParam(value = "size",defaultValue = "10") Integer size) {
        return Result.ok(blogService.findBlogByUserId(userId, current, size));
    }


    /**
     * 随机获取几条数据
     * @return blogList
     */
    @OperationLogger("随机文章")
    @GetMapping("/random")
    public Result getRandomBlog() {
        return Result.ok(blogService.randomBlog());
    }


    /**
     * 最新blog
     * @return blogList
     */
    @OperationLogger("最新文章")
    @GetMapping("/new")
    public Result newBlog() {
        return Result.ok(blogService.newBlog());
    }

    /**
     * 最新blog
     * @return blogList
     */
    @OperationLogger("上一篇文章")
    @GetMapping("/before")
    public Result beforeBlog(@RequestParam(value = "blogId",required = true) Long blogId) {
        Blog blog = blogService.beforeBlog(blogId);
        if(blog!=null){
            return Result.ok(blog);
        }
        return Result.error("没有更多了");
    }

    /**
     * 最新blog
     * @return blogList
     */
    @OperationLogger("下一篇文章")
    @GetMapping("/next")
    public Result nextBlog(@RequestParam(value = "blogId",required = true) Long blogId) {
        Blog blog = blogService.nextBlog(blogId);
        if(blog!=null){
            return Result.ok(blog);
        }
        return Result.error("没有更多了");
    }
}
