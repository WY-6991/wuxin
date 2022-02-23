package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.exception.CustomException;
import com.wuxin.blog.mapper.BlogMapper;
import com.wuxin.blog.mapper.CategoryMapper;
import com.wuxin.blog.mapper.TagMapper;
import com.wuxin.blog.mapper.UserMapper;
import com.wuxin.blog.mapper.BlogTagMapper;
import com.wuxin.blog.pojo.blog.Blog;
import com.wuxin.blog.pojo.blog.BlogTag;
import com.wuxin.blog.pojo.blog.Tag;
import com.wuxin.blog.redis.RedisKey;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.service.BlogService;
import com.wuxin.blog.service.TagService;
import com.wuxin.blog.utils.ThrowUtils;
import com.wuxin.blog.utils.mapper.MapperUtils;
import com.wuxin.blog.utils.string.ListUtil;
import com.wuxin.blog.utils.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:08
 * @Description:
 */
@Service
@Transactional(rollbackFor = {Exception.class})
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
    private RedisService redisService;


    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Tag tag) {
        ThrowUtils.ops(tagMapper.insert(tag), "标签添加失败！");
        // 这里一定要放在后面，否则会获取不到Id
        addTagCache(tag);
    }

    @Override
    public void delete(Long id) {
        ThrowUtils.ops(tagMapper.deleteById(id), "删除失败！标签不存在");
        deleteTagListCache(id);
    }

    @Override
    public void update(Tag tag) {
        ThrowUtils.ops(tagMapper.updateById(tag), "修改失败！标签不存在");
        updateTagCache(tag);
    }

    @Override
    public Tag findTagByName(String tagName) {
        return MapperUtils.lambdaQueryWrapper(tagMapper).eq(Tag::getName, tagName).one();
    }

    @Override
    public List<Tag> list() {
        // 将tagLit存入redis中
        String redisKey = RedisKey.TAG_LIST;
        boolean b = redisService.hasKey(redisKey);
        if (b) {
            List<Tag> list = (List<Tag>) redisService.get(redisKey);
            if (list.size() != 0) {
                return list;
            }
        }
        // 从数据库中获取tagList
        List<Tag> tags = tagMapper.selectList(null);
        redisService.set(redisKey, tags);
        return tags;
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
    public Tag find(Long id) {
        return tagMapper.selectById(id);
    }

    @Override
    public IPage<Tag> selectListByPage(Integer current, Integer limit) {
        return null;
    }

    @Override
    public IPage<Tag> selectListByPage(Integer current, Integer limit, String keywords) {
        return MapperUtils.lambdaQueryWrapper(tagMapper)
                .like(StringUtils.isNotEmpty(keywords), Tag::getName, keywords)
                .orderByDesc(Tag::getTagId).page(new Page<>(current, limit));
    }


    @Override
    public void addBlogTag(Long blogId, List<Long> tagIds) {
        tagIds.forEach(tagId -> {
            BlogTag blogTag = new BlogTag();
            blogTag.setBlogId(blogId);
            blogTag.setTagId(tagId);
            blogTagMapper.insert(blogTag);
        });

    }

    @Override
    public void updateBlogTag(Long blogId, List<Long> tagIds) {
        // 比较是否改变了
        List<BlogTag> blogTags = MapperUtils.lambdaQueryWrapper(blogTagMapper).eq(BlogTag::getBlogId, blogId).list();
        List<Long> ids = new ArrayList<>();
        blogTags.forEach(blogTag -> {
            ids.add(blogTag.getTagId());
        });
        // 如果相等就不修改
        if (!ListUtil.listEqual(ids, tagIds)) {
            delBlogTagByBlogId(blogId);
            addBlogTag(blogId, tagIds);
        }

    }

    @Override
    public void delBlogTagByBlogId(Long blogId) {
        LambdaQueryWrapper<BlogTag> objectLambdaQueryWrapper = new LambdaQueryWrapper<BlogTag>();
        objectLambdaQueryWrapper.eq(BlogTag::getBlogId, blogId);
        blogTagMapper.delete(objectLambdaQueryWrapper);
    }


    @Override
    public IPage<Blog> findBlogByTagName(Integer current, Integer size, String tagName) {
        Page<Blog> blogPage = new Page<>();
        // tag
        Tag tag = MapperUtils.lambdaQueryWrapper(tagMapper).eq(Tag::getName, tagName).one();
        if (tag == null) {
            return blogPage;
        }
        // tagList
        IPage<BlogTag> blogTagIPage = MapperUtils.lambdaQueryWrapper(blogTagMapper).eq(BlogTag::getTagId, tag.getTagId()).page(new Page<>(current, size));
        if (blogTagIPage.getRecords().size() == 0) {
            return blogPage;
        }
        List<Blog> blogList = new ArrayList<>();
        // 获取文章基本信息
        blogTagIPage.getRecords().forEach(blogTag -> {
            Blog blog = MapperUtils.lambdaQueryWrapper(blogMapper).eq(Blog::getBlogId, blogTag.getBlogId()).eq(Blog::isPublish, 1)
                    .select(
                            Blog::getBlogId, Blog::getCreateTime, Blog::getCid, Blog::getDescription, Blog::getTop, Blog::getTitle,
                            Blog::getViews
                    ).one();
            // 获取其他信息 文章标签 分类
            Blog info = getBlogInfo(blog);
            blogList.add(info);
        });
        blogPage.setCurrent(blogTagIPage.getCurrent());
        blogPage.setSize(blogTagIPage.getSize());
        blogPage.setTotal(blogTagIPage.getTotal());
        blogPage.setRecords(blogList);
        blogPage.setPages(blogTagIPage.getPages());
        return blogPage;
    }


    @Override
    public List<Object> blogCountByTagName() {
        List<Tag> list = list();
        List<Object> arrayList = new ArrayList<>();
        list.forEach(tag -> {
            Integer count = MapperUtils.lambdaQueryWrapper(blogTagMapper).eq(BlogTag::getTagId, tag.getTagId()).count();
            Map<String, Object> map = new HashMap<>();
            map.put("name", tag.getName());
            map.put("value", count);
            arrayList.add(map);
        });
        return arrayList;
    }

    public Blog getBlogInfo(Blog blog) {
        if (blog.getCid() != null) {
            blog.setCategory(categoryMapper.selectById(blog.getCid()));
        }
        List<Tag> tags = new ArrayList<>();
        List<BlogTag> list = MapperUtils.lambdaQueryWrapper(blogTagMapper).eq(BlogTag::getBlogId, blog.getBlogId()).list();
        //添加标签名
        if (list.size() == 0) {
            return blog;
        }
        list.forEach(blogTag -> {
            Tag tag = tagMapper.selectById(blogTag.getTagId());
            tags.add(tag);
        });
        blog.setTags(tags);
        return blog;
    }

    public void deleteTagListCache(Long id) {
        List<Tag> list = list();
        list.removeIf(value -> value.getTagId().equals(id));
        redisService.set(RedisKey.TAG_LIST, list);
    }

    public void updateTagCache(Tag tag) {
        List<Tag> list = list();
        for (Tag value : list) {
            if (value.getTagId().equals(tag.getTagId())) {
                // 修改tag
                value.setColor(tag.getColor());
                value.setName(tag.getName());
            }
        }
        redisService.set(RedisKey.TAG_LIST, list);
    }

    public void addTagCache(Tag tag) {
        List<Tag> list = list();
        list.add(tag);
        redisService.set(RedisKey.TAG_LIST, list);
    }
}

