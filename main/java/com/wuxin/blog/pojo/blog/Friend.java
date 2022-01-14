package com.wuxin.blog.pojo.blog;

import com.baomidou.mybatisplus.annotation.*;
import com.wuxin.blog.pojo.mode.CreateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/10:08
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_friend")
public class Friend extends CreateTime implements Serializable {



    private static final long serialVersionUID = 1L;

    @TableId(value = "friend_id",type = IdType.AUTO)
    private Long friendId;
    private String url;
    private String username;
    private String avatar;
    private boolean status;
    private String introduction;

}
