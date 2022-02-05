package com.wuxin.blog.pojo.blog;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuxin.blog.pojo.mode.CreateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@TableName("wx_user")
public class User extends CreateTime  implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;
    private String username;
    private String password;
    private String nickname;
    private String salt;
    private String avatar;
    private String email;
    private String phone;
    private Date updateTime;
    private String motto;
    private Integer status;
    // 是否订阅消息回复
    private boolean subscription ;
    @TableField(value = "role_id")
    private Long roleId;

    @TableField(exist = false)
    private String code;
    @TableField(exist = false)
    private String roleName;
}
