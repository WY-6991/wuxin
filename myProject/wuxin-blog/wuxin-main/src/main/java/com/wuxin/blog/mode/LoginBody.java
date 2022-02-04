package com.wuxin.blog.mode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wuxin001
 * @Date: 2022/01/13/10:46
 * @Description: 登录用户需要输入信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginBody {
    private String username;
    private String password;
    private Integer code;
    private String uuid =  "";
}
