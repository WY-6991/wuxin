package com.wuxin.basic;

/**
 * 在java中一关键字是有具体含义的
 * 关键字特点
 *  1、全部小写
 *  2、有具体含义
 * @Author: wuxin001
 * @Date: 2022/02/25/14:58
 * @Description: 关键字
 */
public class Keywords {
    public static void main(String[] args) {
        Keywords keywords = new Keywords();
        keywords.test();
    }

    void test(){
        System.out.println(this);
    }
}
