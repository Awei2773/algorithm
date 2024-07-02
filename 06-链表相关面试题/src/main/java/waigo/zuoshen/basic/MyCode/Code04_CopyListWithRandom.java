package waigo.zuoshen.basic.MyCode;

import java.util.HashMap;

/**
 * author waigo
 * create 2021-02-19 16:52
 */
@SuppressWarnings("Duplicates")
public class Code04_CopyListWithRandom {
    public static class Node {
        private int value;
        private Node next;
        private Node rand;

        public Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" + "value=" + value + '}';
        }
    }

    //use hashmap can make me know the old node pointer point to which new node
    public static Node copyListWithRand1(Node origin) {
        HashMap<Node, Node> oldToNew = new HashMap<>();
        Node cur = origin;
        //复刻所有的节点
        while (cur != null) {
            oldToNew.put(cur, new Node(cur.value));
            cur = cur.next;
        }
        //连接每个新节点,一次就搞定next和rand
        cur = origin;
        while (cur != null) {
            Node newOne = oldToNew.get(cur);
            newOne.next = oldToNew.get(cur.next);
            newOne.rand = cur.rand == null ? null : oldToNew.get(cur.rand);
            cur = cur.next;
        }
        return oldToNew.get(origin);
    }

    //un use extra space
    public static Node copyListWithRand2(Node origin) {
        if (origin == null) return null;
        Node cur = origin;
        Node newNode;
        while (cur != null) {
            newNode = new Node(cur.value);//复刻
            //插入到旧节点后面
            newNode.next = cur.next;
            cur.next = newNode;
            cur = newNode.next;//注意newNode插入了，下一个旧节点在newNode后面
        }
        //复刻完毕，现在弄rand
        cur = origin;
        while (cur != null) {
            //cur.next.rand-->新节点的rand
            //如果旧的rand指向了null就让新的也指向null，否则指向旧的rand指向的节点的新复刻节点
            cur.next.rand = cur.rand == null ? null : cur.rand.next;
            //一次走两步,因为是一比一复刻，所以走两步才能到下一个旧节点
            cur = cur.next.next;
        }
        //分离两条链表
        cur = origin;//链表
        Node cpyHead = cur.next,cpyHelp = cpyHead;
        while (cur != null) {
            cur.next = cpyHelp.next;
            cur = cur.next;//旧链表先走，所以它会先到null
            cpyHelp.next = cur == null ? null : cur.next;
            cpyHelp = cpyHelp.next;
        }
        return cpyHead;
    }

    public static void printRandLinkedList(Node head) {
        Node cur = head;
        System.out.print("order: ");
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println();
        cur = head;
        System.out.print("rand:  ");
        while (cur != null) {
            System.out.print(cur.rand == null ? "- " : cur.rand.value + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = null;
        Node res1 = null;
        Node res2 = null;
        printRandLinkedList(head);
        res1 = copyListWithRand1(head);
        printRandLinkedList(res1);
        res2 = copyListWithRand2(head);
        printRandLinkedList(res2);
        printRandLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);

        head.rand = head.next.next.next.next.next; // 1 -> 6
        head.next.rand = head.next.next.next.next.next; // 2 -> 6
        head.next.next.rand = head.next.next.next.next; // 3 -> 5
        head.next.next.next.rand = head.next.next; // 4 -> 3
        head.next.next.next.next.rand = null; // 5 -> null
        head.next.next.next.next.next.rand = head.next.next.next; // 6 -> 4

        printRandLinkedList(head);
        res1 = copyListWithRand1(head);
        printRandLinkedList(res1);
        res2 = copyListWithRand2(head);
        printRandLinkedList(res2);
        printRandLinkedList(head);
        System.out.println("=========================");

    }
}
