package com.wuxin.blog.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.wuxin.blog.pojo.vo.CommentReply;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_comment")
public class Comment {

    @TableId(value = "comment_id", type = IdType.ASSIGN_ID) // 自定义commentId
    private Long commentId; // 自定义commentId
    @TableField(value = "blog_id")
    private Long blogId;// 博客Id
    @TableField(value = "comment_user_id")
    private Long commentUserId;// 评论用户Id
    private String content; // 内容
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;


    @TableField(exist = false)
    private String username; // 用户昵称
    @TableField(exist = false)
    private String avatar; // 用户头像
    @TableField(exist = false)
    private String adminComment;// 博主评论
    @TableField(exist = false)
    private String email; // 邮箱


    @TableField(exist = false)  // 回复评论列表
    List<CommentReply> commentReplyList = new ArrayList<>();
}
