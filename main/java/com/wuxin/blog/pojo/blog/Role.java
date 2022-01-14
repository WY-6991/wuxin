package com.wuxin.blog.pojo.blog;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_id",type = IdType.AUTO)
    private Long roleId;
    @TableField(value = "role_name")
    private String roleName;
    private String description;
}
