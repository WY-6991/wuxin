package com.wuxin.blog.controller.front.category;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.annotation.VisitLogger;
import com.wuxin.blog.mode.PageVo;
import com.wuxin.blog.service.CategoryService;
import com.wuxin.blog.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: wuxin001
 * @Date: 2021/08/27/15:36
 * @Description: 分类
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;



    @GetMapping("/list")
    public Result cateList() {
        return Result.ok(categoryService.list());
    }


    /**
     * 根据categoryName显示blog信息
     * @return result
     */
    @VisitLogger(value = "获取分类下的文章列表",name = "分类页")
    @PostMapping("/blog/list")
    public Result findBlogByCategoryName(@RequestBody PageVo pageVo) {log.info("分类博客信息 categoryName={}",pageVo.getKeywords());
        return Result.ok(categoryService.findBlogByCategoryName(pageVo.getCurrent(), pageVo.getLimit(), pageVo.getKeywords()));
    }




}
