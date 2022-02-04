package com.wuxin.blog.mode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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

public class PageVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Integer current;
    private Integer limit;
    private String keywords;
    private Object content;
    private Integer type;
    private String start;
    private String end;
}
