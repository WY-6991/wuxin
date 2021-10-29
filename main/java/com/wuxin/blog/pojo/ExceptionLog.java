package com.wuxin.blog.pojo;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_exception_log")
public class ExceptionLog {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    // 操作用户
    private String username;
    private String url;// 请求路径
    private String os;// 请求路径
    private String browser;// 请求路径
    private String message;// 异常信息
    private String type;// 请求方式 get put post等
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;// 请求时间
    private String method;//请求方式
    private Object[] args;//请求参数
}
