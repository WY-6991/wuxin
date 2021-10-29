package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.Blog;
import com.wuxin.blog.pojo.Category;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {

    /**
     * 添加分类
     * @param category categoryDTO
     * @return int
     */
    int addCategory(Category category);

    /**
     * 删除
     * @param id id
     * @return int
     */
    int delCategory(Long id);

    /**
     * 修改category
     * @param category categoryDTO
     * @return true
     */
    boolean updateCategory(Category category);

    /**
     * categoryList
     * @return list
     */

    List<Category> findCategory();

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
    List<Blog> findBlogByCategoryName(Integer current, Integer size, String categoryName);

    /**
     * 分类分页
     * @param current current
     * @param limit limit
     * @return record
     */
    IPage<Category> findCategoryList(Integer current, Integer limit,String keywords);
}
