package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.UploadPictureMapper;
import com.wuxin.blog.pojo.blog.Comment;
import com.wuxin.blog.pojo.blog.UploadPicture;
import com.wuxin.blog.service.UploadPictureService;
import com.wuxin.blog.utils.ThrowUtils;
import com.wuxin.blog.utils.mapper.MapperUtils;
import com.wuxin.blog.utils.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:08
 * @Description:
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class UploadPictureServiceImpl implements UploadPictureService {

    @Autowired
    private UploadPictureMapper uploadPictureMapper;



    @Override
    public void add(UploadPicture uploadPicture) {
        ThrowUtils.ops(uploadPictureMapper.insert(uploadPicture),"图片记录添加失败");
    }


    @Override
    public void delete(Long id) {
        ThrowUtils.ops(uploadPictureMapper.deleteById(id),"仓库记录不存在！");
    }

    @Override
    public IPage<UploadPicture> selectListByPage(Integer current, Integer limit,String start,String end) {
        return MapperUtils.lambdaQueryWrapper(uploadPictureMapper)
                .orderByDesc(UploadPicture::getCreateTime)
                .le(StringUtils.isNotEmpty(end), UploadPicture::getCreateTime, end)
                .ge(StringUtils.isNotEmpty(start), UploadPicture::getCreateTime, start)
                .page(new Page<>(current,limit));
    }

}
