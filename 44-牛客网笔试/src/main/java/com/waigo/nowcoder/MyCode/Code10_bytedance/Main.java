package com.waigo.nowcoder.MyCode.Code10_bytedance;

/**
 * author waigo
 * create 2021-09-08 16:33
 */
public class Main {

    static class ListNode {
        private int data;// 数据域
        public ListNode next;// 指针域

        public ListNode(int data) {
            this.data = data;
        }

        public int getData() {
            return data;
        }

        public void setData(int Data) {
            this.data = Data;
        }

        public ListNode getNext() {
            return next;
        }

        public void setNext(ListNode Next) {
            this.next = Next;
        }
    }
    //每隔K个进行一次链表反转，最后不足k个也进行一次反转
    static ListNode reverseK(ListNode head, int k) {
        if(k<=0) return head;
        ListNode res = new ListNode(0);//哑节点
        ListNode tail = res;
        ListNode partHead = head;
        while(partHead!=null){
            //走k步拆出一段
            ListNode temp = partHead;
            for(int i = 0;i<k-1&&temp!=null;i++){
                temp = temp.next;
            }
            ListNode next = temp!=null?temp.next:null;
            if(temp!=null){
                temp.next = null;//断开和下一截的连接
            }
            tail.next = reverseList(partHead);
            //插入结果链表
            while(tail.next!=null){
                tail = tail.next;
            }
            partHead = next;
        }
        return res.next;
    }

    private static ListNode reverseList(ListNode head) {
        ListNode pre = null,next;
        while(head!=null){
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    public static void printListNodes(ListNode head) {
        // 打印反转后的结果
        while (null != head) {
            System.out.print(head.getData() + " ");
            head = head.getNext();
        }
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode pre = head;
        for (int i = 3; i < 5; i++) {
            ListNode ListNode = new ListNode(i);
            pre.next = ListNode;
            pre = ListNode;
        }
        printListNodes(head);
        ListNode result = reverseK(head, 1);
        System.out.println();
        printListNodes(result);
    }
}
