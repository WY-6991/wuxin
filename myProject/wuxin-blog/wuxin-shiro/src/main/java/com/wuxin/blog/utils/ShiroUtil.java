package com.wuxin.blog.utils;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;


/**
 * shiro工具类
 * @author Administrator
 */

public class ShiroUtil {
    /**
     * 根据用户名生成盐
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
