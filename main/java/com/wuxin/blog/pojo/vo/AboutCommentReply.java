package com.wuxin.blog.pojo.vo;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_about_comment_reply")
public class AboutCommentReply {


    @TableId(value = "about_comment_reply_id",type = IdType.ASSIGN_ID)
    private Long replyId;
    @TableField(value = "reply_content")
    private String replyContent;
    @TableField(value = "comment_user_id")
    private Long commentUserId;
    @TableField(value = "about_comment_id")
    private Long commentId;
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "reply_user_id")
    private Long replyUserId;

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
