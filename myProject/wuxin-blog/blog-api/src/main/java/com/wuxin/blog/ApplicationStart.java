package com.wuxin.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: wuxin001
 * @Date: 2022/02/08/11:59
 * @Description:
 */
@MapperScan(basePackages = "com.wuxin.blog")
@SpringBootApplication
public class ApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class);
        System.out.println("=================================项目启动成功！========================");
    }
}