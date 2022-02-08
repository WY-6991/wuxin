package com.wuxin.design.signle;

/**
 * @Author: wuxin001
 * @Date: 2022/02/08/15:33
 * @Description: 单例模式 饿汉式1
 */
public class Demo01 {

    public static void main(String[] args) {


        User user = User.USER;
        User user1 = User.USER;
        System.out.println(user == user1);

        System.out.println(user.hashCode());
        System.out.println(user1.hashCode());


    }
}

class User {

    private User(){

    }

    public final static User USER = new User();


}
