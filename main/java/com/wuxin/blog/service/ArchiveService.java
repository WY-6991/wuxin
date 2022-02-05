package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
     *
     * @param blogId blogId
     */
    void delArchiveByBlogId(Long blogId);


    /**
     * 返回归档列表
     *
     * @return list
     */
    List<Archive> findArchiveList();

    /**
     * 根据blogID显示归档内容
     *
     * @param blogId blogID
     * @return DTO
     */
    Archive findArchiveByBlogId(Long blogId);


    /**
     * 根据blogID显示归档内容
     *
     * @param archive blogID
     * @return DTO
     */
    Archive findArchive(Archive archive);


    /**
     * 日期查询
     *
     * @param current
     * @param limit
     * @param keywords
     * @param start
     * @param end
     * @return
     */
    IPage<Archive> selectListByPage(Integer current, Integer limit, String keywords, String start, String end);


}
