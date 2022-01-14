package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.blog.Moment;
import com.wuxin.blog.mode.Base.PageService;

/**
 * @Author: wuxin001
 * @Date: 2021/10/03/1:35
 * @Description:
 */
public interface MomentService extends PageService<Moment> {


    /**
     * 根据时间添加分页查询功能
     * @param current 页码
     * @param limit 大小
     * @param keywords 关键词
     * @param start 开始日期
     * @param end 结束日期
     * @return page
     */
    IPage<Moment> selectListByPage(Integer current,Integer limit,String keywords,String start,String end);





}
