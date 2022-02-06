package com.wuxin.blog.pojo.log;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuxin.blog.mode.Log;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wuxin001
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_exception_log")
public class ExceptionLog extends Log implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 请求参数
     */
    private String params;




}
