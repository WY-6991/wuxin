package com.wuxin.blog.filter;

import cn.hutool.json.JSONUtil;
import com.wuxin.blog.constant.HttpStatus;
import com.wuxin.blog.utils.result.Result;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Author: wuxin001
 * @Date: 2022/01/11/14:35
 * @Description:
 */
public class CorsAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(CorsAuthenticationFilter.class);

    public CorsAuthenticationFilter() {
        super();
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        logger.info("========================经过自定义过滤器================================");
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Methods", "*");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setStatus(HttpServletResponse.SC_OK);
        res.setCharacterEncoding("UTF-8");
        if ("OPTIONS".equals(((HttpServletRequest) request).getMethod().toUpperCase())) {
            return true;
        }

        return super.isAccessAllowed(request, response, mappedValue);

    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Methods", "*");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setStatus(HttpServletResponse.SC_OK);
        res.setCharacterEncoding("UTF-8");
        PrintWriter writer = res.getWriter();
        writer.write(JSONUtil.toJsonStr(Result.error(HttpStatus.UNAUTHORIZED, "未登录")));
        writer.close();
        return false;
    }
}
