package com.wuxin.blog.utils.security;

import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.jwt.JWTUtil;
import com.wuxin.blog.pojo.blog.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.UUID;


/**
 * shiro工具类
 * @author Administrator
 */

public class ShiroUtil {
    /**
     * 生成32的随机盐值
     */
    public static String createSalt(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 加盐加密
     * @param srcPwd    原始密码
     * @param saltValue 盐值
     */
    public static String salt(Object srcPwd, String saltValue){
        return new SimpleHash("MD5", srcPwd, saltValue, 1024).toString();
    }

    /**
     * 获取当前用户
     */
    public static User getUser(){
        return (User) SecurityUtils.getSubject().getPrincipal();
    }


    /**
     * 获取用户标识
    //  */
    // public static User getUserUUID(String username,String password){
    //     return
    // }

    // JWTUtil

    // HashUtil



}
