package com.wuxin.blog.exception;

/**
 * @Author: wuxin001
 * @Date: 2022/01/18/17:40
 * @Description: 内容不存在
 */
public class NotFoundException extends RuntimeException{

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
