package com.wuxin.blog.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: wuxin001
 * @Date: 2021/10/01/9:25
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_archive")
public class Archive {

    @TableId(value = "archive_id",type = IdType.ASSIGN_ID)
    private Long archiveId;
    @TableField("blog_id")
    private Long blogId; // 文章路径
    @TableField("blog_title")
    private String blogTitle; // 文章标题
    @TableField("type")
    private String type; //  原创 和 转载
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime; // 归档时间 如 2021,2,23
    @TableField("archive_title")
    private String archiveTitle;// 归档级别 2021.9
}
