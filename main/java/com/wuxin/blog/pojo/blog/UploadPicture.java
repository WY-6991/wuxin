package com.wuxin.blog.pojo.blog;


import com.baomidou.mybatisplus.annotation.*;
import com.wuxin.blog.pojo.mode.CreateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Administrator
 */ // 上传图片管理
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_upload_picture")
public class UploadPicture extends CreateTime implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 自定义生成id
     */
    @TableId(value = "picture_id",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 加速之后的访问地址
     */
    private String url;

    /**
     * 文件名
     */
    private String name;

    /**
     * 消息
     */
    private String message;

    /**
     * 图片sha 用于删除
     */
    private String sha;

    /**
     * 图片真实地址用于删除
     */
    private String realUrl;


    /**
     * 图片真实地址用于删除
     */
    private Integer size;

    /**
     * gitUrl
     */
    private String gitUrl;


    /**
     * 文件上传记录
     */
    private Object records;



}
