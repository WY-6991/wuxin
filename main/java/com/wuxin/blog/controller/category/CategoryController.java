package com.wuxin.blog.controller.category;

import com.wuxin.blog.pojo.Category;
import com.wuxin.blog.pojo.vo.PageVo;
import com.wuxin.blog.service.CategoryService;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wuxin001
 * @Date: 2021/08/27/15:36
 * @Description: 分类
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     * 显示分类列表
     * @return result
     */
    @GetMapping("/list")
    public Result cateList() {
        return R.ok(categoryService.findCategory());
    }


    /**
     * 根据categoryName显示blog信息
     * @return result
     */
    @PostMapping("/blog/list")
    public Result findBlogByCategoryName(@RequestBody PageVo pageVo) {log.info("分类博客信息 categoryName={}",pageVo.getKeywords());
        return R.ok(categoryService.findBlogByCategoryName(pageVo.getCurrent(), pageVo.getLimit(), pageVo.getKeywords()));
    }




}
