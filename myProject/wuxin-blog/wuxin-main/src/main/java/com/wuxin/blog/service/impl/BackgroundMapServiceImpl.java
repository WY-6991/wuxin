package com.wuxin.blog.service.impl;

import com.wuxin.blog.mapper.BackgroundMapMapper;
import com.wuxin.blog.pojo.system.BackgroundMap;
import com.wuxin.blog.redis.RedisKey;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.service.BackgroundMapService;
import com.wuxin.blog.utils.MapperUtils;
import com.wuxin.blog.utils.ThrowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2021/12/14/16:00
 * @Description:
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class BackgroundMapServiceImpl implements BackgroundMapService {


    @Autowired
    private BackgroundMapMapper backgroundMapMapper;


    @Autowired
    private RedisService redisService;

    @Override
    public void add(BackgroundMap backgroundMap) {
        ThrowUtils.ops(backgroundMapMapper.insert(backgroundMap), "背景图不存在！");
        // 将新添加壁纸加入到redis中
        List<BackgroundMap> list = list();
        list.add(backgroundMap);
        redisService.set(RedisKey.HOME_BACKGROUND_IMAGE, list);

    }

    @Override
    public void update(BackgroundMap backgroundMap) {
        ThrowUtils.ops(backgroundMapMapper.updateById(backgroundMap), "背景图不存在！");
        List<BackgroundMap> list = list();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(backgroundMap.getId())) {
                list.remove(i);
                list.add(backgroundMap);
                redisService.set(RedisKey.HOME_BACKGROUND_IMAGE, list);
            }
        }
    }

    @Override
    public BackgroundMap find(Long id) {
        return null;
    }

    @Override
    public List<BackgroundMap> list() {
        // 从redis中获取首页壁纸
        boolean b = redisService.hasKey(RedisKey.HOME_BACKGROUND_IMAGE);
        if (b) {
            List<BackgroundMap> list = (List<BackgroundMap>) redisService.get(RedisKey.HOME_BACKGROUND_IMAGE);
            if (list.size() != 0) {
                return list;
            }
        }
        // 从数据库中获取壁纸列表
        List<BackgroundMap> list = MapperUtils.lambdaQueryWrapper(backgroundMapMapper).list();
        redisService.set(RedisKey.HOME_BACKGROUND_IMAGE, list);
        return list;
    }

    @Override
    public void delete(Long id) {
        ThrowUtils.ops(backgroundMapMapper.deleteById(id), "背景图不存在！");
        List<BackgroundMap> list = list();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(id)) {
                list.remove(i);
                redisService.set(RedisKey.HOME_BACKGROUND_IMAGE, list);
            }
        }

    }


}
