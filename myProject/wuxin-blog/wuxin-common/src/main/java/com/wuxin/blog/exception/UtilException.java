package com.wuxin.blog.exception;

/**
 * @Author: wuxin001
 * @Date: 2022/01/03/16:44
 * @Description:
 */
public class UtilException extends RuntimeException{
    private static final long serialVersionUID = 8247610319171014183L;

    public UtilException(Throwable e)
    {
        super(e.getMessage(), e);
    }

    public UtilException(String message)
    {
        super(message);
    }

    public UtilException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
