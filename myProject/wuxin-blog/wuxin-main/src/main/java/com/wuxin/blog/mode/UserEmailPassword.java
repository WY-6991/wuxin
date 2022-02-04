package com.wuxin.blog.mode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wuxin001
 * @Date: 2022/02/01/11:45
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEmailPassword {
    private String email;
    private String code;
    private String password;
}
