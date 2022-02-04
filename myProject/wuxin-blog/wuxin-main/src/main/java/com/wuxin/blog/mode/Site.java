package com.wuxin.blog.mode;


import com.wuxin.blog.pojo.system.MySystem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wuxin001
 * @Date: 2022/01/18/17:21
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Site {

    /**
     * 登录地址
     */
    private String loginUrl;

    /**
     * 网站地址
     */
    private String webUrl;

    /**
     * 二维码图片
     */
    private String erCode;


    public static Site getSite(MySystem mySystem){
        Site site = new Site();
        site.setLoginUrl(mySystem.getLoginUrl());
        site.setErCode(null);
        site.setWebUrl(mySystem.getWebUrl());
        return site;
    }
}
