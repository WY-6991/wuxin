package com.wuxin.design.signle;

/**
 * @Author: wuxin001
 * @Date: 2022/02/08/15:56
 * @Description: 单例模式双重检查
 */
public class Demo04 {

    public static void main(String[] args) {
        Person instance = Person.getInstance();
        Person instance1 = Person.getInstance();
        System.out.println(instance == instance1);
        System.out.println(instance.hashCode());
        System.out.println(instance1.hashCode());
    }
}

class Person {


    // 构造器私有化
    private Person() {

    }

    private static Person person = null;

    // 使用关键词synchronized
    public static Person getInstance() {
        if (person == null) {
            synchronized (Person.class) {
                if (person == null) {
                    person = new Person();
                }
            }
        }
        return person;
    }


}