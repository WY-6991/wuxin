package com.wuxin.basic;

/**
 * @Author: wuxin001
 * @Date: 2022/02/25/21:30
 * @Description: 方法重载
 */
public class FunDemo1 {
    public static void main(String[] args) {
        int a = 100;
        int b = 10;
        FunDemo1 funDemo1 = new FunDemo1();
        int add = funDemo1.add(a);
        System.out.println(add);

        int add1 = funDemo1.add(a, b);
        System.out.println(add1);

        int add2 = funDemo1.add(a, b, 1);
        System.out.println(add2);

        double v = funDemo1.aDouble(1, 2);
        System.out.println(v);

        double v1 = funDemo1.aDouble1(1, 2);
        System.out.println(v1);

        System.out.println(v1 == v);


    }

    int add(int a, int b) {
        return a + b;
    }

    int add(int a) {
        return a;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }

    double aDouble(double a, double b) {
        return a + b;
    }

    double aDouble1(int a, double b) {
        return a + b;
    }



}
