package com.wuxin.blog.mode.Base;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2022/01/06/23:08
 * @Description:
 */
public interface LogService<T> {


    /**
     * 添加
     * @param t DTO
     */
    void add(T t);




    /**
     * 查找 全部
     * @return list
     */
    List<T> list();

    /**
     * 删除
     * @param id 根据主键删除
     * @return T
     */
    void delete(Long id);


    /**
     * 全部删除
     */
    void deleteAll();


    /**
     * 批量删除
     */
    void batchDelete();



    /**
     * 分页
     * @param current 页码
     * @param limit 大小
     * @param keywords 实现关键字查询
     * @return page
     */
    IPage<T> selectListByPage(Integer current, Integer limit, String keywords, String start, String end);



}
