package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.exception.CustomException;
import com.wuxin.blog.mapper.ArchiveMapper;
import com.wuxin.blog.pojo.blog.Archive;
import com.wuxin.blog.pojo.blog.ArchiveTitle;
import com.wuxin.blog.pojo.blog.Blog;
import com.wuxin.blog.pojo.blog.Category;
import com.wuxin.blog.redis.RedisKey;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.service.ArchiveService;
import com.wuxin.blog.service.ArchiveTitleService;
import com.wuxin.blog.utils.ThrowUtils;
import com.wuxin.blog.utils.mapper.MapperUtils;
import com.wuxin.blog.utils.string.StringUtils;
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


    private static final String ARCHIVE_TITLE_LIST = RedisKey.ARCHIVE_TITLE_LIST;


    private static final String ARCHIVE_LIST = RedisKey.ARCHIVE_LIST;

    @Autowired
    private ArchiveMapper archiveMapper;

    @Autowired
    private RedisService redisService;


    @Autowired
    private ArchiveTitleService archiveTitleService;


    public static final Integer TYPE_1 = 1;

    public static final Integer TYPE_2 = 2;


    @Override
    public void add(Archive archive) {
        archiveMapper.insert(archive);
        // addCache(archive);
        redisService.del(ARCHIVE_TITLE_LIST);
    }

    @Override
    public void update(Archive archive) {
        LambdaUpdateChainWrapper<Archive> chainWrapper = new LambdaUpdateChainWrapper<>(archiveMapper);
        boolean update = chainWrapper.eq(Archive::getArchiveId, archive.getArchiveId()).update(archive);
        if (!update) {
            throw new CustomException("归档~修改失败！");
        }
        // updateCache(archive);
        redisService.del(ARCHIVE_TITLE_LIST);
    }

    @Override
    public void delete(Long archiveId) {
        ThrowUtils.ops(archiveMapper.deleteById(archiveId), "删除失败！");
        redisService.del(ARCHIVE_TITLE_LIST);
    }

    @Override
    public void delArchiveByBlogId(Long blogId) {
        LambdaQueryWrapper<Archive> queryChainWrapper = new LambdaQueryWrapper<>();
        queryChainWrapper.eq(Archive::getBlogId, blogId);
        ThrowUtils.ops(archiveMapper.delete(queryChainWrapper), "删除失败！");
        redisService.del(ARCHIVE_TITLE_LIST);
    }

    @Override
    public IPage<Archive> selectListByPage(Integer current, Integer limit) {
        LambdaQueryChainWrapper<Archive> queryChainWrapper = new LambdaQueryChainWrapper<>(archiveMapper);
        return queryChainWrapper.orderByDesc(Archive::getCreateTime).page(new Page<Archive>(current, limit));
    }

    @Override
    public IPage<Archive> selectListByPage(Integer current, Integer limit,String keywords,String start,String end) {
        return MapperUtils.lambdaQueryWrapper(archiveMapper).orderByDesc(Archive::getCreateTime)
                .like(StringUtils.isNotNull(keywords), Archive::getTitle, keywords)
                // 大于开始时间
                .ge(StringUtils.isNotNull(start), Archive::getCreateTime, start)
                // 小于结束时间
                .le(StringUtils.isNotNull(end), Archive::getCreateTime, end)
                .page(new Page<>(current, limit));
    }

    @Override
    public List<Archive> findArchiveList() {
        // return new LambdaQueryChainWrapper<Archive>(archiveMapper).list();
        return null;
    }

    @Override
    public Archive findArchiveByBlogId(Long blogId) {
        return MapperUtils.lambdaQueryWrapper(archiveMapper).eq(Archive::getBlogId, blogId).one();
    }

    @Override
    public Archive findArchive(Archive archive) {
        if (archive.getType().equals(TYPE_1) && StringUtils.isNotNull(archive.getBlogId())) {
            return findArchiveByBlogId(archive.getBlogId());
        }
        // 如果是转载链接地址和文章url不能同时一致
        if (archive.getType().equals(TYPE_2)) {
            return MapperUtils.lambdaQueryWrapper(archiveMapper).eq(Archive::getTitle, archive.getTitle()).eq(Archive::getUrl, archive.getUrl()).one();
        }
        return null;
    }



    @Override
    public Archive find(Long id) {
        Archive archive = MapperUtils.lambdaQueryWrapper(archiveMapper).eq(Archive::getBlogId, id).one();
        ThrowUtils.isNull(archive, "归档内容不存在！");
        return archive;
    }

    @Override
    public List<Archive> list() {
        return MapperUtils.lambdaQueryWrapper(archiveMapper).orderByDesc(Archive::getCreateTime).list();
    }

    @Override
    public IPage<Archive> selectListByPage(Integer current, Integer limit, String keywords) {
        return MapperUtils.lambdaQueryWrapper(archiveMapper).orderByDesc(Archive::getCreateTime).page(new Page<>(current,limit));
    }

    @Override
    public Integer selectCount() {
        return archiveMapper.selectCount(null);
    }


    // public void addCache(Archive archive) {
    //     // 获取一级标题
    //     List<ArchiveTitle> list = archiveTitleService.list();
    //
    //     list.forEach(archiveTitle -> {
    //         archiveTitle.getArchiveList().forEach(value -> {
    //             if (value.getArchiveTitle().equals(archive.getArchiveTitle())) {
    //                 archiveTitle.getArchiveList().add(archive);
    //             }
    //         });
    //     });
    //
    //
    //     redisService.set(ARCHIVE_TITLE_LIST, list);
    //
    // }
    //
    // public void updateCache(Archive archive) {
    //     List<ArchiveTitle> list = archiveTitleService.list();
    //     list.forEach(archiveTitle -> {
    //         archiveTitle.getArchiveList().forEach(value -> {
    //             if (value.getArchiveTitle().equals(archive.getArchiveTitle())) {
    //                 value.setTitle(archive.getTitle());
    //                 value.setUrl(archive.getUrl());
    //                 value.setType(archive.getType());
    //                 value.setBlogId(archive.getBlogId());
    //             }
    //         });
    //     });
    //     redisService.set(ARCHIVE_TITLE_LIST, list);
    // }
    //
    // public void deleteCache(Long id) {
    //     List<ArchiveTitle> list = archiveTitleService.list();
    //     // 遍历一级标题
    //     list.forEach(archiveTitle -> {
    //         archiveTitle.getArchiveList().removeIf(value -> value.getArchiveId().equals(id));
    //     });
    //
    //     redisService.set(ARCHIVE_TITLE_LIST, list);
    // }


}
