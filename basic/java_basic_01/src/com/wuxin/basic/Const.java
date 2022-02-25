package com.wuxin.basic;

/**
 * @Author: wuxin001
 * @Date: 2022/02/25/15:01
 * @Description: 常量 其值在运行中不可改变的量
 */
public class Const {
    private static Integer A = 100;
    private static final String HELLO = "Hello world";
    private static final Boolean FLAG = true;
    public static void main(String[] args) {
        System.out.println(A);
        System.out.println(HELLO);
        System.out.println(FLAG);
        A = A +100;
        System.out.println(A);
    }
}
