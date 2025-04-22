package com.awc20.practice.lc_0421;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * 128. 最长连续序列
 */
public class Lc0421_128 {

    public static void main(String[] args) {
        int[] nums ={0,3,7,2,5,8,4,6,0,1};
        Solution solution=new Solution();
        int i = solution.longestConsecutive(nums);
        System.out.println(i);
    }

    /**
     *             //排序数组
     *             //获取最小值
     *             //往后遍历，如果等于最小值那么就往后继续遍历
     *             //如果等于min+1，那么计数器+1，min也+1迭代
     *             //剩下情况就直接退出
     *
     *             PS:这里有个问题，就是如果是数组后面的部分顺序是最长的呢？我只遍历了前面，中断了就没后续了。
     */
    static class Solution {
        public int longestConsecutive(int[] nums) {
            //我如果直接一个排序然后开始比你又怎么办呢
            if (nums.length==0){
                return 0;
            }
            if(nums.length==1){
                return 1;
            }

            //排序数组
            Arrays.sort(nums);
            //获取最小值
            int min=nums[0];

            int count =1;
            int maxCount=1;
            for (int i = 1; i < nums.length; i++) {
                //往后遍历，如果等于最小值那么就往后继续遍历
                if (nums[i]==min){
                    continue;
                }else if (nums[i]==min+1){
                    //如果等于min+1，那么计数器+1，min也+1迭代
                    count++;
                    min++;
                }else{
                    //剩下的情况往后迭代，可能后面的顺序段会比较长
                    //我们把当前顺序数保存下，只记录最大的
                    if (count>maxCount){
                        maxCount=count;
                    }
                    //往后迭代，最小的更新
                    count=1;
                    min=nums[i];
                }
                if (count>maxCount){
                    maxCount=count;
                }
            }
            return maxCount;
        }
    }

}
