package com.wuxin.blog.utils.security;

import com.wuxin.blog.constant.HttpStatus;
import com.wuxin.blog.exception.ServiceException;
import com.wuxin.blog.pojo.blog.User;
import org.apache.shiro.SecurityUtils;


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
    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

    /**
     * 获取当前登录用户信息
     * @return 登录用户
     */
    public static User getUser() {
        try
        {
            return (User) SecurityUtils.getSubject().getPrincipal();
        }
        catch (Exception e)
        {
            throw new ServiceException("获取用户信息异常", HttpStatus.UNAUTHORIZED);
        }

    }



    public static String getUsername(){

        try
        {
            return getUser().getUsername();
        }
        catch (Exception e)
        {
            throw new ServiceException("用户名获取失败", HttpStatus.UNAUTHORIZED);
        }
    }


    public static Long getUserId(){

        try
        {
            return getUser().getUserId();
        }
        catch (Exception e)
        {
            throw new ServiceException("获取用户Id异常", HttpStatus.UNAUTHORIZED);
        }
    }

}
