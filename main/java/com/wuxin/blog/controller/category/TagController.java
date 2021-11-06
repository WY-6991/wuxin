package com.wuxin.blog.controller.category;

import com.wuxin.blog.pojo.Tag;
import com.wuxin.blog.pojo.vo.PageVo;
import com.wuxin.blog.service.TagService;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wuxin001
 * @Date: 2021/08/27/15:51
 * @Description:
 */

@Slf4j
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;


    /**
     * 显示所有taglist
     * @return list
     */
    @GetMapping("/list")
    public Result findTag() {
        log.info("当前正在执行查看 tagList操作 ；");
        return R.ok(tagService.findTag());
    }



    /**
     * 安装blog tag分类
     * @param pageVo
     * @return
     */
    @PostMapping("/blog/list")
    public Result findBlogByTagName(@RequestBody PageVo pageVo) {
        log.info("当前正在执行查看 博客根据tagName={} ；",pageVo.getKeywords());
        return R.ok(tagService.findBlogByTagName(pageVo.getCurrent(), pageVo.getLimit(), pageVo.getKeywords()));
    }

    /**
     * 统计标签个数
     * @return count
     */
    @GetMapping("/count")
    public Result findTagCount() {
        log.info("统计标签个数");
        return R.ok(tagService.findTagCount());
    }



}
