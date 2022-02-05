package com.wuxin.blog.mybaits_plus;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.wuxin.blog.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: wuxin001
 * @Date: 2021/09/02/20:22
 * @Description: 自定义主键Id
 */

@Slf4j
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
