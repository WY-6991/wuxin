package com.wuxin.blog.config;

import com.wuxin.blog.filter.RepeatableFilter;
import com.wuxin.blog.filter.XssFilter;
import com.wuxin.blog.utils.string.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * 注册过滤器
 * @Author: wuxin001
 * @Date: 2022/01/29/10:33
 * @Description:
 */
@Configuration
public class FilterConfig {

    // @Value("${xss.excludes}")
    // private String excludes;
    //
    // @Value("${xss.urlPatterns}")
    // private String urlPatterns;
    //
    // @SuppressWarnings({ "rawtypes", "unchecked" })
    // @Bean
    // @ConditionalOnProperty(value = "xss.enabled", havingValue = "true")
    // public FilterRegistrationBean xssFilterRegistration()
    // {
    //     FilterRegistrationBean registration = new FilterRegistrationBean();
    //     registration.setDispatcherTypes(DispatcherType.REQUEST);
    //     registration.setFilter(new XssFilter());
    //     registration.addUrlPatterns(StringUtils.split(urlPatterns, ","));
    //     registration.setName("xssFilter");
    //     registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
    //     Map<String, String> initParameters = new HashMap<String, String>();
    //     initParameters.put("excludes", excludes);
    //     registration.setInitParameters(initParameters);
    //     return registration;
    // }
    //
    // @SuppressWarnings({ "rawtypes", "unchecked" })
    // @Bean
    // public FilterRegistrationBean someFilterRegistration()
    // {
    //     FilterRegistrationBean registration = new FilterRegistrationBean();
    //     registration.setFilter(new RepeatableFilter());
    //     registration.addUrlPatterns("/*");
    //     registration.setName("repeatableFilter");
    //     registration.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
    //     return registration;
    // }
}
