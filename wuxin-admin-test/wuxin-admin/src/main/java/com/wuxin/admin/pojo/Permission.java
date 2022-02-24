package com.wuxin.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wuxin001
 * @Date: 2022/02/24/12:11
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("permission")
public class Permission {

    @TableId(value = "pid",type = IdType.AUTO)
    private Long pid;
    private String content;
}
