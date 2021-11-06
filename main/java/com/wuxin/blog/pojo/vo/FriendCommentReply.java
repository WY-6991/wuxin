package com.wuxin.blog.pojo.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_friend_comment_reply")
public class FriendCommentReply {

    @TableId(value = "comment_reply_id",type = IdType.ASSIGN_ID)
    private Long replyId;
    @TableField(value = "reply_user_id")
    private Long replyUserId; // 评论回复用户
    @TableField(value = "reply_content")
    private String replyContent;
    @TableField(value = "comment_user_id")
    private Long commentUserId;  // @user
    @TableField(value = "comment_id")
    private Long commentId; // 评论id
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;
    private Integer status; // 状态是否读取


    @TableField(exist = false)
    private String replyUsername;
    @TableField(exist = false)
    private String replyEmail;
    @TableField(exist = false)
    private String replyAvatar;

    @TableField(exist = false)
    private String commentUsername;
    @TableField(exist = false)
    private String commentAvatar;
}
