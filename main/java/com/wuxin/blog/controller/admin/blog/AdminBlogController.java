package com.wuxin.blog.controller.admin.blog;

import com.wuxin.blog.annotation.AccessLimit;
import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.pojo.blog.Blog;
import com.wuxin.blog.pojo.blog.Tag;
import com.wuxin.blog.pojo.blog.User;
import com.wuxin.blog.mode.PageVo;
import com.wuxin.blog.service.BlogService;
import com.wuxin.blog.service.CommentService;
import com.wuxin.blog.service.TagService;
import com.wuxin.blog.enums.Message;
import com.wuxin.blog.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2021/09/01/15:29
 * @Description: 博客管理
 */
@Slf4j
@RestController
@RequestMapping("/admin/blog")
public class AdminBlogController {


    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;


    @OperationLogger("获取文章列表")
    @PostMapping("/list")
    public Result findBlogPage(@RequestBody PageVo pageVo) {

        return Result.ok(blogService.findBlogPage
                (
                        pageVo.getCurrent(),
                        pageVo.getLimit(),
                        pageVo.getKeywords(),
                        pageVo.getStart(),
                        pageVo.getEnd(),
                        pageVo.getId()
                )
        );
    }


    /**
     * 添加blog
     */
    @AccessLimit(seconds = 60, limitCount = 1, msg = "操作频率过高！一分钟之后再试！")
    @RequiresRoles("root")
    @OperationLogger("添加文章")
    @PostMapping("/add")
    public Result addBlog(@RequestBody Blog blog)
    {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        log.info("添加的blog信息 session 中用户信息add blog ={}", user);
        if (user == null) {
            return Result.error("添加失败，你还未登录！");
        }
        if ("".equals(blog.getTitle())) {
            return Result.error("添加失败，标题不能空！");
        }
        if ("".equals(blog.getContent())) {
            return Result.error("添加失败，内容不能为空！");
        }
        if (blog.getCid() == null) {
            return Result.error("添加失败，获取不到分类！");
        }
        blog.setUserId(user.getUserId());
        // 将得到的blogID返回给blogTag
        Long blogId = blogService.addBlog(blog);
        tagService.addBlogTag(blogId, blog.getTagIds());
        return Result.ok("文章添加成功！");

    }

    @OperationLogger("获取用户文章")
    @GetMapping("/user/list")
    public Result findBlogByUserId(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size)
    {
        return Result.ok(blogService.findBlogByUserId(userId, current, size));
    }


    /**
     * 修改blog
     *
     * @param blog blogDTO
     * @return success
     */
    @AccessLimit(seconds = 60, limitCount = 2, msg = "操作频率过高！一分钟之后再试！")
    @OperationLogger("修改文章")
    @RequiresRoles("root")
    @PutMapping("/update")
    public Result blogUpdate(@RequestBody Blog blog)
    {
        blogService.updateBlog(blog);
        // 修改blog标签
        log.info("blog tagIds：{}", blog.getTagIds());
        tagService.updateBlogTag(blog.getBlogId(), blog.getTagIds());

        return Result.ok("文章修改成功！");
    }


    /**
     * 删除blog
     *
     * @param blogId blogId
     * @return success
     */
    @OperationLogger("删除文章")
    @RequiresRoles("root")
    @DeleteMapping("/del")
    public Result blogDel(@RequestParam("blogId") Long blogId)
    {
        blogService.delBlog(blogId);
        tagService.delBlogTagByBlogId(blogId);
        commentService.delCommentByBlogId(blogId);
        commentService.delCommentReplyByBlogId(blogId);
        return Result.ok(Message.DEL_SUCCESS.getMessage());

    }

    /**
     * 根据用户id删除blog
     *
     * @param userId userid
     * @return success
     */
    @OperationLogger("根据用户id删除文章")
    @RequiresRoles("root")
    @DeleteMapping("/del/userId")
    public Result blogDelByUserId(@RequestParam("userId") Long userId)
    {
        blogService.delBlogByUserId(userId);
        commentService.delCommentByUserId(userId);
        commentService.delReplyByUserId(userId);
        return Result.ok("删除成功！");
    }

    /**
     * 获取全部文章列表
     */
    @AccessLimit(seconds = 60, limitCount = 10, msg = "操作频率过高！一分钟之后再试！")
    @GetMapping("/all/list")
    public Result getAllBlogList() {
        return Result.ok(blogService.getAllBlogList());
    }


}
