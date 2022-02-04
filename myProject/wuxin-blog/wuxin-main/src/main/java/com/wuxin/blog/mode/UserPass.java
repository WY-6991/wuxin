package com.wuxin.blog.mode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wuxin001
 * @Date: 2022/02/01/12:11
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPass {
    private String username;
    private String oldPassword;
    private String newPassword;
}
