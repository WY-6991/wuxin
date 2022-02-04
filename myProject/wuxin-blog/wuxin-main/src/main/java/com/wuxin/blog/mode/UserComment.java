package com.wuxin.blog.mode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wuxin001
 * @Date: 2022/01/20/14:03
 * @Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserComment {

    public final static boolean COMMENT_TYPE = false;
    public final static boolean REPLY_TYPE = true;


    /**
     * 评论用户Id
     */
    private Long userId;

    /**
     * 是否发布消息
     */
    private boolean subscription;


    /**
     * false 是评论，ture是回复
     */
    private boolean type;



}
