package waigo.zuoshen.basic.MyCode;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-18 20:15
 */
@SuppressWarnings("Duplicates")
public class Code01_LinkedListMid {
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

    public static Node generalRandomLinkedList(int maxLen, int maxValue) {
        Node head = null;
        int len = (int) (Math.random() * maxLen);//0~maxLen-1
        while (len-- > 0) {
            Node node = new Node((int) (Math.random() * maxValue));
            if (head == null) {
                head = node;
            } else {
                node.next = head.next;
                head.next = node;
            }
        }
        return head;
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

    //时间复杂度O(N),空间复杂度O(N)
    //1）输入链表头节点，奇数长度返回中点，偶数长度返回上中点
    public static Node getUpMidNode(Node head) {
        if (head == null) return null;
        Node temp = head;
        ArrayList<Node> list = new ArrayList<>();
        while (temp != null) {
            list.add(temp);
            temp = temp.next;//temp move
        }
        return list.get((list.size() - 1) / 2);
    }

    //使用快慢指针，优化常数项时间和降低空间复杂度
    //快慢指针:快指针每次走两步，慢指针每次走一步，快指针到尾的时候，慢指针就到了中间附近
    public static Node getUpMidNode1(Node head) {
        if (head == null) return null;
        Node slow = head;
        Node fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    //2）输入链表头节点，奇数长度返回中点，偶数长度返回下中点
    public static Node getDownMidNode(Node head) {
        if (head == null) return null;
        Node temp = head;
        ArrayList<Node> list = new ArrayList<>();
        while (temp != null) {
            list.add(temp);
            temp = temp.next;//temp move
        }
        return list.get(list.size() / 2);
    }

    public static Node getDownMidNode1(Node head) {
        if (head == null) return null;
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    //3）输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
    public static Node getPreUpMidNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) return null;
        Node temp = head;
        ArrayList<Node> list = new ArrayList<>();
        while (temp != null) {
            list.add(temp);
            temp = temp.next;//temp move
        }
        return list.get(((list.size() - 1) / 2) - 1);
    }

    public static Node getPreUpMidNode1(Node head) {
        if (head == null || head.next == null || head.next.next == null) return null;
        Node slow = head;
        Node fast = head.next.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    //4）输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
    public static Node getPreDownMidNode(Node head) {
        if (head == null || head.next == null) return null;
        Node temp = head;
        ArrayList<Node> list = new ArrayList<>();
        while (temp != null) {
            list.add(temp);
            temp = temp.next;//temp move
        }
        return list.get((list.size() / 2) - 1);
    }

    public static Node getPreDownMidNode1(Node head) {
        if (head == null || head.next == null) return null;
        Node slow = head;
        Node fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    //for test
    public static void main(String[] args) {
        int maxLen = 10, maxValue = 10, times = 10000000;

        int i = 0;
        for (; i < times; i++) {
            Node node = generalRandomLinkedList(maxLen, maxValue);
            double decide = Math.random();
            if (decide < 0.25) {
                if (getUpMidNode(node) != getUpMidNode1(node)) {
                    Logger.getGlobal().info("oops!!!");
                    printList(node);
                    Logger.getGlobal().info("getUpMidNode:" + getUpMidNode(node));
                    Logger.getGlobal().info("getUpMidNode1:" + getUpMidNode1(node));
                    break;
                }
            } else if (decide < 0.5) {
                if (getDownMidNode(node) != getDownMidNode1(node)) {
                    Logger.getGlobal().info("oops!!!");
                    printList(node);
                    Logger.getGlobal().info("getDownMidNode:" + getDownMidNode(node));
                    Logger.getGlobal().info("getDownMidNode1:" + getDownMidNode1(node));
                    break;
                }
            } else if (decide < 0.75) {
                if (getPreUpMidNode(node) != getPreUpMidNode1(node)) {
                    Logger.getGlobal().info("oops!!!");
                    printList(node);
                    Logger.getGlobal().info("getPreUpMidNode:" + getPreUpMidNode(node));
                    Logger.getGlobal().info("getPreUpMidNode1:" + getPreUpMidNode1(node));
                    break;
                }
            } else {
                if (getPreDownMidNode1(node) != getPreDownMidNode(node)) {
                    Logger.getGlobal().info("oops!!!");
                    printList(node);
                    Logger.getGlobal().info("getPreDownMidNode:" + getPreDownMidNode(node));
                    Logger.getGlobal().info("getPreDownMidNode1:" + getPreDownMidNode1(node));
                    break;
                }
            }
        }
        Logger.getGlobal().info(i >= times ? "finish!!!" : "fucking");

    }
}
