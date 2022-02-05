package com.wuxin.blog.pojo.blog;

import com.baomidou.mybatisplus.annotation.*;
import com.wuxin.blog.pojo.mode.CreateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_comment")
public class Comment extends CreateTime implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 三种评论种类,分别是文章，关于我，和友情链接
     */
    public static final Integer BLOG_COMMENT = 1;
    public static final Integer ABOUT_COMMENT = 2;
    public static final Integer FRIEND_COMMENT = 3;

    @TableId(value = "comment_id", type = IdType.AUTO)
    private Long commentId;

    /**
     * 文章id
     */
    @TableField(value = "blog_id")
    private Long blogId;

    /**
     * 评论用户
     */
    @TableField(value = "comment_user_id")
    private Long commentUserId;

    /**
     * 内容
     */
    private String content;


    /**
     * 状态
     */
    private boolean status;

    /**
     * 评论类型
     */
    private Integer type;


    /**
     * 用户名
     */
    @TableField(exist = false)
    private String username;

    /**
     * 头像
     */
    @TableField(exist = false)
    private String avatar;


    /**
     * 是否订阅回复
     */
    @TableField(exist = false)
    private boolean subscription ;

    /**
     * 邮箱
     */
    @TableField(exist = false)
    private String email;


    /**
     * 文章标题
     */
    @TableField(exist = false)
    private String title;


    /**
     * 回复list
     */
    @TableField(exist = false)
    List<CommentReply> replyList = new ArrayList<>();
}
