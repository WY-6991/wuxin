package com.wuxin.blog.controller.admin.blog;

import com.wuxin.blog.pojo.Blog;
import com.wuxin.blog.pojo.User;
import com.wuxin.blog.pojo.vo.PageVo;
import com.wuxin.blog.service.BlogService;
import com.wuxin.blog.service.BlogCommentService;
import com.wuxin.blog.service.TagService;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
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
    private BlogCommentService blogCommentService;

    // @GetMapping("/list/{current}/{size}")
    // public Result findAllBlog(@PathVariable("current") Integer current,
    //                        @PathVariable("size") Integer size) {
    //     log.info("blogList信息 current = {},size = {}", current, size);
    //     return R.ok(blogService.findAllBlog(current, size));
    //
    // }

    @PostMapping("/list")
    public Result findBlogPage(@RequestBody PageVo pageVo) {
        log.info("blogList信息 current = {},limit = {},keywords={}", pageVo.getCurrent(),pageVo.getLimit(),pageVo.getKeywords());
        return R.ok(blogService.findBlogPage(pageVo.getCurrent(),pageVo.getLimit(),pageVo.getKeywords()));
    }



    // @CrossOrigin(origins = "http://localhost:2768")
    @PostMapping("/add")
    public Result addBlog(@RequestBody Blog blog) {
        log.info("添加的blog信息 blog={}",blog);
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        log.info("添加的blog信息 session 中用户信息add blog ={}",user);
        if (user == null) return R.error("未登录");
        blog.setUserId(user.getUserId());
        // 将得到的blogID返回给blogTag，将tagId添加进去
        Long blogId = blogService.addBlog(blog);
        if(tagService.addBlogTag(blogId,blog.getTagIds())==1){
            return R.ok("添加成功！");
        }
        return R.error("添加失败！");

    }


    /**
     * 修改blog
     * @param blog blogDTO
     * @return success
     */
    @PostMapping("/update")
    public Result blogUpdate(@RequestBody Blog blog) {
        log.info("修改blog操作....blogId={}",blog.getBlogId());
        if (blogService.updateBlog(blog)) return R.ok("success");
        return R.error("error");
    }


    /**
     * 删除blog
     * @param blogId blogId
     * @return success
     */
    @GetMapping("/del/{blogId}")
    public Result blogDel(@PathVariable("blogId") Long blogId) {
        if (blogService.delBlog(blogId) == 1){
            tagService.delBlogTagByBlogId(blogId); // 删除回复
            blogCommentService.delCommentByBlogId(blogId); // 删除评论
            blogCommentService.delCommentReplyByBlogId(blogId); // 删除回复
            return R.ok("success");
        }
        return R.error("error");
    }

    /**
     * 根据用户id删除blog
     * @param userId userid
     * @return  success
     */
    @GetMapping("/del/userId/{userId}")
    public Result blogDelByUserId(@PathVariable("userId") Long userId) {
        if (blogService.delBlogByUserId(userId) == 1) {
            blogCommentService.delCommentByUserId(userId);
            blogCommentService.delReplyByUserId(userId);
            return R.ok("删除成功！");
        }
        return R.error("error");
    }


    /**
     *  统计所有已经发布blog点击量
     */
    @GetMapping("/count/views")
    public Result countAllBlogViews(){
       return R.ok(blogService.countAllBlogViews());
    }

    /**
     *  统计所有已经发布blog点击量
     */
    @GetMapping("/count/blog")
    public Result countAllBlog(){
        return R.ok(blogService.countAllBlog());
    }


}
