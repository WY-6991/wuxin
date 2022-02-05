package com.wuxin.blog.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// key工具类
public class KeyUtil {

    private static List<String> AVATAR_URL_LIST =
            Stream.of
                    (
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126200425.png",
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126200435.png",
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126200446.png",
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126200501.png",
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126200510.png",
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126200519.png",
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126200542.png",
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126200553.png",
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126200612.png",
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126200659.png",
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126200730.png",
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126200814.png",
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126200855.png",
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126200952.png",
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126201012.png",
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126201057.png",
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126201139.png",
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126201247.png",
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126201300.png",
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126201410.png",
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126201439.png",
                            "https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211126201226.png"

                    )
                    .collect(Collectors.toList());

    /**
     随机返回一个用户头像
     */
    public static String randomUrl() {
        Random random = new Random();
        int num = random.nextInt(AVATAR_URL_LIST.size());
        return AVATAR_URL_LIST.get(num);
    }

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
    public static Long picId() {

        return Long.parseLong(new SimpleDateFormat("yyyyMM").format(new Date()));
    }


    /**
     * 生产archiveId
     * @return
     */
    public static  String  getArchiveTitle() {
        return new SimpleDateFormat("yyyy-MM").format(new Date());
    }


    /**
     * 获取随机UUID
     */
    public static String fastUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     */
    public static String simpleUUID()
    {
       return UUID.randomUUID().toString().replace("-","");
    }



}
