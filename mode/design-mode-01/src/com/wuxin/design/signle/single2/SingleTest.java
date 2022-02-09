package com.wuxin.design.signle.single2;

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

    static {
        instance = new Demo();
    }

    public static Demo getInstance() {
        return instance;
    }


}