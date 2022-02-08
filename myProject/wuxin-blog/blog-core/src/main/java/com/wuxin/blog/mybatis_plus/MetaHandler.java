package com.wuxin.blog.mybatis_plus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: wuxin001
 * @Date: 2022/02/08/12:15
 * @Description:
 */
@Component
public class MetaHandler implements MetaObjectHandler {

    /**
     * 添加字段
     * @param metaObject metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("create_time", new Date(), metaObject);
    }


    /**
     * 修改字段
     * @param metaObject metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("update_time", new Date(), metaObject);
    }
}
