package com.wuxin.blog.mybaits_plus;

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
        //可以将当前传入的class全类名来作为bizKey,或者提取参数来生成bizKey进行分布式Id调用生成.
        // String bizKey = entity.getClass().getName();
        //根据bizKey调用分布式ID生成
        //返回生成的id值即可.
        try {
            return KeyUtil.IdUtils();
        } catch (Exception e) {
            e.printStackTrace();
            return KeyUtil.IdUtils()+10;
        }
    }

}
