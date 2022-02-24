package com.wuxin.admin.handle;

import com.wuxin.admin.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author: wuxin001
 * @Date: 2022/02/24/13:57
 * @Description: 全局异常处理
 */
@RestController
@ControllerAdvice
public class GlobalException {

    private static final Logger logger = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(Exception.class)
    public Result globalException(Exception e){
        logger.debug("异常信息:{}",e);
        return Result.error(500,e.getMessage());
    }
}
