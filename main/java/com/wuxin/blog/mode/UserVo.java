package com.wuxin.blog.mode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: wuxin001
 * @Date: 2022/01/23/11:57
 * @Description: 登录用户角色信息
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    private Long userId;


    /**
     * 登录用户名
     */
    private String username;

    /**
     * 用户token
     */
    private String token;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录时间
     */
    private Long loginTime;

}
