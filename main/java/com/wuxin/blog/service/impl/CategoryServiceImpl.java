package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.BlogMapper;
import com.wuxin.blog.mapper.CategoryMapper;
import com.wuxin.blog.mapper.TagMapper;
import com.wuxin.blog.mapper.UserMapper;
import com.wuxin.blog.mapper.vo.BlogTagMapper;
import com.wuxin.blog.pojo.Blog;
import com.wuxin.blog.pojo.Category;
import com.wuxin.blog.pojo.Tag;
import com.wuxin.blog.pojo.vo.BlogTag;
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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BlogTagMapper blogTagMapper;

    @Autowired
    private TagMapper tagMapper;


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


    // @Override
    // public IPage<Blog> findBlogByCategoryName(Integer current, Integer size, String categoryName) {
    //     Long cid = new LambdaQueryChainWrapper<Category>(categoryMapper).eq(Category::getName, categoryName).one().getCid();
    //     Page<Blog> blogPage = new Page<>(current,size);
    //     return new LambdaQueryChainWrapper<Blog>(blogMapper).eq(Blog::getCid, cid).page(blogPage);
    // }
    //
    @Override
    public IPage<Blog> findBlogByCategoryName(Integer current, Integer size, String categoryName) {
        Long cid = new LambdaQueryChainWrapper<Category>(categoryMapper).eq(Category::getName, categoryName).one().getCid();
        Page<Blog> blogPage = new Page<>(current,size);
        Page<Blog> ipage = new LambdaQueryChainWrapper<Blog>(blogMapper).eq(Blog::getCid, cid).page(blogPage);
        ipage.getRecords().forEach(blog -> {

        });

        return ipage;

    }


    @Override
    public IPage<Category> findCategoryList(Integer current, Integer limit,String keywords) {
        return new LambdaQueryChainWrapper<Category>(categoryMapper)
                .like(!keywords.isEmpty(),Category::getName,keywords)
                .page(new Page<Category>(current,limit));
    }

    public Blog getBlogInfo(Blog blog){

        blog.setUsername(userMapper.selectById(blog.getUserId()).getNickname());
        // 获取分类
        blog.setCategory(categoryMapper.selectById(blog.getCid()));
        // 返回所有标签
        LambdaQueryChainWrapper<BlogTag> bt = new LambdaQueryChainWrapper<>(blogTagMapper);
        List<BlogTag> list = bt.eq(BlogTag::getBlogId, blog.getBlogId()).list();
        List<Tag> tags = new ArrayList<>();
        for (BlogTag blogTag : list) {
            // 获取标签名
            if (blogTag.getTagId() != null) {
                tags.add(tagMapper.selectById(blogTag.getTagId()));
            }

        }
        //添加标签名
        blog.setTags(tags);
        return blog;
    }
}
