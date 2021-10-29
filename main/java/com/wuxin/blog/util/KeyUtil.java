package com.wuxin.blog.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 随机生成六位数 走位验证码
 */
public class KeyUtil {

    /**
     * email code
     * @return string
     */
    public static String keyUtils() {
        String str="0123456789";
        StringBuilder st=new StringBuilder(4);
        for(int i=0;i<6;i++){
            char ch=str.charAt(new Random().nextInt(str.length()));
            st.append(ch);
        }
        return st.toString().toLowerCase();
    }


    /**
     * 根据当前时间生成Id
     * @return String
     */
    public static Long IdUtils() {


        return Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
    }


    /**
     * 根据当前时间生成Id
     * @return String
     */
    public static Long PicId() {

        return Long.parseLong(new SimpleDateFormat("yyyyMM").format(new Date()));
    }
}
