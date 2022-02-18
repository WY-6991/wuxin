package com.wuxin.blog.mode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: wuxin001
 * @Date: 2022/01/10/20:00
 * @Description: github 图床操作实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Github implements Serializable {

    private static final long serialVersionUID = 1L;

    private String url;

    private String sha;

    private String message;
}
