package com.awc20.practice.lc_0417;

import org.w3c.dom.ls.LSException;

import java.util.List;

/*反转链表*/
public class Lc0417_206 {

    public static void main(String[] args) {

    }

    class Solution {
        /*
        * 三指针平推*/
        public ListNode reverseList(ListNode head) {

            //参数校验
            if (head==null){
                return null;
            }
            if (head.next==null){
                return head;
            }

            ListNode pre=null;
            ListNode cur=head;
            ListNode nxt=head.next;

            while (true){
                cur.next=pre;
                pre=cur;
                //如果nxt已经是空了，那么就反转完成了。
                if (nxt==null){
                    return cur;
                }
                cur=nxt;
                nxt=nxt.next;
            }
        }
    }

     class ListNode {
         int val;
         ListNode next;

         ListNode() {
         }

         ListNode(int val) {
             this.val = val;
         }

         ListNode(int val, ListNode next) {
             this.val = val;
             this.next = next;
         }
     }
}
