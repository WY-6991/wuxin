package com.wuxin.design.signle;

/**
 * @Author: wuxin001
 * @Date: 2022/02/08/15:53
 * @Description:
 */
public class Demo03 {

    public static void main(String[] args) {
        Cat instance = Cat.getInstance();
        Cat instance1 = Cat.getInstance();
        System.out.println(instance == instance1);
        System.out.println(instance.hashCode());
        System.out.println(instance1.hashCode());
    }
}


class Cat {

    private static Cat cat;

    private Cat() {

    }

    public static synchronized Cat getInstance() {
        if (cat == null) {
            cat = new Cat();
        }
        return cat;
    }
}
