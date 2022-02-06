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


    /**
     * 网站名称
     */
    private String webName;


    /**
     * 标签名称
     */
    private String commentLabelName;


    /**
     * 标签颜色
     */
    private String commentLabelColor;


    /**
     * 域名备案
     */
    private String webRecord;


    public static Site getSite(MySystem mySystem){
        Site site = new Site();
        site.setLoginUrl(mySystem.getLoginUrl());
        site.setErCode(mySystem.getErCode());
        site.setWebUrl(mySystem.getWebUrl());
        site.setCommentLabelColor(mySystem.getCommentLabelColor());
        site.setCommentLabelName(mySystem.getCommentLabelName());
        site.setWebRecord(mySystem.getWebRecord());
        site.setWebName(mySystem.getWebName());
        return site;
    }
}
