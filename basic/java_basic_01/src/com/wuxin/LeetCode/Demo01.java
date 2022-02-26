package com.wuxin.LeetCode;

import java.util.Arrays;

// 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那 两个 整数，并返回它们的数组下标。
//
//         你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
//
//         你可以按任意顺序返回答案。
//
//         示例 1：
//
//         输入：nums = [2,7,11,15], target = 9
//         输出：[0,1]
//         解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
//         示例 2：
//
//         输入：nums = [3,2,4], target = 6
//         输出：[1,2]
//         示例 3：
//
//         输入：nums = [3,3], target = 6
//         输出：[0,1]
//         提示：
//
//         2 <= nums.length <= 104
//         -109 <= nums[i] <= 109
//         -109 <= target <= 109
//         只会存在一个有效答案
//         进阶：你可以想出一个时间复杂度小于 O(n2) 的算法吗？
//
//         Related Topics
//         数组
//         哈希表
//
//         👍 13531
//         👎 0


/**
 * @Author: wuxin001
 * @Date: 2022/02/26/0:48
 * @Description: 计算两个数相加
 */
public class Demo01 {

    public static void main(String[] args) {
        Demo01 demo01 = new Demo01();

        int[] nums = {1, 3, 4, 5, 6};
        int target = 10;

        int[] ints = demo01.twoSum(nums, target);
        System.out.println(Arrays.toString(ints));
    }


    public int[] twoSum(int[] nums, int target) {

        // 初始化两个数组下表容器
        int[] ints = new int[2];

        int i = 0;
        int j = 0;

        for (int i1 = 0; i1 < nums.length; i1++) {
            int a = nums[i1];
            for (int i2 = 1; i2 < nums.length; i2++) {
                if (nums[i1] != nums[i2] && (nums[i1] + nums[i2] == target)) {
                    ints[0] = i1;
                    ints[1] = i2;

                }
            }
        }

        return ints;

    }
}
