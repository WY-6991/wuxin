package com.wuxin.blog.pojo;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@TableName("wx_user")
public class User {

    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;
    private String username;
    private String password;
    private String nickname;
    private String salt;
    private String avatar;
    private String email;
    private String phone;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;
    private String motto;
    private Integer status;
    @TableField(value = "role_id")
    private Long roleId;

    @TableField(exist = false)
    private String code;
    @TableField(exist = false)
    private String roleName;
}
