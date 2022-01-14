package com.wuxin.blog.pojo.log;


import com.baomidou.mybatisplus.annotation.*;
import com.wuxin.blog.mode.Log;
import lombok.*;

import java.io.Serializable;

/**
 * 访问信息来源实体类
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@TableName("wx_access_log")
public class AccessLog extends Log implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;


}
