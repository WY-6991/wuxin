package com.wuxin.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuxin.blog.mapper.AboutCommentMapper;
import com.wuxin.blog.pojo.AboutComment;
import com.wuxin.blog.service.AboutCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AboutCommentServiceImpl implements AboutCommentService {


    @Autowired
    private AboutCommentMapper aboutCommentMapper;

    @Override
    public int addAboutComment(AboutComment aboutComment) {
        return aboutCommentMapper.insert(aboutComment);
    }

    @Override
    public int updateAboutComment(AboutComment aboutComment) {
       return aboutCommentMapper.updateById(aboutComment);
    }

    @Override
    public int delAboutComment(Long id) {
        return aboutCommentMapper.deleteById(id);
    }

    @Override
    public IPage<AboutComment> findAboutComment(int current, int limit) {
        LambdaQueryChainWrapper<AboutComment> aboutCommentLambdaQueryChainWrapper = new LambdaQueryChainWrapper<>(aboutCommentMapper);
        Page<AboutComment> page = new Page<>(current,limit);
        return aboutCommentLambdaQueryChainWrapper.orderByDesc(AboutComment::getCreateTime).page(page);
    }
}
