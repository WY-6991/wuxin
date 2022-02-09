package com.wuxin.design.signle.single8;

/**
 * @Author: wuxin001
 * @Date: 2022/02/09/13:40
 * @Description: 枚举
 */
public class SingleTest {

    public static void main(String[] args) {

        Demo instance = Demo.INSTANCE;
        Demo instance1 = Demo.INSTANCE;
        // true
        System.out.println(instance1 == instance);
        // 1163157884
        System.out.println(instance.hashCode());
        // 1163157884
        System.out.println(instance1.hashCode());
    }
}

enum Demo {

    INSTANCE


}