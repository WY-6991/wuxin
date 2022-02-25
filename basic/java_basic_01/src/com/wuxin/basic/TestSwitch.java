package com.wuxin.basic;

import java.util.Scanner;

/**
 * @Author: wuxin001
 * @Date: 2022/02/25/15:18
 * @Description:
 */
public class TestSwitch {
    public static void main(String[] args) {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("请输入一个数");
                int next = scanner.nextInt();
                if (next > 7 || next < 0) {
                    throw new Exception("输入格式不合法，请重新输入！");
                }
                switch (next) {
                    case 1:
                        System.out.println("今天是星期一");
                        break;
                    case 2:
                        System.out.println("今天是星期二");
                        break;
                    case 3:
                        System.out.println("今天是星期三");
                        break;
                    case 4:
                        System.out.println("今天是星期四");
                        break;
                    case 5:
                        System.out.println("今天是星期五");
                        break;
                    case 6:
                        System.out.println("今天是星期6");
                        break;
                    case 7:
                        System.out.println("今天是星期日");
                        break;
                    default:
                        System.out.println("输入格式不合法，请重新输入！");
                        break;
                }
                break;
            } catch (Exception e) {
                System.out.print("请重新输入");
                e.printStackTrace();
            }
        }


    }
}
