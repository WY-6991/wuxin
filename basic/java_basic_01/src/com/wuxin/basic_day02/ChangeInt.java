package com.wuxin.basic_day02;

/**
 * @Author: wuxin001
 * @Date: 2022/02/25/21:44
 * @Description:
 */
public class ChangeInt {
    public static void main(String[] args) {
        int a = 100;
        short b = 100;
        byte c = 100;
        long d = 100;

        float f = 1;

        System.out.println(a == b);
        System.out.println(b == c);
        System.out.println(c == d);
        System.out.println(a == c);
        System.out.println(a == d);


        System.out.println(f);
        System.out.println(f+a);


        System.out.println();
    }
}
