package com.wuxin.blog.controller.front.blog;

import com.wuxin.blog.annotation.AccessLimit;
import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.annotation.VisitLogger;
import com.wuxin.blog.mode.SearchBlog;
import com.wuxin.blog.pojo.blog.Blog;
import com.wuxin.blog.service.BlogService;
import com.wuxin.blog.utils.result.Result;
import com.wuxin.blog.utils.string.StringUtils;
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


    @AccessLimit(seconds = 100, limitCount = 20, msg = "刷新频率过高！稍后再试！")
    @VisitLogger(value = "查看文章列表", name = "首页")
    @GetMapping("/list")
    public Result findBlog(@RequestParam(value = "current", defaultValue = "1") Integer current,
                           @RequestParam(value = "size", defaultValue = "5") Integer size) {
        log.info("blogList信息 current = {},size = {}", current, size);
        return Result.ok(blogService.findBlog(current, size));

    }


    /**
     * 博客详情
     *
     * @return blog
     */
    @AccessLimit(seconds = 100, limitCount = 20, msg = "刷新频率过高！稍后再试！")
    @VisitLogger(value = "访问了文章", name = "文章详情页")
    @GetMapping("/detail")
    public Result findBlogByBlogId(@RequestParam(value = "blogId") Long blogId) {
        return Result.ok(blogService.findBlogByBlogId(blogId));
    }


    /**
     * 随机获取几条数据
     *
     * @return blogList
     */
    @GetMapping("/random")
    public Result getRandomBlog() {
        return Result.ok(blogService.randomBlog());
    }


    /**
     * 最新blog
     *
     * @return blogList
     */
    @AccessLimit(seconds = 100, limitCount = 20, msg = "刷新频率过高！稍后再试！")
    @GetMapping("/before")
    public Result beforeBlog(@RequestParam(value = "blogId") Long blogId) {
        if (StringUtils.isNull(blogId)) {
            return Result.error("获取不到当前文章");
        }
        SearchBlog blog = blogService.beforeBlog(blogId);
        if (StringUtils.isNull(blog)) {
            return Result.error("没有更多了");
        }
        return Result.ok(blog);
    }

    /**
     * 最新blog
     *
     * @return blogList
     */
    @AccessLimit(seconds = 100, limitCount = 20, msg = "刷新频率过高！稍后再试！")
    @GetMapping("/next")
    public Result nextBlog(@RequestParam(value = "blogId") Long blogId) {
        if (StringUtils.isNull(blogId)) {
            return Result.error("获取不到当前文章");
        }
        SearchBlog blog = blogService.nextBlog(blogId);
        if (StringUtils.isNull(blog)) {
            return Result.error("没有更多了");
        }
        return Result.ok(blog);
    }


    @AccessLimit(seconds = 20, limitCount = 1, msg = "操作频率过高！稍后再试！")
    @GetMapping("/keywords")
    public Result searchBlog(@RequestParam("keywords") String keywords) {
        if ("".equals(keywords) || StringUtils.isNull(keywords)) {
            return Result.error("搜索关键词不能为空！");
        }
        return Result.ok(blogService.searchBlog(keywords));
    }
}
