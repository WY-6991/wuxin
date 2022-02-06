package com.wuxin.blog.pojo.log;

import com.baomidou.mybatisplus.annotation.*;
import com.wuxin.blog.mode.Log;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: wuxin001
 * @Date: 2021/10/02/8:40
 * @Description: 登录日志实体
 *
 * 该字段信息不会存入数据库中,使用redis数据作为缓存
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_login_log")
public class LoginLog extends Log implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 登录用户名
     */
    private String username;
}
