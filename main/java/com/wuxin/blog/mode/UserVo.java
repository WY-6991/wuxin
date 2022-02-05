package com.wuxin.blog.mode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: wuxin001
 * @Date: 2022/01/23/11:57
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String token;
    private Long expireTime;
    private String ip;
    private String loginLocation;
    private String os;
    private String browser;
    private Long loginTime;

}
