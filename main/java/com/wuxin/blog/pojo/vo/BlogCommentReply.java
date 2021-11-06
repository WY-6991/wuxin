package com.wuxin.blog.pojo.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: wuxin001
 * @Date: 2021/09/01/12:59
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_blog_comment_reply")
public class BlogCommentReply {

    @TableId(value = "reply_id", type = IdType.ASSIGN_ID)
    private Long replyId;
    @TableField("comment_id") // 回复评论Id
    private Long commentId;
    @TableField("reply_content")
    private String replyContent;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField("reply_user_id")
    private Long replyUserId;
    @TableField("comment_user_id")
    private Long commentUserId;
    @TableField("blog_id")
    private Long blogId;

    @TableField(exist = false)
    private String replyUsername; //  对xx回复的评论
    @TableField(exist = false) //
    private String replyAvatar; //回复评论人头像
    @TableField(exist = false) //
    private String replyEmail; //回复评论人头像


    @TableField(exist = false)
    private String CommentUsername; // 评论用户名
    @TableField(exist = false) //
    private String commentAvatar; //评论人头像


}
