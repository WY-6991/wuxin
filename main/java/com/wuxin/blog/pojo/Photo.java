package com.wuxin.blog.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class Photo {
    @TableId(value = "photo_id",type = IdType.ASSIGN_ID)
    private Long photoId;
    @TableField("img_url")
    private String imgUrl;
    private String alt;
    private String title;
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "create_time",fill = FieldFill.UPDATE)
    private Date updateTime;
    @TableField(exist = false)
    private String username;
}
