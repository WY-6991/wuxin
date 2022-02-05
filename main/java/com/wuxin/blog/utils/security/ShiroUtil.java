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
     * 根据用户生成盐
     */
    public static String createSalt(String username){
        return new Md5Hash(username).toHex();
    }

    /**
     * 加盐加密
     * @param srcPwd    原始密码
     * @param saltValue 盐值
     */
    public static String salt(Object srcPwd, String saltValue){
        return new SimpleHash("MD5", srcPwd, saltValue, 1024).toString();
    }



}
