package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.Blog;

import java.util.List;

public interface BlogService {


    /**
     * 添加blog
     *
     * @param blog blog
     * @return int
     * ogId);
     */
    Long addBlog(Blog blog);


    /**
     * 删除blog 根据blogId
     *
     * @param blogId userId
     * @return 1
     */
    int delBlog(Long blogId);

    /**
     * 删除blog userid
     *
     * @param userId userid
     * @return 1
     */
    int delBlogByUserId(Long userId);


    /**
     * 修改blog
     *
     * @param blog blog实体内容
     * @return int
     */
    boolean updateBlog(Blog blog);

    /**
     * 按照时间倒序分页
     *
     * @param current current
     * @param size    limit
     * @return list
     */
    IPage<Blog> findBlog(Integer current, Integer size);

    /**
     * 根据blogId显示blog
     */
    Blog findBlogByBlogId(Long blogId);


    /**
     * 根据用户分页显示博客
     *
     * @param userId  userid
     * @param current current
     * @param size    limit
     * @return list
     */
    IPage<Blog> findBlogByUserId(Long userId, Integer current, Integer size);

    /**
     * 随机5篇博客
     *
     * @return list
     */
    List<Blog> randomBlog();

    /**
     *
     * @param current
     * @param size
     * @return
     */
    IPage<Blog>  findAllBlog(Integer current, Integer size);

    /**
     * 统计all blog点击量
     * @return int
     */
    Integer countAllBlogViews();




    /**
     * 统计blog count
     * @return  int
     */
    Integer countAllBlog();



    /**
     * 分页显示blog
     * @param current
     * @param limit
     * @param keywords
     * @return 返回结果
     */
    IPage<Blog> findBlogPage(Integer current, Integer limit, String keywords);


}
