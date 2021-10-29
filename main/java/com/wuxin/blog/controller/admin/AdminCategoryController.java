package com.wuxin.blog.controller.admin;

import com.wuxin.blog.pojo.Category;
import com.wuxin.blog.pojo.Tag;
import com.wuxin.blog.pojo.vo.PageVo;
import com.wuxin.blog.service.CategoryService;
import com.wuxin.blog.service.TagService;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wuxin001
 * @Date: 2021/09/01/15:32
 * @Description: 分类管理
 */


@Slf4j
@RestController
@RequestMapping("/admin/category")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     * categoryList
     * @return list
     */
    @PostMapping("/list")
    public Result findCategoryList(@RequestBody PageVo pageVo ) {
        log.info("查看分类操作 current={},limit={},keywords={}",pageVo.getCurrent(),pageVo.getLimit(),pageVo.getKeywords());
        return R.ok(categoryService.findCategoryList(pageVo.getCurrent(),pageVo.getLimit(),pageVo.getKeywords()));
    }


    /**
     * add category
     * @param category categoryDTO
     * @return success
     */
    @PostMapping("/add")
    public Result addCategory(@RequestBody Category category) {
        log.info("添加分类操作 category={}",category);
        Category categoryByName = categoryService.findCategoryByName(category.getName());
        if (categoryByName != null) return R.error("添加失败！该分类名已经存在！");
        if (categoryService.addCategory(category) == 1) return R.ok("添加成功！");
        return R.error("添加失败！");
    }

    /**
     * update category
     * @param category categoryDTO
     * @return success
     */
    @PostMapping("/update")
    public Result updateCategory(@RequestBody Category category) {
        log.info("修改分类操作 category={}",category);
        Category categoryByName = categoryService.findCategoryByName(category.getName());
        if (categoryByName != null) return R.error("该标签名已经存在");
        if (categoryService.updateCategory(category)) return R.ok("修改成功");
        return R.error("修改失败！");
    }


    /**
     * update category
     * @param category categoryDTO
     * @return success
     */
    @PostMapping("/update/color")
    public Result updateCategoryColor(@RequestBody Category category) {
        log.info("修改分类颜色操作 category={}",category);
        if (categoryService.updateCategory(category)) return R.ok("修改成功！");
        return R.error("修改失败！");
    }

    /**
     * 删除category
     * @param cid cid
     * @return success
     */
    @GetMapping("/del/{cid}")
    public Result delCategory(@PathVariable("cid") Long cid) {
        log.info("删除分类操作 cid={}",cid);
        if (categoryService.delCategory(cid) == 1) return R.ok("删除成功！");
        return R.error("删除失败！");
    }
}
