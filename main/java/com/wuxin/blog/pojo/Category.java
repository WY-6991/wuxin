package com.wuxin.blog.pojo;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_category")
public class Category {

    @TableId(value = "category_id",type = IdType.AUTO)
    private Long cid;
    private String name;
    private String color;
}
