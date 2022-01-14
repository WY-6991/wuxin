package com.wuxin.blog.pojo.mode;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: wuxin001
 * @Date: 2022/01/13/10:33
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeTime implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

}
