package com.awc20.practice.lc_0418;

import java.util.List;

/*
* 回文链表*/
public class Lc0418_234 {

    public static void main(String[] args) {

    }

    class Solution {
        //反转链表再两个一起遍历看看是不是一样?   //可以，只是复杂度有点高。
        public boolean isPalindrome(ListNode head) {
            //先复制链表
            //再反转链表
            ListNode head1=head;
            ListNode copyHead=new ListNode();
            ListNode temp =copyHead;
            while (head1!=null){
                temp.val=head1.val;
                temp.next=new ListNode();
                temp=temp.next;
                head1=head1.next;
            }

            ListNode head2 = invertListNode(copyHead);
            while (head!=null&&head2!=null){
                if((head.val)!=(head2.val)){
                    return false;
                }
                head=head.next;
                head2=head2.next;
            }
            return true;
        }
    }



     public class ListNode {
         int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }

     //反转链表
     public ListNode invertListNode(ListNode head){
        //参数校验  head不为空
         if(head==null){
             return null;
         }
         //校验如果是只有一个结点，那么直接返回
         if(head.next==null){
             return head;
         }

         ListNode pre =null;
         ListNode cur =head;
         ListNode next=head.next;

         while (true){
             //开始反转
             cur.next=pre;
             //开始移动指针
             pre=cur;
             if(next==null){
                 //如果下一个已经为空，那么就反转完成
                 break;
             }
             cur=next;
             next=next.next;
         }
         return cur;
     }

}
