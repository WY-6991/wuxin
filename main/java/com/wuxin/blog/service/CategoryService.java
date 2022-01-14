package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.blog.Blog;
import com.wuxin.blog.pojo.blog.Category;
import com.wuxin.blog.mode.Base.PageService;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/9:24
 * @Description:
 */
public interface CategoryService extends PageService<Category> {


    /**
     * 根据categoryName查找category
     * @param name name
     * @return category
     */
    Category findCategoryByName(String name);


    /**
     *
     * @param current current
     * @param size size
     * @param categoryName categoryName
     * @return list
     */
    IPage<Blog> findBlogByCategoryName(Integer current, Integer size, String categoryName);


    /**
     * 返回统计数据
     * @return list
     */
    List<Object> blogCountByCategoryName();
}
