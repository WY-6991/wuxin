package com.wuxin.design.signle.single3;

/**
 * @Author: wuxin001
 * @Date: 2022/02/09/14:15
 * @Description: 静态内部类
 */
public class SingleTest {

    public static void main(String[] args) {
        Demo instance = Demo.getInstance();
        Demo instance1 = Demo.getInstance();
        System.out.println(instance == instance1);
        System.out.println(instance.hashCode());
        System.out.println(instance1.hashCode());
    }

}

class Demo {
    private Demo() {

    }

    private static class Demo01 {
        private static final Demo DEMO = new Demo();
    }

    public static Demo getInstance() {
        return Demo01.DEMO;
    }
}
