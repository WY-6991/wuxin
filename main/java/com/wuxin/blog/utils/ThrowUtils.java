package com.wuxin.blog.utils;

import com.wuxin.blog.exception.CustomException;
import com.wuxin.blog.pojo.blog.User;
import com.wuxin.blog.utils.string.StringUtils;

/**
 * @Author: wuxin001
 * @Date: 2022/01/04/0:21
 * @Description:
 */
public class ThrowUtils {

    public static final CustomException CUSTOM_EXCEPTION = new CustomException("操作失败,内容可能不存在~");

    public static void isNull(Object o, String message) {
        if (StringUtils.isNull(o)) {
            throw new CustomException(message);
        }
    }

    public static void isNull(Object o) {
        if (StringUtils.isNull(o)) {
            throw CUSTOM_EXCEPTION;
        }
    }

    public static void userNull(User user) {
        if (StringUtils.isNull(user)) {
            throw new CustomException("用户不存在");
        }
        if (StringUtils.isNull(user.getNickname())) {
            throw new CustomException("用户名不存在!");
        }
    }


    public static void ops(Integer i, String message) {
        if (i != 1) {
            throw new CustomException(message);
        }
    }


    public static void ops(Integer i) {

        if (i != 1) {
            throw CUSTOM_EXCEPTION;
        }
    }

}
