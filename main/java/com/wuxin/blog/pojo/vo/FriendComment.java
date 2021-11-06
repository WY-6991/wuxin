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
@TableName("wx_friend_comment")
public class FriendComment {


    @TableId(value = "friend_comment_id", type = IdType.ASSIGN_ID)
    private Long commentId;
    @TableField(value = "comment_user_id")
    private Long commentUserId;
    private String content;
    @TableField(fill = FieldFill.INSERT, value = "create_time")
    private Date createTime;

    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String email;
    @TableField(exist = false)
    private String avatar;
    @TableField(exist = false)
    private List<FriendCommentReply> replyList = new ArrayList<>();

}
