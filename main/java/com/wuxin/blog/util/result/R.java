package com.wuxin.blog.util.result;

public class R {

    public static Result ok(Object result) {
        return buildResult(ResultCode.SUCCESS, "成功", result);
    }


    public static Result uploadSuccess(Object data) {
        return new Result(200,"上传成功！",null,data);
    }


    public static Result uploadFail(Object data) {
        return new Result(400,"上传失败！",null,data);
    }

    public static Result create(int code, String message) {
        return buildResult(code, message, null, null);
    }

    public static Result error(String message) {
        return buildResult(ResultCode.FAIL, message, null);
    }

    public static Result buildResultNotAuth(Object result) {
        return buildResult(ResultCode.NOT_AUTHC, "对不起，没有权限！", null);
    }

    public static Result buildResultException401(Object reuslt) {
        return buildResult(ResultCode.UNAUTHORIZED, "请先登录！", null);
    }

    public static Result buildResultException500(Object result) {
        return buildResult(ResultCode.INTERNAL_SERVER_ERROR, "服务器跑路了~", null);
    }

    public static Result buildResult(ResultCode resultCode, String message, Object result) {
        return buildResult(resultCode.code, message, result, null);
    }


    public static Result buildResult(int resultCode, String message, Object result, Object data) {
        return new Result(resultCode, message, result, data);
    }
}
