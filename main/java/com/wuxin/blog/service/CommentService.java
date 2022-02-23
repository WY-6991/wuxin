package com.wuxin.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuxin.blog.pojo.blog.Comment;
import com.wuxin.blog.pojo.blog.CommentReply;

import java.util.List;


/**
 * @Author: wuxin001
 * @Date: 2021/10/01/9:24
 * @Description:
 */
public interface CommentService {


    /**
     * 添加评论
     * @param comment comment
     */
    void addComment(Comment comment);


    /**
     * 添加评论
     * @param commentId commentId
     */
    Comment findCommentByCommentId(Long commentId);


    /**
     * 删除评论
     * @param  commentId id
     */
    void delComment(Long commentId);

    /**
     * 删除评论
     * @param userId id
     */
    void delCommentByUserId(Long userId);

    /**
     * 根据blogId删除评论
     * @param blogId id
     */
    void delCommentByBlogId(Long blogId);

    /**
     * 修改评论
     * @param comment DTO
     */
    void updateComment(Comment comment);

    /**
     * 评论回复
     * @param blogCommentReply DTO
     */
    Long addReply(CommentReply blogCommentReply);


    /**
     * 评论删除
     * @param replyId reply
     * @return 成功消息
     */
    void delReply(Long replyId);


    /**
     * del user reply
     * @param userId id
     */
    void delReplyByUserId(Long userId);


    /**
     * 隐藏回复
     * @param comment DTO
     */
    void updateComment(CommentReply comment);











    /**
     * 根据blogID分页显示comment
     * @param current current
     * @param limit size
     * @return records
     */
    IPage<Comment> findCommentList(Integer current, Integer limit, Long blogId, Integer type);


    /**
     * 统计文章评论数量
     * @param blogId id 可以为null
     * @param type type
     * @return count
     */
    Integer findCommentCount(Long blogId,Integer type);



    /**
     * 删除评论 根据blogId
     * @param blogId id
     * @return 成功消息
     */
    void delCommentReplyByBlogId(Long blogId);

    /**
     * 删除评论 commentId
     * @param commentId id
     * @return 成功消息
     */
    void delCommentReplyByCommentId(Long commentId);

    /**
     * 后台分页显示评论
     * @param current 页码
     * @param limit 大小
     * @param keywords 搜索关键词
     * @return page
     */
    IPage<Comment> findBlogCommentByPage(Integer current, Integer limit,Integer type, String keywords,Long blogId,String start,String end);


    /**
     * 回复
     * @param current 页码
     * @param limit 大小
     * @param keywords 搜索关键词
     * @return page
     */
    IPage<CommentReply> findBlogCommentReplyPage(Integer current, Integer limit, String keywords);

    /**
     * 获取全部评论
     * @param blogId id
     * @return list
     */
    List<Comment> allBlogComment(Long blogId);


    /**
     * 今日评论统计
     * @return 评论数
     */
    Integer commentCount();


    /**
     * 删除全部评论
     */
    void delCommentAll();


    /**
     * 删除全部回复
     */
    void delReplyAll();
}
