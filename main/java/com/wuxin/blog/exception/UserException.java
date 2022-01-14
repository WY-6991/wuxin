package com.wuxin.blog.exception;

/**
 * @Author: wuxin001
 * @Date: 2022/01/04/20:30
 * @Description: 用户未登录异常
 */
public class UserException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public UserException() {
        super();
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
}
