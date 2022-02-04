package com.wuxin.blog.exception;

/**
 * @Author: wuxin001
 * @Date: 2022/01/01/22:59
 * @Description: 自定义异常
 */
public class CustomException extends RuntimeException{

    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
    }


    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }


}
