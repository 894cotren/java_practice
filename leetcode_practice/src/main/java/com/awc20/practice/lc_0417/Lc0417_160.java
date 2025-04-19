package com.awc20.practice.lc_0417;

import java.util.HashSet;

/*
* 160.相交链表*/
public class Lc0417_160 {
    public static void main(String[] args) {
        HashSet<Object> objects = new HashSet<>();
    }


    public class Solution2 {
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            //参数校验
            if(headA==null||headB==null){
                return null;
            }
            //同时遍历两条链表，当链表遍历到底时从头遍历另一条。当相等时就为相交结点。
            ListNode tempA=headA;
            ListNode tempB=headB;
            boolean flag=false;
            while (true){
                //对比两个指针，如果指向同一个结点，那么就是相交结点直接返回。
                if (tempA==tempB){
                    return tempA;
                }
                //如果不是，指针们迭代
                if(tempA.next==null){
                    //flag为了限制，最多走完两个链表，也就A链表遍历完再遍历完B链表都没有找到的话
                    //还没找到的话那么说明没有相交。
                    //可以防止未相交链表相互无限遍历。
                    if (flag){
                        break;
                    }
                    tempA=headB;
                    flag=true;

                }else {
                    tempA=tempA.next;
                }

                if (tempB.next==null){
                    tempB=headA;
                }else {
                    tempB=tempB.next;
                }

            }

            return null;

        }
    }

    //解法1：通过set集合，遍历一条链表将它存入集合，再遍历另一条链表一一查看set集合是否存在
    //找到第一个set存在的，也就是两链表的第一个公共结点，香蕉结点。
     class Solution1 {
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            //参数校验
            if(headA==null||headB==null){
                return null;
            }
            HashSet<Object> set = new HashSet<>();
            //遍历A链表，并将A链表装入set集合
            ListNode temp= headA;
            while (temp!=null){
                set.add(temp);
                temp=temp.next;
            }
            //遍历b链表，寻找第一个set集合里存在的节点，那么就是相交节点
            temp=headB;
            while (temp!=null){
                if(set.contains(temp)){
                    //找到并返回
                    return temp;
                }
                temp=temp.next;
            }
            //找不到，返回null
            return null;
        }
    }



    class ListNode {
         int val;
         ListNode next;
         ListNode(int x) {
             val = x;
             next = null;
         }
     }
}
