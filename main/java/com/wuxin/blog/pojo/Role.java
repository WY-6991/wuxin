package com.wuxin.blog.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_role")
public class Role {

    @TableId(value = "role_id",type = IdType.AUTO)
    private Integer roleId;
    @TableField(value = "role_name")
    private String roleName;
    private String description;
}
