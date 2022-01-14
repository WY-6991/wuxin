package com.wuxin.blog.pojo.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: wuxin001
 * @Date: 2021/12/21/11:12
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_my_system")
public class MySystem implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Integer SYSTEM_ID = 1;

    @TableId(type = IdType.AUTO)
    private Integer id;



    /**
     * 上传图片地址
     */
    @TableField(value = "admin_background_image")
    private String adminBackgroundImage;


    /**
     * 后台登录图标
     */
    @TableField(value = "admin_icon")
    private String adminIcon;


    /**
     * 登录地址
     */
    @TableField(value = "login_url")
    private String loginUrl;


    /**
     * 网站地址
     */
    @TableField(value = "web_url")
    private String webUrl;



    /**
     * 上传头像地址
     */
    @TableField("upload_avatar_url")
    private String uploadAvatarUrl;


    /**
     * 上传图片地址
     */
    @TableField("upload_image_url")
    private String uploadImageUrl;



}
