package com.wuxin.blog.pojo.blog;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
public class Archive implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "archive_id",type = IdType.ASSIGN_ID)
    private Long archiveId;

    /**
     * id
     */
    @TableField("blog_id")
    private Long blogId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章类型  原创 和 转载
     */
    private String type;

    /**
     * 归档时间 如 2021,2,23
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;


    /**
     *  归档时间 如 2021-2
     */
    @TableField(value = "archive_title")
    private String archiveTitle;

}
