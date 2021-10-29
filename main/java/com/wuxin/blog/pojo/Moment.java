package com.wuxin.blog.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/11:16
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_moment")
public class Moment {

    @TableId(value = "moment_id", type = IdType.ASSIGN_ID)
    private Long momentId;
    @TableField("content")
    private String content;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField("likes")
    private int likes;
    @TableField("user_id")
    private Long userId;
    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String avatar;
}
