package com.wuxin.blog.service;

import com.wuxin.blog.pojo.blog.About;
import com.wuxin.blog.mode.Base.BaseService;


/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:08
 * @Description:
 */
public interface AboutService {

    void update(About about);

    About find(Long id);


}
