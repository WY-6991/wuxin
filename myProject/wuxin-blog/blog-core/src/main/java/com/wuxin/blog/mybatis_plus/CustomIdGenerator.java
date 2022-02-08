package com.wuxin.blog.mybatis_plus;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.wuxin.blog.utils.id.KeyUtil;
import org.springframework.stereotype.Component;

/**
 * @Author: wuxin001
 * @Date: 2022/02/08/12:14
 * @Description:
 */
@Component
public class CustomIdGenerator implements IdentifierGenerator {
    @Override
    public Long nextId(Object entity) {
        try {
            return KeyUtil.IdUtils();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MybatisPlusException("id生成失败！");
        }
    }

}