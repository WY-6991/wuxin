package com.wuxin.blog.pojo.blog;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "category_id",type = IdType.AUTO)
    private Long cid;
    private String name;
    private String color;
}
