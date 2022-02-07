package com.wuxin.blog.config;

import com.wuxin.blog.interceptor.AccessLimitInterceptor;
import com.wuxin.blog.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * web配置
 * @author Administrator
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {


    @Autowired
    private AccessLimitInterceptor accessLimitInterceptor;

    @Autowired
    private TokenInterceptor tokenInterceptor;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // .allowedOrigins("http://localhost:2768","http://localhost:8080")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                .maxAge(3600);
    }




    /**
     * 拦截器注册
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       // registry.addInterceptor(accessLimitInterceptor).addPathPatterns("/**");
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/admin/**");

    }


}
