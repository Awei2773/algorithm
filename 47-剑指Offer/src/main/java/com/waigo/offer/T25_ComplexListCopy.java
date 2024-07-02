package com.waigo.offer;

/**
 * author waigo
 * create 2021-09-18 22:37
 */

public class T25_ComplexListCopy {
    static class RandomListNode {
        int label;
        RandomListNode next = null;
        RandomListNode random = null;

        RandomListNode(int label) {
            this.label = label;
        }
    }
    public static RandomListNode Clone(RandomListNode pHead) {
        //先将复制的链表节点每个都放在原节点的后一个，为了之后连random指针提供方便
        RandomListNode tmp = pHead;
        while(tmp!=null){
            RandomListNode newNode = new RandomListNode(tmp.label);
            newNode.next = tmp.next;
            tmp.next = newNode;
            tmp = newNode.next;
        }
        //再遍历一遍，连好random指针
        tmp = pHead;
        while(tmp!=null){
            tmp.next.random = (tmp.random == null?null:tmp.random.next);
            tmp = tmp.next.next;
        }
        //将新链表拆出来
        RandomListNode res = new RandomListNode(0);
        RandomListNode tail = res;
        tmp = pHead;
        while(tmp!=null){
            tail.next = tmp.next;
            tmp = tmp.next.next;
            tail = tail.next;
            tail.next = null;
        }
        return res.next;
    }

    public static void main(String[] args) {
        RandomListNode randomListNode = new RandomListNode(0);
        RandomListNode next1 = new RandomListNode(1);
        randomListNode.random = next1;
        randomListNode.next = next1;
        randomListNode.next.next = new RandomListNode(2);
        Clone(randomListNode);
    }
}



