package com.wuxin.blog.mode;

import com.wuxin.blog.pojo.blog.User;
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
public class UserRegister {
    private String username;
    private String password;
    private String email;
    private String code;


    public static User getUser(UserRegister userRegister){
        User user = new User();
        user.setUsername(userRegister.getUsername());
        user.setPassword(userRegister.getPassword());
        user.setEmail(userRegister.getEmail());
        return user;
    }
}
