package com.wuxin.blog.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: wuxin001
 * @Date: 2021/10/02/8:40
 * @Description: 登录日志实体
 *
 * 该字段信息不会存入数据库中,使用redis数据作为缓存
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_login_log")
public class LoginLog {

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;
    private String username;//用户名称
    private String ip;//ip
    @TableField("ip_source")
    private String ipSource;//ip来源
    private String os;//操作系统 win10
    private String browser;//浏览器 火狐,谷歌等等
    private String status;//登录状态 success error
    private Integer code;
    // private String description;//操作信息
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;//操作时间
    @TableField("user_agent")
    private String userAgent;// 代理标识
}
