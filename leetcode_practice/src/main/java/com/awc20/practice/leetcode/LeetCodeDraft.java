package com.awc20.practice.leetcode;

import com.sun.jdi.Value;

import java.lang.reflect.Array;
import java.security.Key;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class LeetCodeDraft {

    {

    }

    public static void main(String[] args) {
        System.out.println(MyInterface.getHello());
    }


    class Solution {
        public List<List<Integer>> permute(int[] nums) {
            int len = nums.length;
            boolean[] mirror = new boolean[len];
            int depth = 0;
            List<List<Integer>> result = new ArrayList<>();
            List<Integer> elem = new ArrayList<>();
            recursion(len,depth,nums,mirror,result,elem);
            return result;
        }

        private void recursion(int len, int depth, int[] nums, boolean[] mirror, List<List<Integer>> result, List<Integer> elem) {
            if (depth==len){
                //记录
                result.add(new ArrayList<>(elem));
                return;
            }

            for (int i = 0; i < nums.length; i++) {
                if (mirror[i]){
                    //如果使用过了就下一个
                    continue;
                }
                //没使用就收录,标记，并进行递归
                elem.add(nums[i]);
                mirror[i]=true;
                recursion(len,depth+1,nums,mirror,result,elem);
                //出来了进行一个擦除
                mirror[i]=false;
                elem.remove(elem.size()-1);
            }
        }
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

}
