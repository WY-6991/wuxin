package com.wuxin.blog.enums;

import lombok.Getter;

/**
 * @Author: wuxin001
 * @Date: 2021/12/17/20:59
 * @Description:
 */
@Getter
public enum Message
{
    /**
     * 删除成功
     */
    DEL_SUCCESS("删除成功！"),

    /**
     * 删除失败
     */
    DEL_FAIL("删除成功！"),

    /**
     * 修改成功
     */
    UPDATE_SUCCESS("修改成功!"),

    /**
     * 修改失败
     */
    UPDATE_FAIL("修改成功!"),

    /**
     * 添加成功
     */
    ADD_SUCCESS("添加成功!"),

    /**
     * 添加失败
     */
    ADD_FAIL("添加成功!");


    private final String message;

    Message(String message){
        this.message = message;
    }



}
