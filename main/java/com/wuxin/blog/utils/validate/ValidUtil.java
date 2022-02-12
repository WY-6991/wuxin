package com.wuxin.blog.utils.validate;

/**
 * @Author: wuxin001
 * @Date: 2022/02/10/19:04
 * @Description: 使用正则表达式验证工具类
 */
public class ValidUtil {

    /**
     * 正则表达式验证邮箱
     */
    public static boolean validEmail(String email) {
        if (email == null || "".equals(email)) {
            return false;
        }
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return email.matches(regex);
    }


    /**
     * 验证用户名
     */
    public static boolean validUsername(String username) {
        if (username == null || "".equals(username)) {
            return false;
        }
        return username.length() >= 2 && username.length() <= 15;
    }

    /**
     * 验证手机号
     */
    public static boolean validPhone(String phone) {
        if (phone == null || "".equals(phone)) {
            return false;
        }

        String regex = "^1[3|4|5|8][0-9]\\d{8}$";
        return phone.matches(regex);
    }

    /**
     * 验证qq号
     */
    public static boolean validQq(String qq) {
        if (qq == null || "".equals(qq)) {
            return false;
        }
        String regex = "[1-9][0-9]{4,10}";
        return qq.matches(regex);
    }

    /**
     * 验证密码长度是否合法
     */
    public static boolean validPassword(String username) {
        if (username == null || "".equals(username)) {
            return false;
        }
        return username.length() >= 4 && username.length() <= 15;
    }
}
