package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-04-04 0:07
 */
/*
给定一个链表，判断链表中是否有环。
如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位
置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
如果链表中存在环，则返回 true 。 否则，返回 false 。
进阶：
你能用 O(1)（即，常量）内存解决此问题吗？
*/
public class Code09_listHasCycle {
    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    //快慢指针，如果两个指针能够相遇表示肯定有环
    public boolean hasCycle(ListNode head) {
        if (head == null) return false;
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) return true;
        }
        return false;
    }
    /*给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
    为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。注意，
    pos 仅仅是用于标识环的情况，并不会作为参数传递到函数中。
    说明：不允许修改给定的链表。
    进阶：
    你是否可以使用 O(1) 空间解决此题？*/
    public ListNode detectCycle(ListNode head) {
        if(head==null) return null;
        ListNode fast = head;
        ListNode slow = head;
        while(fast!=null&&fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast==slow) break;
        }
        if(fast==null||fast.next==null) return null;
        fast = head;
        while (fast!=slow){
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

}
