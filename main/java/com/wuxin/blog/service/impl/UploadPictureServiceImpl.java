package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.UploadPictureMapper;
import com.wuxin.blog.pojo.UploadPicture;
import com.wuxin.blog.service.UploadPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UploadPictureServiceImpl implements UploadPictureService {

    @Autowired
    private UploadPictureMapper uploadPictureMapper;

    @Override
    public void addUploadPicture(UploadPicture uploadPicture) {
        uploadPictureMapper.insert(uploadPicture);
    }

    @Override
    public void deleteUploadPicture(Long id) {
        uploadPictureMapper.deleteById(id);
    }

    @Override
    public IPage<UploadPicture> findUploadPictureList(int current, int limit) {
        LambdaQueryChainWrapper<UploadPicture> chainWrapper = new LambdaQueryChainWrapper<>(uploadPictureMapper);
        Page<UploadPicture> uploadPicturePage = new Page<>(current,limit);
        return chainWrapper.orderByDesc(UploadPicture::getCreateTime).page(uploadPicturePage);
    }
}
