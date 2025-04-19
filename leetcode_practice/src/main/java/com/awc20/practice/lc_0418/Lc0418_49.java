package com.awc20.practice.lc_0418;

import org.w3c.dom.ls.LSException;

import java.util.*;
import java.util.function.Function;

public class Lc0418_49 {

    public static void main(String[] args) {
        String strs[] = {"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> lists = new Solution().groupAnagrams(strs);
    }

    static class Solution {
        public List<List<String>> groupAnagrams(String[] strs) {
            Map<String,List<String>> map =new HashMap<>();
            for (String str : strs) {
                //拆散掉字符串，进行一个规则排序，如果排序后是相等的，那么他们就符合字母异位词
                char[] chars = str.toCharArray();
                Arrays.sort(chars);
                String strKey = new String(chars);
                //在通过map的api，如果存在key那么就拿到key的值，如果不存在key就调用函数，结果作为值存入，并返回值。
                map.computeIfAbsent(strKey, k->new ArrayList<String>()).add(str);
            }
            //获取到map的值的集合，返回。
            return new ArrayList<>(map.values());
        }
    }
}
