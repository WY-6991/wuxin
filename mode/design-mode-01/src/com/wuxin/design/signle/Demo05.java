package com.wuxin.design.signle;

/**
 * @Author: wuxin001
 * @Date: 2022/02/08/16:00
 * @Description: 单例模式枚举
 */
public class Demo05 {

    public static void main(String[] args) {
        Man man = Man.MAN;
        Man man1 = Man.MAN;
        System.out.println(man == man1);
        System.out.println(man.hashCode());
        System.out.println(man1.hashCode());
    }
}


enum Man {
    MAN
}