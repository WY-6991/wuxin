package com.wuxin.blog.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_about")
public class About {

    @TableId(value = "about_id", type = IdType.ASSIGN_ID)
    private Long aboutId;
    private String name;
    private String content;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;
    @TableField("comment_enable")// 是否开启评论
    private String commentEnable;
}
