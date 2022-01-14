package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.blog.UploadPicture;


/**
 * @Author: wuxin001
 * @Date: 2021/10/01/9:24
 * @Description:
 */
public interface UploadPictureService  {

    /**
     * 添加一条记录
     * @param uploadPicture DTO
     */
    void add(UploadPicture uploadPicture);

    /**
     * 删除
     * @param id id
     */
    void delete(Long id);


    /**
     * 分页记录
     * @param current 页码
     * @param limit 大小
     * @return page
     */
    IPage<UploadPicture> selectListByPage(Integer current, Integer limit);

}
