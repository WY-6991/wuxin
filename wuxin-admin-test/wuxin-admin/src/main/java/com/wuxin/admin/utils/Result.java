package com.wuxin.admin.utils;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wuxin001
 * @Date: 2022/02/24/13:26
 * @Description:
 */
public class Result extends HashMap<String,Object> {

    private final static String  CODE = "code";
    private final static String  MESSAGE = "message";
    private final static String  RESULT = "result";

    private Result(){

    }

    private Result(Integer code,String message){
        this.put(CODE,code);
        this.put(MESSAGE,message);
    }

    private Result(Integer code,String message,Object result){
        this.put(CODE,code);
        this.put(MESSAGE,message);
        this.put(RESULT,result);
    }

    public static Result getInstance(){
        return new Result();
    }

    public static Result ok(){
        return new Result(200,"请求成功");
    }

    public static Result ok(Object result){
        return new Result(200,"请求成功",result);
    }

    public static Result error(){
        return new Result(400,"请求错误");
    }

    public static Result error(String message){
        return new Result(400,message);
    }

    public static Result error(Integer code,String message){
        return new Result(code,message);
    }

    public static Result create(Integer code,String message,Object result){
        return new Result(code,message,result);


    }

    public static Result create(Integer code,String message){
        return new Result(code,message);
    }


    @Override
    public Result put(String key, Object value) {
        super.put(key,value);
        return this;
    }
}
