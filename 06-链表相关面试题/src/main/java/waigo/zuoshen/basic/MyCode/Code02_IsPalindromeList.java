package waigo.zuoshen.basic.MyCode;

import java.util.Stack;
import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-18 23:09
 */
@SuppressWarnings("Duplicates")
public class Code02_IsPalindromeList {
    public static class Node {
        private int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" + "value=" + value + '}';
        }
    }

    // need n extra space
    public static boolean isPalindrome1(Node head) {
        if (head == null) return false;
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        //栈已经存了一遍链表，倒出为逆序
        cur = head;
        while (!stack.isEmpty()) {
            if (stack.pop().value != cur.value) return false;
            cur = cur.next;
        }
        return true;
    }

    // need n/2 extra space
    public static boolean isPalindrome2(Node head) {
        if(head==null) return false;
        //先找到链表的奇数长度的中点，或者偶数长度的下中点
        Node midOrDownMid = getMidOrDownMid(head);
        Stack<Node> stack = new Stack<>();
        while (midOrDownMid != null) {
            stack.push(midOrDownMid);
            midOrDownMid = midOrDownMid.next;
        }
        //将栈中那一半倒出来比对
        midOrDownMid = head;//为了少用个变量就将midOrDownMid指针重新赋值了
        while (!stack.isEmpty()) {
            if (stack.pop().value != midOrDownMid.value) return false;
            midOrDownMid = midOrDownMid.next;//midOrDownMid move
        }
        return true;
    }
    //need O(1) extra space
    public static boolean isPalindrome3(Node head) {
        if(head == null) return false;
        Node midOrDownMid = getMidOrDownMid(head);
        //将后半段逆序,midOrDownMid已经是后半段的开始了
        Node secondHead = reverseList(midOrDownMid);
        Node secHelp = secondHead,firHelp = head;
        boolean succeed = true;
        while (secHelp!=null&&firHelp!=null){//偶数个的时候右边会先到next为空这里
            if(firHelp.value!=secHelp.value) {
                succeed = false;
                break;
            }
            firHelp = firHelp.next;//firHelp move
            secHelp = secHelp.next;//secHelp move
        }
        //将第二段复原
        reverseList(secondHead);
        return succeed;
    }
    public static void printList(Node head) {
        Node temp = head;
        StringBuilder builder = new StringBuilder("[");
        while (temp != null) {
            builder.append(temp);
            builder.append(",");
            temp = temp.next;
        }
        if (builder.length() > 1) {
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append("]");
        Logger.getGlobal().info(builder.toString());
    }
    public static Node reverseList(Node head) {
        if(head==null) return null;
        Node pre = null,next;
        while(head!=null){
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    public static Node getMidOrDownMid(Node head) {
        if (head == null) return null;
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static void main(String[] args) {
        Node head = null;
        printList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printList(head);
        System.out.println("=========================");

        head = new Node(1);
        printList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        printList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(1);
        printList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        printList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(1);
        printList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(1);
        printList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(2);
        head.next.next.next = new Node(1);
        printList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(2);
        head.next.next.next.next = new Node(1);
        printList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printList(head);
        System.out.println("=========================");
    }
}
