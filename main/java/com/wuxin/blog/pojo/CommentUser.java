package com.wuxin.blog.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_comment_user")
public class CommentUser {

    @TableId(value = "user_id",type = IdType.ASSIGN_ID)
    private Long userId;
    private String username;
    private String email;
    private String avatar;
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;
}
