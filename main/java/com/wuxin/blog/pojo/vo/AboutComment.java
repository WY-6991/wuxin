package com.wuxin.blog.pojo.vo;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_about_comment")
public class AboutComment {


    @TableId(value = "about_comment_id", type = IdType.ASSIGN_ID)
    private Long commentId;
    @TableField("comment_user_id")
    private Long commentUserId;
    @TableField(value = "content")
    private String content;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String email;
    @TableField(exist = false)
    private String avatar;
    @TableField(exist = false)  // 回复评论列表
    List<AboutCommentReply> replyList = new ArrayList<>();


}
