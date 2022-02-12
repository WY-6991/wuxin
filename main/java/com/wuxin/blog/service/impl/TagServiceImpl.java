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
        if(!ListUtil.listEqual(ids,tagIds)){
            delBlogTagByBlogId(blogId);
            // 添加
            addBlogTag(blogId,tagIds);
        }

    }

    @Override
    public void delBlogTagByBlogId(Long blogId) {
        LambdaQueryWrapper<BlogTag> objectLambdaQueryWrapper = new LambdaQueryWrapper<BlogTag>();
        objectLambdaQueryWrapper.eq(BlogTag::getBlogId, blogId);
        blogTagMapper.delete(objectLambdaQueryWrapper);
    }


    @Override
    public List<Blog> findBlogByTagName(Integer current, Integer size, String tagName) {

        List<Blog> blogList = new ArrayList<>();
        // 获取对应tagId
        LambdaQueryChainWrapper<Tag> tagLambdaQueryChainWrapper = new LambdaQueryChainWrapper<>(tagMapper);
        Long tagId = tagLambdaQueryChainWrapper.eq(Tag::getName, tagName).one().getTagId();
        LambdaQueryChainWrapper<BlogTag> blogTagLambdaQueryChainWrapper = new LambdaQueryChainWrapper<>(blogTagMapper);

        // 获取blogList
        Page<BlogTag> blogTagPage = new Page<>(current, size);
        List<BlogTag> list = blogTagLambdaQueryChainWrapper.eq(BlogTag::getTagId, tagId).page(blogTagPage).getRecords();
        // 判断标签中是否含有内容
        if (list.size() == 0) {
            throw new CustomException("该文章没有任何标签！");
        }
        list.forEach(blogTag -> {
            // 获取blog
            Blog blogInfo = getBlogInfo(blogMapper.selectById(blogTag.getBlogId()));
            blogList.add(blogInfo);
        });

        return blogList;
    }



    @Override
    public List<Object> blogCountByTagName() {
        List<Tag> list = list();
        // 得到tagList
        List<Object> arrayList = new ArrayList<>();
        list.forEach(tag -> {
            // 从blogTag中查找符合 blogTag.getTagId() == tag.getTagId()的文章数量
            Integer count = MapperUtils.lambdaQueryWrapper(blogTagMapper).eq(BlogTag::getTagId, tag.getTagId()).count();
            Map<String, Object> map = new HashMap<>();
            map.put("name", tag.getName());
            map.put("value", count);
            arrayList.add(map);
        });
        return arrayList;
    }

    public Blog getBlogInfo(Blog blog) {

        blog.setUsername(userMapper.selectById(blog.getUserId()).getNickname());
        // 获取分类
        blog.setCategory(categoryMapper.selectById(blog.getCid()));
        // 返回所有标签
        LambdaQueryChainWrapper<BlogTag> bt = new LambdaQueryChainWrapper<>(blogTagMapper);
        List<BlogTag> list = bt.eq(BlogTag::getBlogId, blog.getBlogId()).list();
        List<Tag> tags = new ArrayList<>();
        for (BlogTag blogTag : list) {
            // 获取标签名
            Tag tag = tagMapper.selectById(blogTag.getTagId());
            ThrowUtils.isNull(tag, "该标签不存在！");
            tags.add(tag);
        }
        //添加标签名
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

