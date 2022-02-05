package com.wuxin.blog.pojo.blog;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_archive_title")
public class ArchiveTitle {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 归档标题
     */
    @TableField(value = "archive_title")
    private String archiveTitle;

    /**
     * color
     */
    private String color;


    /**
     * 列表
     */
    @TableField(exist = false)
    private List<Archive> archiveList = new ArrayList<>();
}
