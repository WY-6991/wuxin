package com.wuxin.blog.utils.result;


import com.wuxin.blog.constant.HttpStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;


public class Result extends HashMap<String, Object> {


    public final static String CODE = "code";
    public final static String MESSAGE = "message";
    public final static String RESULT = "result";

    public Result() {

    }

    public Result(Integer code, String message) {
        super.put(CODE, code);
        super.put(MESSAGE, message);
    }

    public Result(Integer code, String message, Object result) {
        super.put(CODE, code);
        super.put(MESSAGE, message);
        super.put(RESULT, result);
    }


    /**
     * 默认请求成功信息
     *
     * @return result
     */
    public static Result ok() {
        return new Result(HttpStatus.SUCCESS, "请求成功");
    }


    /**
     * 请求成功 自定义返回结果
     *
     * @param result result
     * @return result
     */
    public static Result ok(Object result) {
        return new Result(HttpStatus.SUCCESS, "请求成功", result);
    }

    /**
     * 自定义返回结果
     *
     * @param message message
     * @param result  result
     * @return result
     */
    public static Result ok(String message, Object result) {
        return new Result(HttpStatus.SUCCESS, message, result);
    }

    /**
     * 默认请求失败信息
     *
     * @return result
     */
    public static Result error() {
        return new Result(HttpStatus.ERROR, "请求失败");
    }

    /**
     * 自定义失败信息
     *
     * @param message message
     * @return result
     */
    public static Result error(String message) {
        return new Result(HttpStatus.ERROR, message);
    }

    /**
     * 自定义失败状态码,请求信息
     *
     * @param code    状态码
     * @param message message
     * @return result
     */
    public static Result error(Integer code, String message) {
        return new Result(code, message);
    }

    /**
     * 自定义结果
     *
     * @param code    code
     * @param message message
     * @return result
     */
    public static Result create(Integer code, String message) {
        return new Result(code, message);
    }


    /**
     * 自定义结果
     *
     * @param code    code
     * @param message message
     * @param result  result
     * @return result
     */
    public static Result create(Integer code, String message, Object result) {
        return new Result(code, message, result);
    }


    /**
     * 自定义在原基础上链式拓展
     *
     * @param key   key
     * @param value value
     * @return result
     */
    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
