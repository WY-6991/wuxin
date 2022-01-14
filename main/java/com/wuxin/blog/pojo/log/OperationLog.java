package com.wuxin.blog.pojo.log;

import com.baomidou.mybatisplus.annotation.*;
import com.wuxin.blog.mode.Log;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_operation_log")
public class OperationLog extends Log implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String username;

}
