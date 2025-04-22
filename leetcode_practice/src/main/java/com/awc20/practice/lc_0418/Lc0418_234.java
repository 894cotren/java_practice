package com.awc20.practice.lc_0418;

import java.util.List;

/*
* 回文链表*/
public class Lc0418_234 {

    public static void main(String[] args) {
        ListNode testNode =new ListNode(2, new ListNode(3, new ListNode(4, null)));
        boolean palindrome = new Solution().isPalindrome(testNode);
    }

    static class Solution2{
        public boolean isPalindrome(ListNode head) {
            //特殊值校验
            if (head==null){
                return false;
            }
            if (head.next==null){
                return true;
            }

            ListNode tempHead=head;
            //找head的中间结点；如果是奇数就中间结点，如果是偶数就中间偏右
            ListNode midHead =this.findMidHead(tempHead);
            //反转中间结点后的链表
            ListNode checkHead = invertListNode(midHead);
            //遍历两链表
            while (checkHead!=null){
                //对比值是否一样
                if (checkHead.val!=head.val){
                    return false;
                }
                //迭代
                checkHead=checkHead.next;
                head=head.next;
            }
            //到这儿就是回文链表
            return true;

        }

        private ListNode findMidHead(ListNode tempHead) {
            //快慢指针找中点，快指针要比慢指针多走一倍
            ListNode slow=tempHead;
            ListNode fast=tempHead;

            while (fast!=null && fast.next!=null){
                slow=slow.next;
                fast=fast.next.next;
            }
            return  slow;
        }
    }

    /**
     * 成功，先复制一个一样的链表，在反转一个链表，两个同时遍历如果不一样就返回false
     */
    static class Solution {
        //反转链表再两个一起遍历看看是不是一样?   //可以，只是复杂度有点高。
        public boolean isPalindrome(ListNode head) {
            //先复制链表

            ListNode head1=head;
            ListNode copyHead=new ListNode();
            ListNode temp =copyHead;
            while (head1!=null){
                temp.val=head1.val;
                if(head1.next!=null){
                    temp.next=new ListNode();
                }
                temp=temp.next;
                head1=head1.next;
            }
            //再反转链表
            ListNode head2 = invertListNode(copyHead);

            while (head!=null && head2!=null){
                if((head.val)!=(head2.val)){
                    return false;
                }
                head=head.next;
                head2=head2.next;
            }
            return true;
        }
    }



     public static class ListNode {
         int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }

     //反转链表
     public static ListNode invertListNode(ListNode head){
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
