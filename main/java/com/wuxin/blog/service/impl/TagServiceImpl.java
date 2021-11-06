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
import com.wuxin.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BlogTagMapper blogTagMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogService blogService;


    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public int addTag(Tag tag) {
        return tagMapper.insert(tag);
    }

    @Override
    public int delTag(Long id) {
        return tagMapper.deleteById(id);
    }

    @Override
    public boolean updateTag(Tag tag) {
        return new LambdaUpdateChainWrapper<Tag>(tagMapper)
                .eq(Tag::getTagId, tag.getTagId()).update(tag);
    }




    @Override
    public Tag findTagByName(String tagName) {
        return new LambdaQueryChainWrapper<Tag>(tagMapper).eq(Tag::getName, tagName).one();

    }

    @Override
    public List<Tag> findTag() {
        return tagMapper.selectList(null);
    }

    @Override
    public List<Tag> selectBlogTag(Long blogId) {
        LambdaQueryChainWrapper<BlogTag> chain = new LambdaQueryChainWrapper<>(blogTagMapper);
        List<BlogTag> list = chain.eq(BlogTag::getBlogId, blogId).list();
        ArrayList<Tag> tags = new ArrayList<>();
        for (BlogTag blogTag : list) {
            tags.add(tagMapper.selectById(blogTag.getTagId()));
        }
        return tags;
    }

    @Override
    public int addBlogTag(Long blogId, List<Long> tagIds) {
        tagIds.forEach(tagId->{
            BlogTag blogTag = new BlogTag();
            blogTag.setBlogId(blogId);
            blogTag.setTagId(tagId);
            blogTagMapper.insert(blogTag);
        });
        return 1;

    }

    @Override
    public int delBlogTagByBlogId(Long blogId) {
        // LambdaQueryChainWrapper<BlogTag> chainWrapper = new LambdaQueryChainWrapper<BlogTag>(blogTagMapper);
        // chainWrapper.eq(BlogTag::getBlogId,blogId);
        // return blogTagMapper.delete(chainWrapper);

        LambdaQueryWrapper<BlogTag> objectLambdaQueryWrapper = new LambdaQueryWrapper<BlogTag>();
        objectLambdaQueryWrapper.eq(BlogTag::getBlogId,blogId);
        return blogTagMapper.delete(objectLambdaQueryWrapper);
    }





    // 标签名->找到标签id->从blogTag中找出blogId->根据blogId找出blog和用户名
    @Override
    public List<Blog> findBlogByTagName(Integer current, Integer size, String tagName) {

        // Page<Blog> blogPage = new Page<>(current,size);
        List<Blog> blogList = new ArrayList<>();
        // 获取对应tagId
        LambdaQueryChainWrapper<Tag> tagLambdaQueryChainWrapper = new LambdaQueryChainWrapper<>(tagMapper);
        Long tagId = tagLambdaQueryChainWrapper.eq(Tag::getName, tagName).one().getTagId();
        LambdaQueryChainWrapper<BlogTag> blogTagLambdaQueryChainWrapper = new LambdaQueryChainWrapper<>(blogTagMapper);

        // 获取blogList
        Page<BlogTag> blogTagPage = new Page<>(current, size);
        List<BlogTag> list = blogTagLambdaQueryChainWrapper.eq(BlogTag::getTagId, tagId).page(blogTagPage).getRecords();
        // 判断标签中是否含有内容
        if (list.size() == 0) return null;
        for (BlogTag blogTag : list) {
            //  获取一篇blog信息
            Blog blog = blogService.findBlogByBlogId(blogTag.getBlogId());
            //将blog信息添加到BlogList
            blogList.add(blog);
        }

        return blogList;
    }


    @Override
    public Integer findTagCount() {
        return tagMapper.selectCount(null);
    }


    @Override
    public IPage<Tag> findTagByPage(Integer current, Integer limit, String keywords) {
        LambdaQueryChainWrapper<Tag> chainWrapper = new LambdaQueryChainWrapper<>(tagMapper);
        Page<Tag> tagPage = new Page<>(current,limit);
        return chainWrapper.like(!keywords.isEmpty(),Tag::getName,keywords).page(tagPage);
    }

    public Blog getBlogInfo(Blog blog){

        // blog.setUsername(userMapper.selectById(blog.getUserId()).getNickname());
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

