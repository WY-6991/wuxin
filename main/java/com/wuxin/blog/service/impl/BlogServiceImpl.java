package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.exception.CustomException;
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
import com.wuxin.blog.utils.ThrowUtils;
import com.wuxin.blog.utils.mapper.MapperUtils;
import com.wuxin.blog.utils.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:08
 * @Description:
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class BlogServiceImpl implements BlogService {

    public static final Integer ONE = 1;

    public static final Integer TWO = 2;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BlogTagMapper blogTagMapper;

    @Autowired
    RedisService redisService;

    @Override
    public Long addBlog(Blog blog) {
        blogMapper.insert(blog);
        // 删除redis中博客列表
        redisService.hdel(RedisKey.BLOG_LIST);
        return blog.getBlogId();
    }

    @Override
    public void delBlog(Long blogId) {
        // 删除redis中博客列表
        redisService.hdel(RedisKey.BLOG_LIST);
        // 设置 redis文章有效期
        redisService.hdel(RedisKey.BLOG,blogId);
        blogMapper.deleteById(blogId);
    }

    @Override
    public void delBlogByUserId(Long userId) {
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Blog::getUserId, userId);
        // 删除redis中博客列表
        redisService.hdel(RedisKey.BLOG_LIST);
        blogMapper.delete(queryWrapper);
    }

    @Override
    public void updateBlog(Blog blog) {
        // 修改redis中文章
        String redisKey = RedisKey.BLOG;
        // 存入redis中
        System.out.println("==========================从数据库中修改文章======================");
        redisService.hset(redisKey, blog.getBlogId(), blog);
        // 数据库中修改
        LambdaUpdateChainWrapper<Blog> uc = new LambdaUpdateChainWrapper<>(blogMapper);
        uc.eq(Blog::getBlogId, blog.getBlogId()).update(blog);
    }

    @Override
    public IPage<Blog> findBlog(Integer current, Integer limit) {
        // 先从redis中查找
        String redisKey = RedisKey.BLOG_LIST;
        // 判断hashkey是否存在
        boolean b = redisService.hHasKey(redisKey, current);
        if (b) {
            IPage<Blog> redisPage = (IPage<Blog>) redisService.hget(redisKey, current);
            if (redisPage.getRecords().size() != 0) {
                return redisPage;
            }
        }
        // 指定
        Page<Blog> page = MapperUtils.lambdaQueryWrapper(blogMapper).orderByDesc(Blog::getTop).orderByDesc(Blog::getCreateTime).page(new Page<>(current, limit));
        for (Blog blog : page.getRecords()) {
            getBlogInfo(blog);
        }
        System.out.println("==========================从数据库中获取文章里列表======================");
        // 如果没有将首页缓存起来
        redisService.hset(redisKey, current, page);
        return page;
    }

    @Override
    public List<Blog> newBlog() {
        // 缓存查找
        String redisKey = RedisKey.NEW_BLOG_LSIT;
        // 判断key是否存在
        boolean b = redisService.hasKey(redisKey);
        if (b) {
            // 返回
            List<Blog> list = (List<Blog>) redisService.get(redisKey);
            if (list.size() != 0) {
                return list;
            }
        }
        // 从数据库中获取list
        List<Blog> list = blogMapper.newBlog();
        System.out.println("==========================从数据库中获取最新文章列表=====================");
        // 没有缓存起来
        redisService.set(redisKey, list);
        return list;
    }

    @Override
    public Blog findBlogByBlogId(Long blogId) {
        // 从redis查找
        String redisKey = RedisKey.BLOG;
        boolean b = redisService.hHasKey(redisKey, blogId);
        if (b) {
            Blog blog = (Blog) redisService.hget(redisKey, blogId);
            // 如果blog对象不为空
            if (StringUtils.isNotNull(blog)) {
                return blog;
            }
        }
        // 从数据库中查找
        Blog blog = blogMapper.selectById(blogId);
        if (StringUtils.isNull(blog)) {
            throw new CustomException("文章不存在！");
        }
        // 处理blog其他信息
        Blog blogInfo = getBlogInfo(blog);
        // 存入redis
        System.out.println("==========================从数据库中获取文章详情=====================");
        redisService.hset(redisKey, blogId, blogInfo);
        return blogInfo;
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
        blogList.forEach(this::getBlogInfo);
        return blogList;
    }


    @Override
    public Integer countAllBlogViews() {
        int count = 0;
        List<Blog> blogList = new LambdaQueryChainWrapper<Blog>(blogMapper).list();
        for (Blog blog : blogList) {
            count = count + blog.getViews();
        }
        return count;
    }

    @Override
    public Integer blogCount() {
        // String redisKey = RedisKey.BLOG_COUNT;
        return blogMapper.selectCount(null);
    }


    @Override
    public IPage<Blog> findBlogPage(Integer current, Integer limit, String keywords, String start, String end) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Category> eq = lambdaQueryWrapper.eq(Category::getName, keywords);
        Category category = categoryMapper.selectOne(eq);
        // 文章列表
        Page<Blog> page = new Page<>(current, limit);
        // 查找条件
        Page<Blog> ipage;
        if (StringUtils.isNotNull(category)) {
            ipage = MapperUtils.lambdaQueryWrapper(blogMapper).orderByDesc(Blog::getCreateTime)
                    .like(StringUtils.isNotNull(keywords), Blog::getTitle, keywords)
                    // 大于开始时间
                    .ge(StringUtils.isNotNull(start), Blog::getCreateTime, start)
                    // 小于结束时间
                    .le(StringUtils.isNotNull(end), Blog::getCreateTime, end)
                    .or().eq(Blog::getCid, category.getCid()).page(page);
            ;

        } else {
            ipage = MapperUtils.lambdaQueryWrapper(blogMapper).orderByDesc(Blog::getCreateTime)
                    .like(StringUtils.isNotNull(keywords), Blog::getTitle, keywords)
                    // 大于开始时间
                    .ge(StringUtils.isNotNull(start), Blog::getCreateTime, start)
                    // 小于结束时间
                    .le(StringUtils.isNotNull(end), Blog::getCreateTime, end)
                    .page(page);
        }


        for (Blog blog : ipage.getRecords()) {
            // 获取用户
            getBlogInfo(blog);
        }
        return ipage;
    }


    @Override
    public Blog beforeBlog(Long blogId) {
        return blogMapper.beforeBlog(blogId);
    }

    @Override
    public Blog nextBlog(Long blogId) {
        return blogMapper.nextBlog(blogId);
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
            ThrowUtils.isNull(blogTag.getTagId(), "标签id不能为空！");
            Tag tag = tagMapper.selectById(blogTag.getTagId());
            ThrowUtils.isNull(tag, "标签不存在！");
            tags.add(tag);

        }
        //添加标签名
        blog.setTags(tags);
        return blog;
    }
}
