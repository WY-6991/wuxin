package com.wuxin.blog.utils;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;

/**
 * @Author: wuxin001
 * @Date: 2022/02/04/19:18
 * @Description:
 */
public class MapperUtils {

    public static <T> LambdaQueryChainWrapper<T> lambdaQueryWrapper(BaseMapper<T> baseMapper) {
        return new LambdaQueryChainWrapper<>(baseMapper);
    }

    public static <T> LambdaUpdateChainWrapper<T> lambdaUpdateChainWrapper(BaseMapper<T> baseMapper) {
        return new LambdaUpdateChainWrapper<>(baseMapper);
    }

    // public static <T extends CreateTime> IPage<T> lambdaQueryWrapper(BaseMapper<T> baseMapper, Integer current, Integer limit) {
    //     return new LambdaQueryChainWrapper<T>(baseMapper).orderByDesc(CreateTime::getCreateTime).page(new Page<T>(current, limit));
    // }
}
