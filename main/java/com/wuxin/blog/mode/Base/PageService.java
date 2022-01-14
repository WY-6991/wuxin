package com.wuxin.blog.mode.Base;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @Author: wuxin001
 * @Date: 2022/01/06/20:23
 * @Description:
 */
public interface PageService<T> extends BaseService<T>{

    /**
     * 分页
     * @param current 页码
     * @param limit 大小
     * @return page
     */
    IPage<T> selectListByPage(Integer current, Integer limit);


    /**
     * 分页
     * @param current 页码
     * @param limit 大小
     * @param keywords 实现关键字查询
     * @return page
     */
    IPage<T> selectListByPage(Integer current,Integer limit,String keywords);
}
