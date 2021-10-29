package com.wuxin.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_tag")
public class Tag {

    @TableId(value = "tag_id", type = IdType.AUTO)
    private Long tagId;
    private String name;
    private String color;

}
