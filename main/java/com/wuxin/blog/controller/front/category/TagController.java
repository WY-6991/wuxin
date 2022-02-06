package com.wuxin.blog.controller.front.category;

import com.wuxin.blog.annotation.VisitLogger;
import com.wuxin.blog.mode.PageVo;
import com.wuxin.blog.service.TagService;
import com.wuxin.blog.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: wuxin001
 * @Date: 2021/08/27/15:51
 * @Description:
 */

@Slf4j
@RestController
@RequestMapping("/tag")
public class TagController {

    @Resource
    private TagService tagService;



    @GetMapping("/list")
    public Result findTag() {
        log.info("当前正在执行查看 tagList操作 ；");
        return Result.ok(tagService.list());
    }



    /**
     * 安装blog tag分类
     * @param pageVo
     * @return
     */
    @VisitLogger(value = "根据文章标签名获取文章列表",name = "标签页")
    @PostMapping("/blog/list")
    public Result findBlogByTagName(@RequestBody PageVo pageVo) {
        log.info("当前正在执行查看 博客根据tagName={} ；",pageVo.getKeywords());
        return Result.ok(tagService.findBlogByTagName(pageVo.getCurrent(), pageVo.getLimit(), pageVo.getKeywords()));
    }





}
