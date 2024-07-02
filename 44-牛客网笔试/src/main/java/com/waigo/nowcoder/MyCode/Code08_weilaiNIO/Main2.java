package com.waigo.nowcoder.MyCode.Code08_weilaiNIO;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * author waigo
 * create 2021-08-31 19:26
 */
public class Main2 {
    public static class ListNode {
        int val;
        ListNode next = null;

        public ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode remove_duplicate(ListNode head) {
        //遍历一遍将每个数出现次数记录一下
        HashMap<Integer,Integer> occurTimes = new HashMap<>();
        ListNode temp = head;
        while(temp!=null){
            occurTimes.put(temp.val,occurTimes.getOrDefault(temp.val,0)+1);
            temp = temp.next;
        }
        //遍历哈希表，将重复的删掉
        occurTimes.entrySet().removeIf(cur -> cur.getValue() > 1);
        //遍历一遍收不重复的答案
        temp = head;
        ListNode res = new ListNode(0);
        ListNode tail = res;
        while(temp!=null){
            if(occurTimes.containsKey(temp.val)){
                tail.next = temp;
                tail = temp;
            }
            temp = temp.next;
        }
        tail.next = null;
        return res.next;
    }
    //题目有点问题，原来重复是要算一次，而不是全部去掉
    //将链表中有的进set
    public ListNode remove_duplicate1(ListNode head) {
        ListNode temp = head;
        ListNode res = new ListNode(0);
        ListNode tail = res;
        HashSet<Integer> set = new HashSet<>();
        while(temp!=null){
            if(!set.contains(temp.val)){
                tail.next = temp;
                tail = temp;
                set.add(temp.val);
            }
            temp = temp.next;
        }
        tail.next = null;
        return res.next;
    }
    public static void main(String[] args) {
        ListNode a = new ListNode(3);
        a.next = new ListNode(2);
        a.next.next = new ListNode(1);
        a.next.next.next = new ListNode(4);
        a.next.next.next.next = new ListNode(1);
        ListNode listNode = new Main2().remove_duplicate1(a);

    }
}
