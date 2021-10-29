package com.wuxin.blog.pojo;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// 上传图片管理
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_upload_picture")
public class UploadPicture {

    @TableId(value = "picture_id",type = IdType.ASSIGN_ID)
    private Long id;
    private String url;
    private String name;
    private String message;
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

}
