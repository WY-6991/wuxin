package com.wuxin.blog.mode;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: wuxin001
 * @Date: 2021/12/16/14:45
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {


    /**
     * 方法
     */
    public String method;


    /**
     * 请求类型
     */
    public String type;


    /**
     * 请求结果
     */
    public String result;


    /**
     * 请求状态码
     */
    public Integer code;


    /**
     * 操作系统
     */
    public String os;


    /**
     * ip
     */
    public String ip;


    /**
     * ip来源
     */
    public String ipSource;


    /**
     * 浏览器类型
     */
    public String browser;


    /**
     * 标识
     */
    public String byCreate;


    /**
     * 操作说明
     */
    public String description;

    /**
     * 操作用户
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT_UPDATE)
    public Date createTime;



}
