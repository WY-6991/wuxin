package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.exception.CustomException;
import com.wuxin.blog.mapper.ArchiveMapper;
import com.wuxin.blog.pojo.blog.Archive;
import com.wuxin.blog.service.ArchiveService;
import com.wuxin.blog.utils.ThrowUtils;
import com.wuxin.blog.utils.mapper.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/9:25
 * @Description:
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired
    private ArchiveMapper archiveMapper;


    @Override
    public void add(Archive archive) {
        archiveMapper.insert(archive);
    }

    @Override
    public void update(Archive archive) {
        LambdaUpdateChainWrapper<Archive> chainWrapper = new LambdaUpdateChainWrapper<>(archiveMapper);
        boolean update = chainWrapper.eq(Archive::getArchiveId, archive.getArchiveId()).update(archive);
        if(!update){
            throw new CustomException("归档~修改失败！");
        }
    }

    @Override
    public void delete(Long archiveId) {
        ThrowUtils.ops(archiveMapper.deleteById(archiveId),"删除失败！");
    }

    @Override
    public void delArchiveByBlogId(Long blogId) {
        LambdaQueryWrapper<Archive> queryChainWrapper= new LambdaQueryWrapper<>();
        queryChainWrapper.eq(Archive::getBlogId,blogId);
        ThrowUtils.ops(archiveMapper.delete(queryChainWrapper),"删除失败！");

    }

    @Override
    public IPage<Archive> selectListByPage(Integer current, Integer limit) {
        LambdaQueryChainWrapper<Archive> queryChainWrapper = new LambdaQueryChainWrapper<>(archiveMapper);
        return queryChainWrapper.orderByDesc(Archive::getCreateTime).page(new Page<Archive>(current,limit));
    }

    @Override
    public List<Archive> findArchiveList() {
        return new LambdaQueryChainWrapper<Archive>(archiveMapper).list();
    }

    @Override
    public Archive findArchiveByBlogId(Long blogId) {
        Archive archive = MapperUtils.lambdaQueryWrapper(archiveMapper).eq(Archive::getBlogId, blogId).one();
        ThrowUtils.isNull(archive,"归档内容不存在！");
        return archive ;
    }

    @Override
    public List<Archive> findArchiveAll() {
        return MapperUtils.lambdaQueryWrapper(archiveMapper).orderByDesc(Archive::getCreateTime).list();
    }

    @Override
    public Archive find(Long id) {
        Archive archive = MapperUtils.lambdaQueryWrapper(archiveMapper).eq(Archive::getBlogId, id).one();
        ThrowUtils.isNull(archive,"归档内容不存在！");
        return archive ;
    }

    @Override
    public List<Archive> list() {
        return MapperUtils.lambdaQueryWrapper(archiveMapper).orderByDesc(Archive::getCreateTime).list();
    }

    @Override
    public IPage<Archive> selectListByPage(Integer current, Integer limit, String keywords) {
        return null;
    }
}
