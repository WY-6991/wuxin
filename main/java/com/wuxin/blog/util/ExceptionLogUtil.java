package com.wuxin.blog.util;


import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.wuxin.blog.pojo.ExceptionLog;
import com.wuxin.blog.service.ExceptionService;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Slf4j
public class ExceptionLogUtil {


    @Autowired
    private ExceptionService exceptionService;


    public ExceptionLog addException(HttpServletRequest request, String msg,ExceptionLog exceptionLog) {


        // User user = ShiroUtil.getUser();
        //
        // // 未登录
        // if (user == null) {
        //     return null;
        // }


        String requestURI = request.getRequestURI();
        Map<String, String[]> parameterMap = request.getParameterMap();
        String method = request.getMethod();
        for (Map.Entry<String, String[]> stringEntry : parameterMap.entrySet()) {
            log.info("stringEntry:{}", stringEntry);
        }
        String args = JSONUtil.toJsonStr(parameterMap);


        log.info("url:{},parameterMap:{}", requestURI, JSONUtil.toJsonStr(parameterMap));

        // 请求路径
        exceptionLog.setUrl(request.getRequestURI());
        // 获取当前操作用户
        // exceptionLog.setUsername(user.getUsername());
        // 获取异常信息
        if(!msg.isEmpty()){
            exceptionLog.setMessage(msg);
        }else {
            exceptionLog.setMessage("空指针异常");
        }

        // 获取请求参数类型
        exceptionLog.setType(request.getMethod());
        // 获取请求方法名
        exceptionLog.setMethod(method);
        // 获取请求头
        String header = request.getHeader("User-Agent");
        // 获取user-agent
        UserAgent userAgent = UserAgent.parseUserAgentString(header);
        // 获取操作系统
        exceptionLog.setOs(userAgent.getOperatingSystem().toString());
        // 获取浏览器型号
        exceptionLog.setBrowser(userAgent.getBrowser().toString());
        // exceptionLog.setArgs(args);
        log.info("exception:{}", exceptionLog);
        return exceptionLog;
    }


}
