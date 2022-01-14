package com.wuxin.blog.pojo.blog;


import com.baomidou.mybatisplus.annotation.*;
import com.wuxin.blog.pojo.mode.ChangeTime;
import com.wuxin.blog.pojo.mode.CreateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 博客实体类
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_blog")
public class Blog extends ChangeTime implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "blog_id", type = IdType.ASSIGN_ID)
    private Long blogId;

    /**
     * 文章描述
     */
    private String description;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;


    /**
     * 是否开启赞赏功能
     */
    private boolean appreciation;


    /**
     * 是否开启评论
     */
    // @TableField(value = "comment_enabled", fill = FieldFill.INSERT)
    private boolean commentEnabled;

    /**
     * 是否置顶
     */
    private int top;

    /**
     * 文章字数
     */
    private Long words;

    /**
     * 是否发布
     */
    private boolean publish;

    /**
     * 状态
     */
    private boolean status;


    /**
     * 是否公开
     */
    private boolean secrecy;


    /**
     * 不公开的文章需要设置密码
     */
    private String password;


    /**
     * 文章作者
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 文章分类
     */
    @TableField("cid")
    private Long cid;

    /**
     * 文章点击量
     */
    private Integer views;


    /**
     * 文章标签
     */
    @TableField(exist = false)
    private List<Tag> tags = new ArrayList<>();

    /**
     * 文章标签Id
     */
    @TableField(exist = false)
    private List<Long> tagIds = new ArrayList<>();

    /**
     * 用户名
     */
    @TableField(exist = false)
    private String username;

    /**
     * 分类名
     */
    @TableField(exist = false)
    private Category category;


    /**
     * 评论数量
     */
    @TableField(exist = false)
    private Integer commentNum;


}
