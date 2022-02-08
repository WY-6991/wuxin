package com.wuxin.design.signle;

/**单例模式静态内部内实现
 * @Author: wuxin001
 * @Date: 2022/02/08/16:03
 * @Description:  枚举单例：线程安全，调用效率高，不能延时加载，可以天然的防止反射和反序列化调用
 */
public class Demo06 {
    public static void main(String[] args) {
        SingletonDemo06 instance = SingletonDemo06.getInstance();
        SingletonDemo06 instance1 = SingletonDemo06.getInstance();
        System.out.println(instance == instance1);
        System.out.println(instance.hashCode());
        System.out.println(instance1.hashCode());
    }
}

class SingletonDemo06 {

    private SingletonDemo06() {

    }


    /**
     * 静态内部类实现
     */
    private static class Hello {

        private static SingletonDemo06 singletonDemo06 = new SingletonDemo06();
    }

    public static SingletonDemo06 getInstance() {
        return Hello.singletonDemo06;
    }
}
