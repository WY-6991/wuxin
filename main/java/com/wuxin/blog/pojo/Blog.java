package com.wuxin.blog.pojo;


import com.baomidou.mybatisplus.annotation.*;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 博客实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_blog")
public class Blog {

    @TableId(value = "blog_id", type = IdType.ASSIGN_ID)
    private Long blogId; // 自定义BlogId

    private String title; //标题

    private String content; // 内容

    private String description; //介绍

    private String published; // 是否发表了

    private String appreciation; // 是否开启赞赏功能

    @TableField(value = "comment_enabled", fill = FieldFill.INSERT)
    private String commentEnabled;
    private String top; // 是否指定
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    private Long words;

    private String secrecy; // 是否开启密码

    private String password; // 密码

    @TableField("user_id")
    private Long userId;


    @TableField("cid")
    private Long cid;

    private Integer views; //点击量


    @TableField(exist = false)
    private List<Tag> tags = new ArrayList<>();


    @TableField(exist = false)
    private List<Long> tagIds = new ArrayList<>();


    @TableField(exist = false)
    private String username;


    @TableField(exist = false)
    private Category category;

    @TableField(exist = false)
    private Integer commentNum;

}
