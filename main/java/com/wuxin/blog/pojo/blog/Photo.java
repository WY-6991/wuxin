package com.wuxin.blog.pojo.blog;

import com.baomidou.mybatisplus.annotation.*;
import com.wuxin.blog.pojo.mode.CreateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: wuxin001
 * @Date: 2021/08/23/13:21
 * @Description: 照片类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_photo")
public class Photo extends CreateTime implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "photo_id",type = IdType.ASSIGN_ID)
    private Long photoId;
    @TableField("img_url")
    private String imgUrl;
    private String alt;
    private String title;
    private Date updateTime;
    @TableField(exist = false)
    private String username;
}
