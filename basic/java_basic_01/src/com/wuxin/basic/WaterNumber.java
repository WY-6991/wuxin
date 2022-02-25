package com.wuxin.basic;

import java.util.Scanner;

/**
 * @Author: wuxin001
 * @Date: 2022/02/25/15:31
 * @Description: 水仙花数是指一个 3 位数，它的每个位上的数字的 3次幂之和等于它本身
 */
public class WaterNumber {
    public static void main(String[] args) throws Exception {
        int i = 0;
        while (true) {
            System.out.println("================水仙花数=================");
            // 输入的是否是整数
            System.out.print("请输入大于100的三位数:");
            try {
                Scanner scanner = new Scanner(System.in);
                i = scanner.nextInt();
                if (i <= 100) {
                    throw new Exception("输入的数小于100,请重新输入");
                }
                WaterNumber waterNumber = new WaterNumber();
                // waterNumber.add(i);
                waterNumber.waterNum(i);
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }


    // 计算三位数大小相同
    void add(Integer total) {
        int sum = 100;
        while (sum <= total) {
            int i = sum / 100;
            int j = i * 100 + i * 10 + i;
            if (sum == j) {
                System.out.println("[100-" + sum + "]以内的水仙花数:" + j);
            }
            sum++;

        }

    }


    void waterNum(Integer total) {

        for (int integer = 100; integer < total; integer++) {
            int h = integer / 100;
            int tens = integer / 10 - h * 10;
            int ones = integer % 10;
            int sum = h * h * h + tens * tens * tens + ones * ones * ones;
            if (integer == sum) {
                System.out.println("[100-" + sum + "]以内的水仙花数:" + sum);
            }
        }
    }
}
