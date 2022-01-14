package com.wuxin.blog.mode.Base;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2022/01/06/20:14
 * @Description:
 */
public interface BaseService<T>{

    /**
     * 添加
     * @param t DTO
     */
    void add(T t);

    /**
     * 修改
     * @param t DTO
     */
    void update(T t);



    /**
     * 查找
     * @param id 根据Id查找
     * @return T
     */
    T find(Long id);



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





}
