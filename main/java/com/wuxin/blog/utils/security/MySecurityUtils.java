package com.wuxin.blog.utils.security;

import com.wuxin.blog.constant.HttpStatus;
import com.wuxin.blog.exception.ServiceException;
import com.wuxin.blog.pojo.blog.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;


/**
 * @Author: wuxin001
 * @Date: 2022/01/03/16:47
 * @Description: 用户安全管理工具类
 */
public class MySecurityUtils {


    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 登录用户
     */
    public static User getUser() {
        try {
            User principal = (User) SecurityUtils.getSubject().getPrincipal();
            if (principal == null) {
                throw new UnauthorizedException("未登录！");
            }
            return principal;
        } catch (Exception e) {
            throw new UnauthorizedException("用户信息获取失败！");
        }

    }


    public static String getUsername() {

        try {
            return getUser().getUsername();
        } catch (Exception e) {
            throw new UnauthorizedException("用户名获取失败！");
        }
    }


    public static Long getUserId() {

        try {
            return getUser().getUserId();
        } catch (Exception e) {
            throw new UnauthorizedException("用户id获取失败！");
        }
    }

}
