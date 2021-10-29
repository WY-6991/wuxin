package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.ArchiveMapper;
import com.wuxin.blog.pojo.Archive;
import com.wuxin.blog.service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/9:25
 * @Description:
 */
@Service
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired
    private ArchiveMapper archiveMapper;

    @Override
    public int addArchive(Archive archive) {
        return archiveMapper.insert(archive);
    }

    @Override
    public boolean updateArchive(Archive archive) {
        LambdaUpdateChainWrapper<Archive> chainWrapper = new LambdaUpdateChainWrapper<>(archiveMapper);
        return  chainWrapper.eq(Archive::getArchiveId,archive.getArchiveId()).update(archive);
    }

    @Override
    public int delArchive(Long archiveId) {

        return archiveMapper.deleteById(archiveId);
    }

    @Override
    public int delArchiveByBlogId(Long blogId) {
        LambdaQueryWrapper<Archive> queryChainWrapper= new LambdaQueryWrapper<>();
        queryChainWrapper.eq(Archive::getBlogId,blogId);
        return archiveMapper.delete(queryChainWrapper);
    }

    @Override
    public IPage<Archive> findArchive(Integer current, Integer limit) {
        LambdaQueryChainWrapper<Archive> wrapper = new LambdaQueryChainWrapper<>(archiveMapper);
        // 按照时间查询,按照时间排序
        Page<Archive> archivePage = new Page<>(current,limit);
        return  wrapper.orderByDesc(Archive::getCreateTime).page(archivePage);
    }

    @Override
    public List<Archive> findArchiveList() {
        return new LambdaQueryChainWrapper<Archive>(archiveMapper).list();
    }
}
