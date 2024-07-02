package com.waigo.nowcoder.MyCode.Code10_tx;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * author waigo
 * create 2021-09-05 19:58
 */


public class Main1 {
    public static class ListNode {
        int val;
        ListNode next = null;

        public ListNode(int val) {
            this.val = val;
        }
    }
    //模拟法
    //1.遍历，碰上链表为空就跳过
    public ListNode solve(ListNode[] a) {
        ListNode res = new ListNode(0);
        if(a==null||a.length==0) return res.next;
        ListNode tail = res;
        //记录一下当前不为空的链表
        Set<Integer> notNull = new HashSet<>();
        for(int i = 0;i<a.length;i++){
            if(a[i]!=null){
                notNull.add(i);
            }
        }
        while(notNull.size()>0){
            Iterator<Integer> iterator = notNull.iterator();
            while(iterator.hasNext()){
                int i = iterator.next();
                tail.next = a[i];
                tail = tail.next;
                a[i] = a[i].next;
                tail.next = null;
                if(a[i]==null){
                    iterator.remove();
                }
            }
        }
        return res.next;
    }

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(3);
        ListNode listNode1 = new ListNode(2);
        new Main1().solve(new ListNode[]{listNode,null,listNode1});
    }
}
