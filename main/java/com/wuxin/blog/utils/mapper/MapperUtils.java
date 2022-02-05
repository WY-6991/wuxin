package com.wuxin.blog.utils.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.pojo.mode.CreateTime;

/**
 * mapper-plus 查询工具类和修改工具类
 *
 * @Author: wuxin001
 * @Date: 2022/01/06/20:13
 * @Description:
 */
public class MapperUtils {


    public static <T> LambdaQueryChainWrapper<T> lambdaQueryWrapper(BaseMapper<T> baseMapper) {
        return new LambdaQueryChainWrapper<>(baseMapper);
    }

    public static <T> LambdaUpdateChainWrapper<T> lambdaUpdateChainWrapper(BaseMapper<T> baseMapper) {
        return new LambdaUpdateChainWrapper<>(baseMapper);
    }

    public static <T extends CreateTime> IPage<T> lambdaQueryWrapper(BaseMapper<T> baseMapper, Integer current, Integer limit) {
        return new LambdaQueryChainWrapper<T>(baseMapper).orderByDesc(CreateTime::getCreateTime).page(new Page<T>(current, limit));
    }


}
