package com.wuxin.design.signle;

/**
 * @Author: wuxin001
 * @Date: 2022/02/08/15:39
 * @Description: 单例模式饿汉式2
 */
public class Demo02 {

    public static void main(String[] args) {
        Book book = Book.book;
        Book book1 = Book.book;
        System.out.println(book == book1);
        System.out.println(book.hashCode());
        System.out.println(book1.hashCode());


        System.out.println("=================");
        Content instance = Content.getInstance();
        Content instance1 = Content.getInstance();
        System.out.println(instance == instance1);
        System.out.println(instance.hashCode());
        System.out.println(instance1.hashCode());


    }
}


class Book {

    public static Book book;

    // 在静态代码快中执行
    static {
        book = new Book();
    }

    private Book() {

    }


}


class Content {


    private static Content content = new Content();

    // 构造器私有化，外部无法创建
    private Content() {

    }

    public static Content getInstance() {
        return content;
    }


}