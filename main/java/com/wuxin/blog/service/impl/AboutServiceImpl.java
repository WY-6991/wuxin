package com.wuxin.blog.service.impl;

import com.wuxin.blog.mapper.AboutMapper;
import com.wuxin.blog.pojo.blog.About;
import com.wuxin.blog.redis.RedisKey;
import com.wuxin.blog.redis.RedisService;
import com.wuxin.blog.service.AboutService;
import com.wuxin.blog.utils.ThrowUtils;
import com.wuxin.blog.utils.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:08
 * @Description:
 */
@Transactional(rollbackFor = {Exception.class})
@Service
public class AboutServiceImpl implements AboutService {

    @Autowired
    private AboutMapper aboutMapper;

    @Autowired
    private RedisService redisService;


    @Override
    public void update(About about) {

        String redisKey = RedisKey.ABOUT;
        about.setAboutId(About.ABOUT_ID);
        About ab = find(About.ABOUT_ID);
        if (StringUtils.isNull(ab)) {
            aboutMapper.insert(about);
        } else {
            ThrowUtils.ops(aboutMapper.updateById(about), "修改失败！");
            System.out.println("=================mysql about=======================");
        }
        // 修改内容设置到redis中
        redisService.set(redisKey,about);

    }


    @Override
    public About find(Long id) {
        String redisKey = RedisKey.ABOUT;
        boolean b = redisService.hasKey(redisKey);
        if(b){
            About o = (About) redisService.get(redisKey);
            if(StringUtils.isNotNull(o)){
                return o;
            }
        }
        About about = aboutMapper.selectById(id);
        // 将about内容存入redis中
        redisService.set(redisKey,about);
        ThrowUtils.isNull(about, "关于内容不存在！");
        return about;
    }

}
