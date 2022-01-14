package com.wuxin.blog.pojo.blog;

import com.baomidou.mybatisplus.annotation.*;
import com.wuxin.blog.pojo.mode.CreateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_about")
public class About extends CreateTime implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Long ABOUT_ID  = 1L;

    /**
     * id 唯一 默认为1L 不可修改
     */
    @TableId(value = "about_id", type = IdType.ASSIGN_ID)
    private Long aboutId;

    /**
     * 名称标题 可不填
     */
    private String name;

    /**
     * 内容
     */
    private String content;

    /**
     * 是否开启平
     */
    @TableField("comment_enabled")
    private boolean commentEnabled;
}
