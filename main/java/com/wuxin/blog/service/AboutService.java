package com.wuxin.blog.service;

import com.wuxin.blog.pojo.About;
import com.wuxin.blog.pojo.Category;

public interface AboutService {

    /**
     * 添加关于我的内容
     * @param about
     * @return
     */
    int addAbout(About about);

    /**
     * 删除
     * @param aboutId
     * @return
     */
    int delAbout(Long aboutId);

    /**
     * 修改
     * @param about
     * @return
     */
    boolean updateAbout(About about);


    /**
     * 显示我的内容
     * @param aboutId
     * @return
     */
    About findAbout(Long aboutId);
}
