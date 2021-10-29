package com.wuxin.blog.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页基本数据
 *
 * @Author: wuxin001
 * @Date: 2021/08/23/13:26
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PageVo {
    private Long id;
    private Integer current;
    private Integer limit;
    private String keywords;
    private Object content;
}
