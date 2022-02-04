package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.blog.ArchiveTitle;
import com.wuxin.blog.base.BaseService;


/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:08
 * @Description:
 */
public interface ArchiveTitleService extends BaseService<ArchiveTitle> {


     /**
      *
      * @param title
      * @return
      */
     ArchiveTitle selectArchiveTitle(String title);

     /**
      *
      * @param title
      */
     void add(String title);


     /**
      *
      * @param current
      * @param limit
      * @return
      */
     IPage<ArchiveTitle> selectListByPage(int current,int limit);

}
