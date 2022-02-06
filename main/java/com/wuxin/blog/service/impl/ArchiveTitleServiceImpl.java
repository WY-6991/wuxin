package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.ArchiveMapper;
import com.wuxin.blog.mapper.vo.ArchiveTitleMapper;
import com.wuxin.blog.pojo.blog.Archive;
import com.wuxin.blog.pojo.blog.ArchiveTitle;
import com.wuxin.blog.pojo.blog.Category;
import com.wuxin.blog.redis.RedisKey;
import com.wuxin.blog.redis.RedisService;
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


    private static final String ARCHIVE_TITLE_LIST = RedisKey.ARCHIVE_TITLE_LIST;

    @Autowired
    private ArchiveTitleMapper archiveTitleMapper;

    @Autowired
    private ArchiveMapper archiveMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public void add(String title) {
        ArchiveTitle archiveTitle = new ArchiveTitle();
        archiveTitle.setArchiveTitle(title);
        ThrowUtils.ops(archiveTitleMapper.insert(archiveTitle),"添加失败");
        // addCache(archiveTitle);
        redisService.del(ARCHIVE_TITLE_LIST);
    }

    @Override
    public void delete(Long id) {
        ThrowUtils.ops(archiveTitleMapper.deleteById(id),"内容不存在");
        // deleteCache(id);
        redisService.del(ARCHIVE_TITLE_LIST);
    }

    @Override
    public void update(ArchiveTitle archiveTitle) {
        ThrowUtils.ops(archiveTitleMapper.updateById(archiveTitle),"内容不存在");
        // updateCache(archiveTitle);
        redisService.del(ARCHIVE_TITLE_LIST);
    }

    @Override
    public ArchiveTitle selectArchiveTitle(String title) {
        return MapperUtils.lambdaQueryWrapper(archiveTitleMapper).eq(ArchiveTitle::getArchiveTitle, title).one();
    }


    // @Override
    // public IPage<ArchiveTitle> selectListByPage(int current,int limit) {
    //     Page<ArchiveTitle> archiveTitlePage = new Page<>(current, limit);
    //     Page<ArchiveTitle> page = MapperUtils.lambdaQueryWrapper(archiveTitleMapper).orderByDesc(ArchiveTitle::getId).page(archiveTitlePage);
    //     page.getRecords().forEach(archiveTitle -> {
    //         List<Archive> list = MapperUtils.lambdaQueryWrapper(archiveMapper).eq(Archive::getArchiveTitle, archiveTitle.getArchiveTitle()).list();
    //         archiveTitle.setArchiveList(list);
    //     });
    //     return page;
    // }


    @Override
    public List<ArchiveTitle> list() {
        // redis
        boolean b = redisService.hasKey(ARCHIVE_TITLE_LIST);
        if(b){
            List<ArchiveTitle> list = (List<ArchiveTitle>) redisService.get(ARCHIVE_TITLE_LIST);
            if(list.size()!=0){
                return list;
            }
        }
        List<ArchiveTitle> archiveTitleList = MapperUtils.lambdaQueryWrapper(archiveTitleMapper).orderByDesc(ArchiveTitle::getId).list();
        archiveTitleList.forEach(archiveTitle -> {
            List<Archive> list = MapperUtils.lambdaQueryWrapper(archiveMapper).orderByDesc(Archive::getCreateTime).eq(Archive::getArchiveTitle, archiveTitle.getArchiveTitle()).list();
            archiveTitle.setArchiveList(list);
        });
        redisService.set(ARCHIVE_TITLE_LIST,archiveTitleList);
        return archiveTitleList;
    }

    @Override
    public void add(ArchiveTitle archiveTitle) {

    }

    @Override
    public ArchiveTitle find(Long id) {
        return null;
    }

    @Override
    public IPage<ArchiveTitle> selectListByPage(Integer current, Integer limit) {
        // Page<ArchiveTitle> archiveTitlePage = new Page<>(current, limit);
        // Page<ArchiveTitle> page = MapperUtils.lambdaQueryWrapper(archiveTitleMapper).orderByDesc(ArchiveTitle::getId).page(archiveTitlePage);
        // page.getRecords().forEach(archiveTitle -> {
        //     List<Archive> list = MapperUtils.lambdaQueryWrapper(archiveMapper).eq(Archive::getArchiveTitle, archiveTitle.getArchiveTitle()).list();
        //     archiveTitle.setArchiveList(list);
        // });
        // return page;
        return null;
    }

    @Override
    public IPage<ArchiveTitle> selectListByPage(Integer current, Integer limit, String keywords) {
        Page<ArchiveTitle> archiveTitlePage = new Page<>(current, limit);
        Page<ArchiveTitle> page = MapperUtils.lambdaQueryWrapper(archiveTitleMapper)
                .orderByDesc(ArchiveTitle::getId)
                .like(ArchiveTitle::getArchiveTitle,keywords)
                .page(archiveTitlePage);
        page.getRecords().forEach(archiveTitle -> {
            List<Archive> list = MapperUtils.lambdaQueryWrapper(archiveMapper)
                    .eq(Archive::getArchiveTitle, archiveTitle.getArchiveTitle())
                    .like(Archive::getTitle,keywords)
                    .list();
            archiveTitle.setArchiveList(list);
        });
        return page;
    }


    // public void deleteCache(Long id) {
    //     List<ArchiveTitle> list = list();
    //     list.removeIf(value -> value.getId().equals(id));
    //     redisService.set(ARCHIVE_TITLE_LIST, list);
    // }
    //
    // public void updateCache(ArchiveTitle archiveTitle) {
    //     List<ArchiveTitle> list = list();
    //     list.add(archiveTitle);
    //     redisService.set(ARCHIVE_TITLE_LIST, list);
    // }
    //
    //
    // public void addCache(ArchiveTitle archiveTitle) {
    //     List<ArchiveTitle> list = list();
    //     list.add(archiveTitle);
    //     redisService.set(ARCHIVE_TITLE_LIST, list);
    // }


}
