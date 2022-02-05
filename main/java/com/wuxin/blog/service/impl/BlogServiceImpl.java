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
import com.wuxin.blog.mapper.vo.CommentMapper;
import com.wuxin.blog.mode.SearchBlog;
import com.wuxin.blog.pojo.blog.*;
import com.wuxin.blog.redis.RedisKey;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.service.BlogService;
import com.wuxin.blog.utils.ThrowUtils;
import com.wuxin.blog.utils.mapper.MapperUtils;
import com.wuxin.blog.utils.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private BlogTagMapper blogTagMapper;

    @Autowired
    RedisService redisService;

    @Override
    public Long addBlog(Blog blog) {
        blogMapper.insert(blog);
        // 删除redis中博客列表
        deleteBlogListCache();
        return blog.getBlogId();
    }

    @Override
    public String findCommentBlogTitle(Long blogId) {
        return MapperUtils.lambdaQueryWrapper(blogMapper).eq(Blog::getBlogId, blogId).select(Blog::getBlogId, Blog::getTitle).one().getTitle();
    }

    @Override
    public List<Blog> getAllBlogList() {
        return MapperUtils.lambdaQueryWrapper(blogMapper).select(Blog::getBlogId, Blog::getTitle, Blog::getCreateTime).orderByDesc(Blog::getCreateTime).list();
    }

    @Override
    public List<SearchBlog> searchBlog(String keywords) {
        return blogMapper.searchBlog(keywords);
    }

    @Override
    public void delBlog(Long blogId) {
        // 删除redis中博客列表
        deleteBlogListCache();
        blogMapper.deleteById(blogId);
    }

    @Override
    public void delBlogByUserId(Long userId) {
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Blog::getUserId, userId);
        deleteBlogListCache();
        blogMapper.delete(queryWrapper);
    }

    @Override
    public void updateBlog(Blog blog) {
        String redisKey = RedisKey.BLOG;
        // redisService.hset(redisKey, blog.getBlogId(), blog);
        // System.out.println("redis blog=>"+redisService.hget(redisKey,blog.getBlogId()));
        redisService.hdel(redisKey, blog.getBlogId());
        MapperUtils.lambdaUpdateChainWrapper(blogMapper).eq(Blog::getBlogId, blog.getBlogId()).update(blog);
        deleteBlogListCache();
    }

    @Override
    public IPage<Blog> findBlog(Integer current, Integer limit) {
        String redisKey = RedisKey.BLOG_LIST;
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
        // 如果没有将缓存起来
        redisService.hset(redisKey, current, page);

        return page;
    }

    @Override
    public List<SearchBlog> newBlog() {
        // 缓存查找
        String redisKey = RedisKey.NEW_BLOG_LSIT;
        // 判断key是否存在
        boolean b = redisService.hasKey(redisKey);
        if (b) {
            // 返回
            List<SearchBlog> list = (List<SearchBlog>) redisService.get(redisKey);
            if (list.size() != 0) {
                return list;
            }
        }
        // 从数据库中获取list
        List<SearchBlog> list = blogMapper.newBlog();
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
        Category category = null;
        Page<Blog> ipage;
        if (StringUtils.isNotNull(keywords)) {
            category = MapperUtils.lambdaQueryWrapper(categoryMapper)
                    .eq( Category::getName, keywords).one();
        }


        if (StringUtils.isNotNull(category)) {
            ipage = MapperUtils.lambdaQueryWrapper(blogMapper).orderByDesc(Blog::getCreateTime)

                    .like(true, Blog::getTitle, keywords)
                    // 大于开始时间
                    .ge(StringUtils.isNotNull(start), Blog::getCreateTime, start)
                    // 小于结束时间
                    .le(StringUtils.isNotNull(end), Blog::getCreateTime, end)
                    .or().eq(Blog::getCid, category.getCid())
                    .page(new Page<>(current, limit));

        } else {
            ipage = MapperUtils.lambdaQueryWrapper(blogMapper).orderByDesc(Blog::getCreateTime)
                    .like(StringUtils.isNotNull(keywords), Blog::getTitle, keywords)
                    // 大于开始时间
                    .ge(StringUtils.isNotNull(start), Blog::getCreateTime, start)
                    // 小于结束时间
                    .le(StringUtils.isNotNull(end), Blog::getCreateTime, end)
                    .page(new Page<>(current, limit));
        }

        // 获取文章基本信息
        ipage.getRecords().forEach(this::getBlogInfo);
        return ipage;
    }


    @Override
    public SearchBlog beforeBlog(Long blogId) {
        return blogMapper.beforeBlog(blogId);
    }

    @Override
    public SearchBlog nextBlog(Long blogId) {
        return blogMapper.nextBlog(blogId);
    }

    public Blog getBlogInfo(Blog blog) {
        blog.setUsername(userMapper.selectById(blog.getUserId()).getNickname());
        // 获取分类
        blog.setCategory(categoryMapper.selectById(blog.getCid()));
        // 获取评论数量
        Integer count = MapperUtils.lambdaQueryWrapper(commentMapper).eq(Comment::getType, Comment.BLOG_COMMENT).eq(StringUtils.isNotNull(blog.getBlogId()), Comment::getBlogId, blog.getBlogId()).count();
        blog.setCommentNum(count);
        // 返回所有标签
        List<BlogTag> list = MapperUtils.lambdaQueryWrapper(blogTagMapper)
                .eq(BlogTag::getBlogId, blog.getBlogId()).list();
        List<Tag> tags = new ArrayList<>();
        list.forEach(blogTag -> {
            // 获取标签名
            Tag tag = tagMapper.selectById(blogTag.getTagId());
            tags.add(tag);
        });
        //添加标签名
        blog.setTags(tags);
        return blog;
    }

    /**
     * 使用redis遍历删除blog-list
     */
    public void deleteBlogListCache() {
        // 获取文章总数
        Integer total = blogMapper.selectCount(null);
        // 前台设置了页码大小为5
        Integer size = 5;
        // 获取页码总数
        int i = (total / size) + 1;
        for (int i1 = 1; i1 < i; i1++) {
            boolean b = redisService.hHasKey(RedisKey.BLOG_LIST, i1);
            if (b) {
                redisService.hdel(RedisKey.BLOG_LIST, i1);
            }
        }

    }


    public void updateBlogListCache(Blog blog) {

    }
}
