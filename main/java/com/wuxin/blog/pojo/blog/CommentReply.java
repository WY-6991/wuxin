package com.wuxin.blog.pojo.blog;

import com.baomidou.mybatisplus.annotation.*;
import com.wuxin.blog.pojo.mode.CreateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: wuxin001
 * @Date: 2021/09/01/12:59
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_comment_reply")
public class CommentReply extends CreateTime implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "reply_id", type = IdType.ASSIGN_ID)
    private Long replyId;

    /**
     * 评论id
     */
    @TableField("comment_id")
    private Long commentId;

    /**
     * 内容
     */
    @TableField("reply_content")
    private String replyContent;



    /**
     * 回复用户
     */
    @TableField("reply_user_id")
    private Long replyUserId;

    /**
     * 被回复用户
     */
    @TableField("comment_user_id")
    private Long commentUserId;

    /**
     * 文章id
     */
    @TableField("blog_id")
    private Long blogId;

    /**
     * 评论类型
     */
    private Integer type;

    /**
     * 状态
     */
    private boolean status;

    /**
     * 评论用户
     */
    @TableField(exist = false)
    private String replyUsername;

    /**
     * 评论用户是否 订阅了消息
     */
    @TableField(exist = false)
    private boolean subscription ;

    /**
     * 头像
     */
    @TableField(exist = false)
    private String replyAvatar;

    /**
     * 评论用户的邮箱
     */
    @TableField(exist = false)
    private String replyEmail;

    /**
     * 被评论的用户
     */
    @TableField(exist = false)
    private String commentUsername;

    /**
     * 被评论用户的邮箱
     */
    @TableField(exist = false)
    private String commentAvatar;

    /**
     * 被回复的用户是否订阅了消息
     */
    @TableField(exist = false)
    private boolean commentAvatarSubscription;


}
