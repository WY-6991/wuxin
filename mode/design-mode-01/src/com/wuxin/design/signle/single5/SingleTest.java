package com.wuxin.design.signle.single5;

/**
 * @Author: wuxin001
 * @Date: 2022/02/09/13:40
 * @Description: 懒汉式 线程不安全
 */
public class SingleTest {

    public static void main(String[] args) {

        Demo instance = Demo.getInstance();
        Demo instance1 = Demo.getInstance();
        // true
        System.out.println(instance1 == instance);
        // 1163157884
        System.out.println(instance.hashCode());
        // 1163157884
        System.out.println(instance1.hashCode());
    }
}

class Demo {

    // 构造器私有化 无法从外部获取
    private Demo() {

    }

    private static Demo demo;

    public static synchronized Demo getInstance() {
        if(demo==null){
            demo = new Demo();
        }
        return demo;
    }


}