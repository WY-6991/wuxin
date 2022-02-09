package com.wuxin.design.signle.single1;

/**
 * @Author: wuxin001
 * @Date: 2022/02/09/13:40
 * @Description:
 */
public class SingleTest {

    public static void main(String[] args) {
        Demo01 demo01 = Demo01.getInstance();
        Demo01 demo02 = Demo01.getInstance();
        // true
        System.out.println(demo01 == demo02);
        // 1163157884
        System.out.println(demo01.hashCode());
        // 1163157884
        System.out.println(demo02.hashCode());

    }
}

class Demo01 {

    // 构造器私有化 无法从外部获取
    private Demo01() {

    }

    private final static Demo01 DEMO01 = new Demo01();

    public static Demo01 getInstance(){
        return DEMO01;
    }
}
