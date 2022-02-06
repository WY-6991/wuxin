package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.mode.Base.PageService;
import com.wuxin.blog.pojo.blog.ArchiveTitle;
import com.wuxin.blog.mode.Base.BaseService;


/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:08
 * @Description:
 */
public interface ArchiveTitleService extends PageService<ArchiveTitle> {


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




}
