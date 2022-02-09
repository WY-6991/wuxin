package com.wuxin.design.signle.single4;

/**
 * @Author: wuxin001
 * @Date: 2022/02/09/13:40
 * @Description:
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

    private static Demo instance;

    // 加入同步代码解决线程不安全问题
    public static  Demo getInstance() {
        if(instance==null){
            instance = new Demo();
        }
        return instance;
    }


}