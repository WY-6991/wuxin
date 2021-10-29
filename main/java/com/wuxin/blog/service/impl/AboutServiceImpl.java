package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.wuxin.blog.mapper.AboutMapper;
import com.wuxin.blog.pojo.About;
import com.wuxin.blog.service.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:08
 * @Description:
 */
@Service
public class AboutServiceImpl implements AboutService {


    @Autowired
    private AboutMapper aboutMapper;

    @Override
    public int addAbout(About about) {
        return aboutMapper.insert(about);
    }

    @Override
    public int delAbout(Long aboutId) {
        return aboutMapper.deleteById(aboutId);
    }

    @Override
    public boolean updateAbout(About about) {
        return new LambdaUpdateChainWrapper<About>(aboutMapper).eq(About::getAboutId,about.getAboutId()).update(about);
    }

    @Override
    public About findAbout(Long aboutId) {
        return aboutMapper.selectById(aboutId);
    }
}
