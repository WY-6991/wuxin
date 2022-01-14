package com.wuxin.blog.mode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wuxin001
 * @Date: 2022/01/13/10:49
 * @Description: 通过邮箱方式登录
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginEmail {
    private String email;
    private String code;
}
