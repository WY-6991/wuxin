package com.wuxin.basic;

/**
 * @Author: wuxin001
 * @Date: 2022/02/25/15:06
 * @Description: 类型转换
 */
public class ChangeType {

    public static void main(String[] args) {

        String c = "100";
        Integer d = Integer.parseInt(c);
        System.out.println(d);

        int i = 1;
        char j = 'c';
        System.out.println(i);
        System.out.println(j);
        System.out.println((int)j); //99
    }
}
