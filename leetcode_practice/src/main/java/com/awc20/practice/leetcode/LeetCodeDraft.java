package com.awc20.practice.leetcode;

import java.lang.reflect.Array;
import java.util.*;

public class LeetCodeDraft {

    public static void main(String[] args) {
        System.out.println(MyInterface.getHello());
    }

    class Solution {
        public void setZeroes(int[][] matrix) {
            //解法1： 标记法；
            //
            //1. 创建行和列的各自记录行号的数组，set；
            //2. 遍历二维数组，如果元素是0，那么记录行和列；
            //3. 第二次遍历数组，判断当前行号和列号是否在set集合里，在的话进行一个置零；

            //1. 创建行和列的各自记录行号的数组，set；
            int rowLength = matrix.length;
            int colLength = matrix[0].length;
            Set<Integer> colSet= new HashSet<>(colLength);
            Set<Integer> rowSet= new HashSet<>(rowLength);

            for (int i = 0; i < rowLength; i++) {
                for (int j = 0; j < colLength; j++) {
                    if (matrix[i][j]==0){
                        //2. 遍历二维数组，如果元素是0，那么记录行和列；
                        rowSet.add(i);
                        colSet.add(j);
                    }
                }
            }

            for (int i = 0; i < rowLength; i++) {
                for (int j = 0; j < colLength; j++) {
                    if (rowSet.contains(i) || colSet.contains(j)){
                        matrix[i][j]=0;
                    }
                }
            }

        }
    }
    
}
