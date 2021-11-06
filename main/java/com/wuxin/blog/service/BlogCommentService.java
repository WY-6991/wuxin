package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.vo.BlogComment;
import com.wuxin.blog.pojo.vo.BlogCommentReply;

public interface BlogCommentService {


    /**
     * 添加评论
     * @param blogComment commentDTO
     * @return int
     */
    int addComment(BlogComment blogComment);


    /**
     * 删除评论
     * @param  commentId
     * @return int
     */
    int delComment(Long commentId);

    /**
     * 删除评论
     * @param userId userID
     * @return int
     */
    int delCommentByUserId(Long userId);

    /**
     *
     * @param blogId
     * @return
     */
    int delCommentByBlogId(Long blogId);

    /**
     * 修改评论
     * @param blogComment comment
     * @return 修改评论
     */
    boolean updateComment(BlogComment blogComment);

    /**
     * 评论回复
     * @param blogCommentReply commentReply
     * @return int
     */
    Integer addReply(BlogCommentReply blogCommentReply);


    /**
     * 评论删除
     * @param replyId reply
     * @return int
     */
    Integer delReply(Long replyId);


    /**
     * del user reply
     * @param userId
     * @return
     */
    Integer delReplyByUserId(Long userId);


    /**
     * 根据blogID分页显示comment
     * @param current current
     * @param size size
     * @param blogId blogId
     * @return records
     */
    IPage<BlogComment> findBlogCommentByBlogId(Integer current, Integer size, Long blogId);


    /**
     * 统计评论数量
     * @return
     */
    Integer findCommentCount();



    /**
     * 删除评论 根据blogId
     * @param blogId
     * @return
     */
    int delCommentReplyByBlogId(Long blogId);

    /**
     * 删除评论 commentId
     * @param commentId
     * @return
     */
    int delCommentReplyByCommentId(Long commentId);

    /**
     * 后台分页显示评论
     * @param current
     * @param limit
     * @param keywords
     * @return
     */
    IPage<BlogComment> findBlogCommentByPage(Integer current, Integer limit, String keywords);


    /**
     * 回复
     * @param current
     * @param limit
     * @param keywords
     * @return
     */
    IPage<BlogCommentReply> findBlogCommentReplyPage(Integer current, Integer limit, String keywords);
}
