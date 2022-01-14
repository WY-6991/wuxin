package com.wuxin.blog.controller.admin.blog;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.pojo.blog.Blog;
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
    private CommentService blogCommentService;


    @OperationLogger("获取文章列表")
    @PostMapping("/list")
    public Result findBlogPage(@RequestBody PageVo pageVo) {

        return Result.ok(blogService.findBlogPage
                (
                        pageVo.getCurrent(),
                        pageVo.getLimit(),
                        pageVo.getKeywords(),
                        pageVo.getStart(),
                        pageVo.getEnd())
                );
    }


    /**
     * 添加blog
     *
     * @param blog DTO
     * @return 成功消息
     */
    @OperationLogger("添加文章")
    @PostMapping("/add")
    public Result addBlog(@RequestBody Blog blog) {
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
        // 将得到的blogID返回给blogTag，将tagId添加进去
        Long blogId = blogService.addBlog(blog);
        tagService.addBlogTag(blogId, blog.getTagIds());
        return Result.ok(Message.ADD_SUCCESS.getMessage());

    }


    /**
     * 修改blog
     *
     * @param blog blogDTO
     * @return success
     */
    @OperationLogger("修改文章")
    @PutMapping("/update")
    public Result blogUpdate(@RequestBody Blog blog) {
        blogService.updateBlog(blog);
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
    public Result blogDel(@RequestParam("blogId") Long blogId) {
        blogService.delBlog(blogId);
        tagService.delBlogTagByBlogId(blogId);
        blogCommentService.delCommentByBlogId(blogId);
        blogCommentService.delCommentReplyByBlogId(blogId);
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
    public Result blogDelByUserId(@RequestParam("userId") Long userId) {
        blogService.delBlogByUserId(userId);
        blogCommentService.delCommentByUserId(userId);
        blogCommentService.delReplyByUserId(userId);
        return Result.ok(Message.DEL_SUCCESS.getMessage());
    }



}
