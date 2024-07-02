package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-04-03 7:43
 */
/*
输入两个递增排序的链表，合并这两个链表并使新链表中的节点仍然是递增排序的。

示例1：

输入：1->2->4, 1->3->4
输出：1->1->2->3->4->4
*/
public class Code07_Merge2SortedList {
    public static class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }
  //这两条链表不能有环或者相交
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1==l2) return l1;//两个为空或者根本就是一条链表
        if(l1==null||l2==null) return l1==null?l2:l1;//一个为空，直接返回没空那个链表
        ListNode newHead,tail;
        if(l1.val<=l2.val){
            newHead = l1;
            l1 = l1.next;
        }else{
            newHead = l2;
            l2 = l2.next;
        }
        newHead.next = null;
        tail = newHead;
        //新链表的初始化完成
        while (l1!=null&&l2!=null){
            if(l1.val<=l2.val){
                tail.next = l1;
                l1 = l1.next;
            }else{
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next;
            tail.next = null;//其实这里没必要将新的链表从原链表中断开，因为两个链表都是无环的，最后都会指向空，所以新链表不会出现回环
        }
        while (l1!=null){//将这个l1的头拆下来装到新链表尾巴处
            tail.next = l1;
            l1 = l1.next;
            tail = tail.next;
            tail.next = null;
        }
        while (l2!=null){//将这个l2的头拆下来装到新链表尾巴处
            tail.next = l2;
            l2 = l2.next;
            tail = tail.next;
            tail.next = null;
        }
        return newHead;
    }
    //找到自己这个时候的头，然后剩下的递归
    public static ListNode mergeTwoLists1(ListNode l1, ListNode l2) {
        if(l1==l2) return l1;//两个为空或者根本就是一条链表
        if(l1==null||l2==null) return l1==null?l2:l1;//一个为空，直接返回没空那个链表
        ListNode newHead;
        if(l1.val<=l2.val){
            newHead = l1;
            l1 = l1.next;
        }else{
            newHead = l2;
            l2 = l2.next;
        }
        newHead.next = mergeTwoLists1(l1,l2);
        return newHead;
    }

    public static void main(String[] args) {
        ListNode a = new ListNode(1);
        a.next = new ListNode(3);
        a.next.next = new ListNode(4);
        ListNode b = new ListNode(2);
        b.next = new ListNode(4);
        b.next.next = new ListNode(5);
        ListNode listNode = mergeTwoLists1(a, b);
        while (listNode!=null){
            System.out.print(listNode.val+" ");
            listNode = listNode.next;
        }
    }
}
