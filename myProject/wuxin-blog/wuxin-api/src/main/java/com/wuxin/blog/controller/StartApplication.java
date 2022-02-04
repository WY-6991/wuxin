package com.wuxin.blog.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: wuxin001
 * @Date: 2022/02/04/18:05
 * @Description:
 */
@ComponentScan(basePackages = "com.wuxin.blog")
@SpringBootApplication
public class StartApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class);
        System.out.println("=======================项目启动完毕============================");
    }
}
