package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.AboutComment;

public interface AboutCommentService {

    int addAboutComment(AboutComment aboutComment);
    int updateAboutComment(AboutComment aboutComment);
    int delAboutComment(Long id);
    IPage<AboutComment> findAboutComment(int current,int limit);

}
