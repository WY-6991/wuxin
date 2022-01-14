package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.ArchiveMapper;
import com.wuxin.blog.mapper.vo.ArchiveTitleMapper;
import com.wuxin.blog.pojo.blog.Archive;
import com.wuxin.blog.pojo.blog.ArchiveTitle;
import com.wuxin.blog.service.ArchiveTitleService;
import com.wuxin.blog.utils.ThrowUtils;
import com.wuxin.blog.utils.mapper.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:08
 * @Description:
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class ArchiveTitleServiceImpl implements ArchiveTitleService {

    @Autowired
    private ArchiveTitleMapper archiveTitleMapper;

    @Autowired
    private ArchiveMapper archiveMapper;

    @Override
    public void add(String title) {
        ArchiveTitle archiveTitle = new ArchiveTitle();
        archiveTitle.setArchiveTitle(title);
        ThrowUtils.ops(archiveTitleMapper.insert(archiveTitle),"添加失败");
    }

    @Override
    public void delete(Long id) {
        ThrowUtils.ops(archiveTitleMapper.deleteById(id),"内容不存在");
    }

    @Override
    public void update(ArchiveTitle archiveTitle) {
        ThrowUtils.ops(archiveTitleMapper.updateById(archiveTitle),"内容不存在");

    }

    @Override
    public ArchiveTitle selectArchiveTitle(String title) {
        ArchiveTitle one = MapperUtils.lambdaQueryWrapper(archiveTitleMapper).eq(ArchiveTitle::getArchiveTitle, title).one();
        ThrowUtils.isNull(one,"归档内容不存在！");
        return one;
    }


    @Override
    public IPage<ArchiveTitle> selectListByPage(int current,int limit) {
        Page<ArchiveTitle> archiveTitlePage = new Page<>(current, limit);
        Page<ArchiveTitle> page = MapperUtils.lambdaQueryWrapper(archiveTitleMapper).orderByDesc(ArchiveTitle::getId).page(archiveTitlePage);
        page.getRecords().forEach(archiveTitle -> {
            List<Archive> list = MapperUtils.lambdaQueryWrapper(archiveMapper).eq(Archive::getArchiveTitle, archiveTitle.getArchiveTitle()).list();
            archiveTitle.setArchiveList(list);
        });
        return page;
    }


    @Override
    public List<ArchiveTitle> list() {
        List<ArchiveTitle> archiveTitleList = MapperUtils.lambdaQueryWrapper(archiveTitleMapper).orderByDesc(ArchiveTitle::getId).list();
        archiveTitleList.forEach(archiveTitle -> {
            List<Archive> list = MapperUtils.lambdaQueryWrapper(archiveMapper).orderByDesc(Archive::getCreateTime).eq(Archive::getArchiveTitle, archiveTitle.getArchiveTitle()).list();
            archiveTitle.setArchiveList(list);
        });

        return archiveTitleList;
    }

    @Override
    public void add(ArchiveTitle archiveTitle) {

    }

    @Override
    public ArchiveTitle find(Long id) {
        return null;
    }

}
