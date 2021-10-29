package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.BlogMapper;
import com.wuxin.blog.mapper.CategoryMapper;
import com.wuxin.blog.mapper.UserMapper;
import com.wuxin.blog.pojo.Blog;
import com.wuxin.blog.pojo.Category;
import com.wuxin.blog.service.BlogService;
import com.wuxin.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogMapper blogMapper;


    @Override
    public int addCategory(Category category) {
        return categoryMapper.insert(category);
    }

    @Override
    public int delCategory(Long id) {
        return categoryMapper.deleteById(id);
    }

    @Override
    public boolean updateCategory(Category category) {
        return new LambdaUpdateChainWrapper<Category>(categoryMapper).eq(Category::getCid, category.getCid()).update(category);
    }

    @Override
    public List<Category> findCategory() {
        return categoryMapper.selectList(null);
    }

    @Override
    public Category findCategoryByName(String name) {
        return new LambdaQueryChainWrapper<Category>(categoryMapper).eq(Category::getName, name).one();
    }


    @Override
    public List<Blog> findBlogByCategoryName(Integer current, Integer size, String categoryName) {
        List<Blog> blogList = new ArrayList<>();
        Long cid = new LambdaQueryChainWrapper<Category>(categoryMapper).eq(Category::getName, categoryName).one().getCid();
        List<Blog> blogCateList = new LambdaQueryChainWrapper<Blog>(blogMapper).eq(Blog::getCid, cid).list();
        for (Blog blog : blogCateList) {
            blogList.add(blogService.findBlogByBlogId(blog.getBlogId()));
        }
        return blogList;
    }


    @Override
    public IPage<Category> findCategoryList(Integer current, Integer limit,String keywords) {
        return new LambdaQueryChainWrapper<Category>(categoryMapper)
                .like(!keywords.isEmpty(),Category::getName,keywords)
                .page(new Page<Category>(current,limit));
    }
}
