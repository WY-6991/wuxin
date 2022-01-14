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
     * 获取当前时间
     * @return localtime
     */
    public static String localTime(){
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }


    /**
     * 获取当天凌晨时间
     * @return localtime
     */
    public static String todayStartTime(){
        Date now = new Date(); //获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(now)+" 00:00:00";

    }



}



