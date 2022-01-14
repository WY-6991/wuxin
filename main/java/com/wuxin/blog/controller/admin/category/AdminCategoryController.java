package com.wuxin.blog.controller.admin.category;

import com.wuxin.blog.annotation.OperationLogger;
import com.wuxin.blog.pojo.blog.Category;
import com.wuxin.blog.mode.PageVo;
import com.wuxin.blog.service.CategoryService;
import com.wuxin.blog.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: wuxin001
 * @Date: 2021/09/01/15:32
 * @Description: 分类管理
 */


@Slf4j
@RestController
@RequestMapping("/admin/category")
public class AdminCategoryController {

    @Resource
    private CategoryService categoryService;


    /**
     * categoryList
     *
     * @return list
     */
    @OperationLogger("查看分类标签")
    @PostMapping("/list")
    public Result findCategoryList(@RequestBody PageVo pageVo) {
        log.info("查看分类操作 current={},limit={},keywords={}", pageVo.getCurrent(), pageVo.getLimit(), pageVo.getKeywords());
        return Result.ok(categoryService.selectListByPage(pageVo.getCurrent(), pageVo.getLimit(), pageVo.getKeywords()));
    }


    /**
     * add category
     *
     * @param category categoryDTO
     * @return success
     */
    @OperationLogger("添加分类")
    @RequiresRoles("root")
    @PostMapping("/add")
    public Result addCategory(@RequestBody Category category) {
        log.info("添加分类操作 category={}", category);
        Category categoryByName = categoryService.findCategoryByName(category.getName());
        if (categoryByName != null) {
            return Result.error("添加失败！该分类名已经存在！");
        }
        categoryService.add(category);
        return Result.ok("添加成功！");
    }

    /**
     * update category
     *
     * @param category categoryDTO
     * @return success
     */
    @OperationLogger("修改分类")
    @RequiresRoles("root")
    @PostMapping("/update")
    public Result updateCategory(@RequestBody Category category) {
        log.info("修改分类操作 category={}", category);
        Category categoryByName = categoryService.findCategoryByName(category.getName());
        if (categoryByName != null) {
            return Result.error("该标签名已经存在");
        }
        categoryService.update(category);
        return Result.ok("修改成功");
    }


    /**
     * update category
     *
     * @param category categoryDTO
     * @return success
     */
    @OperationLogger("修改分类颜色")
    @RequiresRoles("root")
    @PutMapping("/update/color")
    public Result updateCategoryColor(@RequestBody Category category) {
        categoryService.update(category);
        return Result.ok("修改成功！");
    }

    /**
     * 删除category
     *
     * @param cid cid
     * @return success
     */
    @OperationLogger("删除分类")
    @RequiresRoles("root")
    @DeleteMapping("/del/{cid}")
    public Result delCategory(@PathVariable("cid") Long cid) {
        categoryService.delete(cid);
        return Result.ok("删除成功");
    }

    /**
     * 统计blog分类
     *
     * @return
     */
    @OperationLogger("统计文章分类数量")
    @GetMapping("/blog/count")
    public Result blogCategoryCount() {
        return Result.ok(categoryService.blogCountByCategoryName());
    }

}
