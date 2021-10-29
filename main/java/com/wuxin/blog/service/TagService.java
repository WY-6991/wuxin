package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.Blog;
import com.wuxin.blog.pojo.Tag;

import java.util.List;

public interface TagService {

    /**
     * 添加标签
     *
     * @param tag tagDTO
     * @return int
     */
    int addTag(Tag tag);

    /**
     * @param id tagID
     * @return
     */
    int delTag(Long id);

    /**
     * @param tag
     * @return
     */
    boolean updateTag(Tag tag);

    /**
     * @param tagName
     * @return
     */
    Tag findTagByName(String tagName);

    /**
     * @return
     */
    List<Tag> findTag();


    /**
     * 返回blogtag
     */
    List<Tag> selectBlogTag(Long blogId);

    /**
     * @param blogId blogId
     * @param tagIds  tagId
     * @return int
     */
    int addBlogTag(Long blogId, List<Long> tagIds);

    /**
     * @param blogId blogId
     * @return 1
     */
    int delBlogTagByBlogId(Long blogId);


    /**
     * 根据标签名显示blog
     *
     * @param current current
     * @param size    size
     * @param tagName 标签名
     * @return records
     */
    List<Blog> findBlogByTagName(Integer current, Integer size, String tagName);

    /**
     * 统计标签信息
     *
     * @return count
     */
    Integer findTagCount();


    /**
     * 分页搜索tag
     * @param current curr
     * @param limit limit
     * @param keywords 内容
     * @return 返回结果
     */
    IPage<Tag> findTagByPage(Integer current, Integer limit, String keywords);
}
