package com.wuxin.blog.service;

import com.wuxin.blog.pojo.blog.Blog;
import com.wuxin.blog.pojo.blog.Tag;
import com.wuxin.blog.mode.Base.PageService;

import java.util.List;


/**
 * @Author: wuxin001
 * @Date: 2021/10/01/9:24
 * @Description:
 */
public interface TagService extends PageService<Tag> {


    /**
     * 查询 tag
     * @param tagName name
     * @return DTO
     */
    Tag findTagByName(String tagName);


    /**
     * 获取blogTag
     * @param blogId id
     * @return list
     */
    List<Tag> selectBlogTag(Long blogId);

    /**
     * 添加blog 标签
     * @param blogId blogId
     * @param tagIds  tagId
     */
    void addBlogTag(Long blogId, List<Long> tagIds);


    /**
     * 修改blogtagIds
     * @param blogId blogId
     * @param tagIds tagList
     */
    void updateBlogTag(Long blogId, List<Long> tagIds);

    /**
     * 删除blogTag
     * @param blogId id
     */
    void delBlogTagByBlogId(Long blogId);


    /**
     * 根据标签名显示blog
     * @param current current
     * @param size    size
     * @param tagName 标签名
     * @return page
     */
    List<Blog> findBlogByTagName(Integer current, Integer size, String tagName);


    /**
     * 后台首页实现tag文章种类分页
     * @return
     */
    List<Object> blogCountByTagName();


}
