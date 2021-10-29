package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.wuxin.blog.pojo.Tag;
import com.wuxin.blog.pojo.vo.BlogTag;
import com.wuxin.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BlogTagMapper blogTagMapper;

    @Override
    public Long addBlog(Blog blog) {
        int insert = blogMapper.insert(blog);
        return blog.getBlogId();
    }

    @Override
    public int delBlog(Long blogId) {
        return blogMapper.deleteById(blogId);
    }

    @Override
    public int delBlogByUserId(Long userId) {
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Blog::getUserId,userId);
        return blogMapper.delete(queryWrapper);
    }

    @Override
    public boolean updateBlog(Blog blog) {
        LambdaUpdateChainWrapper<Blog> uc = new LambdaUpdateChainWrapper<>(blogMapper);
        return uc.eq(Blog::getBlogId, blog.getBlogId()).update(blog);
    }

    @Override
    public IPage<Blog> findBlog(Integer current, Integer size) {
        LambdaQueryChainWrapper<Blog> chainWrapper = new LambdaQueryChainWrapper<>(blogMapper);
        Page<Blog> page = new Page<>(current, size);
        Page<Blog> ipage = chainWrapper.eq(Blog::getPublished, "true").orderByDesc(Blog::getCreateTime).page(page);
        for (Blog blog : ipage.getRecords()) {
            // // 获取用户名
            // blog.setUsername(userMapper.selectById(blog.getUserId()).getUsername());
            // // 获取分类
            // blog.setCategory(categoryMapper.selectById(blog.getCid()));
            // // 返回所有标签
            // LambdaQueryChainWrapper<BlogTag> bt = new LambdaQueryChainWrapper<>(blogTagMapper);
            // List<BlogTag> list = bt.eq(BlogTag::getBlogId, blog.getBlogId()).list();
            // List<Tag> tags = new ArrayList<>();
            // for (BlogTag blogTag : list) {
            //     // 获取标签名
            //     if (blogTag.getTagId() != null) {
            //         tags.add(tagMapper.selectById(blogTag.getTagId()));
            //     }
            //
            // }
            // //添加标签名
            // blog.setTags(tags);
            getBlogInfo(blog);
        }
        return ipage;
    }

    @Override
    public Blog findBlogByBlogId(Long blogId) {
        Blog blog = blogMapper.selectById(blogId);
        // 获取用户名
        // blog.setUsername(userMapper.selectById(blog.getUserId()).getUsername());
        // //获取分类
        // blog.setCategory(categoryMapper.selectById(blog.getCid()));
        // //获取标签内容
        // ArrayList<Tag> tags = new ArrayList<>();
        // List<BlogTag> blogTagList = new LambdaQueryChainWrapper<BlogTag>(blogTagMapper).eq(BlogTag::getBlogId, blogId).list();
        // for (BlogTag blogTag : blogTagList) {
        //     tags.add(tagMapper.selectById(blogTag.getTagId()));
        // }
        // blog.setTags(tags);

        return getBlogInfo(blog);
    }

    @Override
    public IPage<Blog> findBlogByUserId(Long userId, Integer current, Integer size) {
        LambdaQueryChainWrapper<Blog> chainWrapper = new LambdaQueryChainWrapper<>(blogMapper);
        Page<Blog> page = new Page<>(current, size);
        return chainWrapper.orderByDesc(Blog::getCreateTime).eq(Blog::getUserId, userId).page(page);
    }

    @Override
    public List<Blog> randomBlog() {
        List<Blog> blogList = blogMapper.getRandomFiveBlog();
        blogList.forEach(blog -> {
            System.out.println("获取随机blogId={}"+blog.getBlogId());
            // 获取用户基本信息
            // 获取用户
            // blog.setUsername(userMapper.selectById(blog.getUserId()).getNickname());
            // // 获取分类
            // blog.setCategory(categoryMapper.selectById(blog.getCid()));
            //
            // // 获取标签
            // LambdaQueryChainWrapper<BlogTag> bt = new LambdaQueryChainWrapper<>(blogTagMapper);
            // List<BlogTag> list = bt.eq(BlogTag::getBlogId, blog.getBlogId()).list();
            // List<Tag> tags = new ArrayList<>();
            // for (BlogTag blogTag : list) {
            //     // 获取标签名
            //     if (blogTag.getTagId() != null) {
            //         tags.add(tagMapper.selectById(blogTag.getTagId()));
            //     }
            //
            // }
            // //添加标签名
            // blog.setTags(tags);

            getBlogInfo(blog);

        });
        return blogList;
    }

    @Override
    public IPage<Blog> findAllBlog(Integer current, Integer size) {
        LambdaQueryChainWrapper<Blog> chainWrapper = new LambdaQueryChainWrapper<>(blogMapper);
        Page<Blog> page = new Page<>(current, size);
        Page<Blog> ipage = chainWrapper.orderByDesc(Blog::getCreateTime).page(page);
        for (Blog blog : ipage.getRecords()) {
            // // 获取用户
            // blog.setUsername(userMapper.selectById(blog.getUserId()).getUsername());
            // // 获取分类
            // blog.setCategory(categoryMapper.selectById(blog.getCid()));
            // // 返回所有标签
            // LambdaQueryChainWrapper<BlogTag> bt = new LambdaQueryChainWrapper<>(blogTagMapper);
            // List<BlogTag> list = bt.eq(BlogTag::getBlogId, blog.getBlogId()).list();
            // List<Tag> tags = new ArrayList<>();
            // for (BlogTag blogTag : list) {
            //     // 获取标签名
            //     if (blogTag.getTagId() != null) {
            //         tags.add(tagMapper.selectById(blogTag.getTagId()));
            //     }
            //
            // }
            // //添加标签名
            // blog.setTags(tags);
            getBlogInfo(blog);
        }
        return ipage;
    }


    @Override
    public Integer countAllBlogViews() {
        int count = 0;
        List<Blog> blogList = new LambdaQueryChainWrapper<Blog>(blogMapper).eq(Blog::getPublished, "true").list();
        for (Blog blog : blogList) {
             count = count + blog.getViews();
        }
        return count;
    }

    @Override
    public Integer countAllBlog() {
        return blogMapper.selectCount(null);
    }



    @Override
    public IPage<Blog> findBlogPage(Integer current, Integer limit, String keywords) {
        LambdaQueryChainWrapper<Blog> chainWrapper = new LambdaQueryChainWrapper<>(blogMapper);
        Page<Blog> page = new Page<>(current, limit);
        Page<Blog> ipage = chainWrapper
                .orderByDesc(Blog::getCreateTime)
                .like(!keywords.isEmpty(),Blog::getTitle,keywords)
                .page(page);
        for (Blog blog : ipage.getRecords()) {
            // 获取用户
            try {
                // blog.setUsername(userMapper.selectById(blog.getUserId()).getUsername());
                // // 获取分类
                // blog.setCategory(categoryMapper.selectById(blog.getCid()));
                // // 返回所有标签
                // LambdaQueryChainWrapper<BlogTag> bt = new LambdaQueryChainWrapper<>(blogTagMapper);
                // List<BlogTag> list = bt.eq(BlogTag::getBlogId, blog.getBlogId()).list();
                // List<Tag> tags = new ArrayList<>();
                // for (BlogTag blogTag : list) {
                //     // 获取标签名
                //     if (blogTag.getTagId() != null) {
                //         tags.add(tagMapper.selectById(blogTag.getTagId()));
                //     }
                //
                // }
                // //添加标签名
                // blog.setTags(tags);
                getBlogInfo(blog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ipage;
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
