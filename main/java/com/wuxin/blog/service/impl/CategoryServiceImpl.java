package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.exception.NotFoundException;
import com.wuxin.blog.mapper.BlogMapper;
import com.wuxin.blog.mapper.CategoryMapper;
import com.wuxin.blog.mapper.TagMapper;
import com.wuxin.blog.mapper.UserMapper;
import com.wuxin.blog.mapper.vo.BlogTagMapper;
import com.wuxin.blog.pojo.blog.Blog;
import com.wuxin.blog.pojo.blog.Category;
import com.wuxin.blog.pojo.blog.Tag;
import com.wuxin.blog.pojo.blog.BlogTag;
import com.wuxin.blog.redis.RedisKey;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.service.BlogService;
import com.wuxin.blog.service.CategoryService;
import com.wuxin.blog.utils.ThrowUtils;
import com.wuxin.blog.utils.mapper.MapperUtils;
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


    @Autowired
    private RedisService redisService;


    @Override
    public void add(Category category) {
        ThrowUtils.ops(categoryMapper.insert(category), "操作失败");
        addCategoryCache(category);
    }

    @Override
    public void delete(Long id) {
        ThrowUtils.ops(categoryMapper.deleteById(id), "分类标签不存在！");
        deleteCategoryCache(id);
    }

    @Override
    public void update(Category category) {
        ThrowUtils.ops(categoryMapper.updateById(category), "操作失败！标签不存在");
        updateCategoryCache(category);
    }

    @Override
    public List<Category> list() {
        // 获取所有category
        String redisKey = RedisKey.CATEGORY_LIST;
        // 判断key是否存在
        boolean b = redisService.hasKey(redisKey);
        if (b) {
            List<Category> list = (List<Category>) redisService.get(redisKey);
            if (list.size() != 0) {
                return list;
            }
        }
        // redis 中获取不到categoryList 从数据库中获取并且添加到redis中
        List<Category> categories = categoryMapper.selectList(null);
        // 存入redis
        redisService.set(redisKey, categories);
        return categories;
    }

    @Override
    public Category findCategoryByName(String name) {
        return MapperUtils.lambdaQueryWrapper(categoryMapper).eq(Category::getName, name).one();
    }

    @Override
    public IPage<Blog> findBlogByCategoryName(Integer current, Integer size, String categoryName) {
        Long cid = MapperUtils.lambdaQueryWrapper(categoryMapper).eq(Category::getName, categoryName).one().getCid();
        ThrowUtils.isNull(cid);
        if (cid == 0) {
            throw new NotFoundException("不存在该分类标签");
        }
        Page<Blog> blogPage = new Page<>(current, size);
        Page<Blog> ipage = MapperUtils.lambdaQueryWrapper(blogMapper).eq(Blog::getCid, cid).orderByDesc(Blog::getTop).orderByDesc(Blog::getCreateTime).page(blogPage);
        ipage.getRecords().forEach(this::getBlogInfo);
        return ipage;

    }

    @Override
    public Category find(Long id) {
        return null;
    }

    @Override
    public IPage<Category> selectListByPage(Integer current, Integer limit, String keywords) {
        return MapperUtils.lambdaQueryWrapper(categoryMapper)
                .like(StringUtils.isNotNull(keywords), Category::getName, keywords)
                .page(new Page<Category>(current, limit));
    }

    public Blog getBlogInfo(Blog blog) {
        String nickname = userMapper.selectById(blog.getUserId()).getNickname();
        ThrowUtils.isNull(nickname, "用户名不存在！");
        blog.setUsername(nickname);
        Category category = categoryMapper.selectById(blog.getCid());
        ThrowUtils.isNull(category, "用户名不存在！");
        blog.setCategory(category);
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

    @Override
    public List<Object> blogCountByCategoryName() {
        String redisKey = RedisKey.CATEGORY_COUNT;
        boolean b = redisService.hasKey(redisKey);
        if (b) {
            List<Object> arrayList = (List<Object>) redisService.get(redisKey);
            if (arrayList.size() != 0) {
                return arrayList;
            }
        }
        List<Category> list = new LambdaQueryChainWrapper<Category>(categoryMapper).list();
        List<Object> arrayList = new ArrayList<>();
        list.forEach(category -> {
            // 获取blogCount根据categoryName
            Map<String, Object> map = new HashMap<>();
            int count = new LambdaQueryChainWrapper<Blog>(blogMapper).eq(Blog::getCid, category.getCid()).count();
            map.put("name", category.getName());
            map.put("value", count);
            arrayList.add(map);
        });
        // 将数据统计如数据库中
        redisService.set(redisKey, arrayList);
        return arrayList;
    }

    @Override
    public IPage<Category> selectListByPage(Integer current, Integer limit) {
        return null;
    }


    public void deleteCategoryCache(Long id) {
        List<Category> list = list();
        list.removeIf(value -> value.getCid().equals(id));
        redisService.set(RedisKey.CATEGORY_LIST, list);
    }

    public void updateCategoryCache(Category category) {
        List<Category> list = list();
        for (Category value : list) {
            if (value.getCid().equals(category.getCid())) {
                // 修改tag
                value.setColor(category.getColor());
                value.setName(category.getName());
            }
        }
        redisService.set(RedisKey.CATEGORY_LIST, list);
    }

    public void addCategoryCache(Category category) {
        List<Category> list = list();
        list.add(category);
        redisService.set(RedisKey.CATEGORY_LIST, list);
    }

}
