package com.wuxin.blog.utils.time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: wuxin001
 * @Date: 2022/01/12/22:02
 * @Description:
 */
public class DateUtils {


    /**
     * 获取当前时间 2021-12-20 12:12:33
     */
    public static String localTime() {
        SimpleDateFormat dateFormat = getSimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(getDate());
    }


    /**
     * 获取当天凌晨时间 开始时间 比如 2021-12-20 12:00:00
     */
    public static String todayStartTime() {
        SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd");
        return sdf.format(getDate()) + " 00:00:00";

    }


    /**
     * 获取简化日期 比如 11-22
     */
    public static String simpleDate() {
        SimpleDateFormat dateFormat = getSimpleDateFormat("MM-dd");
        return dateFormat.format(getDate());
    }


    /**
     * 获取当前时间戳
     */
    public static Date getDate() {
        return new Date();
    }


    /**
     * 获取时间格式
     */
    public static SimpleDateFormat getSimpleDateFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }


}



