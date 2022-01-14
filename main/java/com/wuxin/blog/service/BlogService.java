package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.blog.Blog;

import java.util.List;



/**
 * @Author: wuxin001
 * @Date: 2021/10/01/9:24
 * @Description:
 */
public interface BlogService {


    /**
     * 添加blog
     * @param blog blog
     * @return int id
     */
    Long addBlog(Blog blog);


    /**
     * 删除blog 根据blogId
     * @param blogId userId
     */
    void delBlog(Long blogId);

    /**
     * 删除blog userid
     * @param userId userid
     */
    void delBlogByUserId(Long userId);


    /**
     * 修改blog
     * @param blog blog实体内容
     */
    void updateBlog(Blog blog);

    /**
     * front使用
     * 按照时间倒序分页
     * @param current current
     * @param size    limit
     * @return page
     */
    IPage<Blog> findBlog(Integer current, Integer size);

    /**
     * 获取最新blog
     * @return list
     */
    List<Blog> newBlog();


    /**
     * 根据blogID查找blog
     * @param blogId id
     * @return DTO
     */
    Blog findBlogByBlogId(Long blogId);


    /**
     * 根据用户分页显示博客
     * @param userId  userid
     * @param current current
     * @param size    limit
     * @return page
     */
    IPage<Blog> findBlogByUserId(Long userId, Integer current, Integer size);

    /**
     * 随机5篇博客
     * @return list
     */
    List<Blog> randomBlog();



    /**
     * 统计all blog点击量
     * @return int
     */
    Integer countAllBlogViews();


    /**
     * 统计blog count
     * @return  int
     */
    Integer blogCount();






    /**
     * 根据时间查询 博客 分页显示 blog
     * @param current 页码
     * @param limit 大小
     * @param keywords 关键字
     * @return page
     */
    IPage<Blog> findBlogPage(Integer current, Integer limit, String keywords,String start,String end);

    /**
     * 上一篇
     * @param blogId blogId
     * @return DTO
     */
    Blog beforeBlog(Long blogId);


    /**
     * 下一篇
     * @param blogId blogID
     * @return DTO
     */
    Blog nextBlog(Long blogId);


}
