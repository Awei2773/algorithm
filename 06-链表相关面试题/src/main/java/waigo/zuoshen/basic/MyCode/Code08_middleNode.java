package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-04-03 23:38
 */
/*
给定一个头结点为 head 的非空单链表，返回链表的中间结点。
如果有两个中间结点，则返回第二个中间结点。
*/

@SuppressWarnings("Duplicates")
public class Code08_middleNode {
    public static class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }
    //下中点或者中点
    public static ListNode middleOrDMiddleNode(ListNode head) {
        if(head==null||head.next==null) return head;
        ListNode fast = head;
        ListNode slow = head;
        while (fast!=null&&fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
    //上中点或者中点
    public static ListNode middleOrUpMiddleNode(ListNode head) {
        if(head==null||head.next==null) return head;
        ListNode fast = head.next;//让slow少走一步
        ListNode slow = head;
        while (fast!=null&&fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
    //对数器
    public static ListNode generalRandomList(int maxLen,int maxVal){
        int len = (int) (Math.random()*maxLen)+1;
        ListNode head = new ListNode((int) (Math.random()*maxVal));
        for(int i = 1;i<len;i++)
            head.next = new ListNode((int) (Math.random()*maxVal));
        return head;
    }

}
