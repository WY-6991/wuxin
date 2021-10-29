package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.UploadPicture;

public interface UploadPictureService {


    void addUploadPicture(UploadPicture uploadPicture);
    void deleteUploadPicture(Long id);
    IPage<UploadPicture> findUploadPictureList(int current,int limit);
}
