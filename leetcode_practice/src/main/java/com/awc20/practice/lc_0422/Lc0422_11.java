package com.awc20.practice.lc_0422;

import com.sun.source.tree.LambdaExpressionTree;

/**
 * 11. 盛最多水的容器
 */
public class Lc0422_11 {
    public static void main(String[] args) {
        int[] height={1,8,6,2,5,4,8,3,7};
        int i = Solution.maxArea(height);
        System.out.println(i);
    }

    static class Solution {
        public static int maxArea(int[] height) {
            /**
             * 双指针，移动每次移动矮的那根去找最大容积，每移动一次，更新下最大值
             *  原理是分类讨论，讨论矮的那根。
             *  从两边往中间找，矮的那根不动，动长的那一边，如果中间的比短的矮，那么宽变窄了，高变矮了容积肯定小
             *  如果中间那根长，长也没用木桶效应只看矮的，那么高不变，但是宽比移动前变矮了，所以容积也是变小
             *  所以：要想找更大的容积，肯定要移动矮的那根，因为矮的那根我们讨论过了，找不到更大容积
             */
            int max=0;
            int left=0;
            int right=height.length-1;
            while (left<right){
                //计算容积
                int volume=(right-left)*Math.min(height[left],height[right]);
                //更新最大容积
                max=Math.max(max,volume);
                //移动矮的那根指针
                if (height[left]>height[right]){
                    right--;
                }else {
                    left++;
                }
            }
            return max;
        }
    }
}
