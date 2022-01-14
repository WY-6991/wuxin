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
    @TableField(value = "blog_id")
    private Long blogId;
    @TableField(value = "comment_user_id")
    private Long commentUserId;
    private String content;


    private boolean status;

    private Integer type;

    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String avatar;
    @TableField(exist = false)
    private String adminComment;
    @TableField(exist = false)
    private String email;


    @TableField(exist = false)
    List<CommentReply> replyList = new ArrayList<>();
}
