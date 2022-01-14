package com.wuxin.blog.mode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: wuxin001
 * @Date: 2022/01/06/17:14
 * @Description: 用户账号信息修改
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String nickname;
    private String password;
    private String newPassword;
    private String email;

}
