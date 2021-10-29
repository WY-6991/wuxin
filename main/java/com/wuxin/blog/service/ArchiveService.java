package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.Archive;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/9:24
 * @Description:
 */
public interface ArchiveService {

    int addArchive(Archive archive);

    boolean updateArchive(Archive archive);

    int delArchive(Long archiveId);

    int delArchiveByBlogId(Long blogId);

    IPage<Archive> findArchive(Integer current, Integer limit);

    List<Archive> findArchiveList();
}
