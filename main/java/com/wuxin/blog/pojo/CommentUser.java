package com.wuxin.blog.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


// @Data
// @AllArgsConstructor
// @NoArgsConstructor
@TableName("wx_comment_user")
public class CommentUser {

    @TableId(value = "user_id",type = IdType.ASSIGN_ID)
    private Long userId;
    private String username;
    private String email;
    private String avatar;
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    public CommentUser() {
    }


    public CommentUser(Long userId, String username, String email, String avatar, Date createTime) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.createTime = createTime;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
