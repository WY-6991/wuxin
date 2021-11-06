package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.vo.AboutComment;
import com.wuxin.blog.pojo.vo.AboutCommentReply;

public interface AboutCommentService {

    /**
     * 添加评论
     * @param aboutComment
     * @return
     */
    int addAboutComment(AboutComment aboutComment);

    /**
     * 修改评论
     * @param aboutComment
     * @return
     */
    int updateAboutComment(AboutComment aboutComment);

    /**
     * 撒谎评论
     * @param id
     * @return
     */
    int delAboutComment(Long id);


    /**
     * 评论查询
     * @param current
     * @param limit
     * @return
     */
    IPage<AboutComment> findAboutComment(int current,int limit);


    /*-------------------------------reply------------------------------------*/


    /**
     * 添加评论
     * @param reply
     * @return
     */
    int addAboutCommentReply(AboutCommentReply reply);

    /**
     * 修改评论
     * @param reply
     * @return
     */
    int updateAboutCommentReply(AboutCommentReply reply);

    /**
     * 撒谎评论
     * @param id
     * @return
     */
    int delAboutCommentReply(Long id);

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    int delAboutCommentReplyByCommentId(Long commentId);
}
