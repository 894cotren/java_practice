package com.awc20.practice.lc_0415;

public class Lc0415_283 {

    public static void main(String[] args) {
        int[] nums = {0, 1, 0, 3, 12};
        moveZeroes(nums);
        System.out.println("123");
    }

    /*
    *
    * 快慢指针，慢指针顺序+为了往前排齐，快指针找需要往前排起的对象并赋值到慢指针位置
    * 最后需要把被覆盖的0补齐到最后，通过慢指针具体数组最长还有多少来补齐*/

    static void moveZeroes(int nums[]) {
        int j=0;
        for (int i = 0; i < nums.length; i++) {
            //第一层遍历，找非零
            if (nums[i]!=0){
                nums[j]=nums[i];
                j++;
            }
        }
        for (;j<nums.length;j++){
            nums[j]=0;
        }
    }

}
