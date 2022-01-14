package com.wuxin.blog.service;

import com.wuxin.blog.pojo.blog.Archive;
import com.wuxin.blog.mode.Base.PageService;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/9:24
 * @Description:
 */
public interface ArchiveService extends PageService<Archive> {

    /**
     * 根据blogID删除归档
     * @param blogId blogId
     */
    void delArchiveByBlogId(Long blogId);


    /**
     * 返回归档列表
     * @return list
     */
    List<Archive> findArchiveList();

    /**
     * 根据blogID显示归档内容
     * @param blogId blogID
     * @return DTO
     */
    Archive findArchiveByBlogId(Long blogId);

    /**
     * 获取全部归档
     * @return list
     */
    List<Archive> findArchiveAll();
}
