package com.wuxin.blog.util.result;

public enum ResultCode {
    SUCCESS(200),
    FAIL(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    NOT_AUTHC(600);


    public int code;

    ResultCode(int code) {
        this.code = code;
    }
}
