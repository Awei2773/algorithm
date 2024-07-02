package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-19 18:34
 */
public class Code05_FindFirstIntersectNode {
    public static class Node {
        private int value;
        private Node next;

        @Override
        public String toString() {
            return "Node{" + "value=" + value + '}';
        }

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node checkIsLoop(Node head) {
        if (head == null) return null;
        Node slow = head;
        Node fast = head;
        boolean isLoop = false;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                isLoop = true;
                break;
            }
        }
        if (!isLoop) return null;
        //如果相遇了表示有环且slow和fast处于相遇点
        fast = head;
        while (fast != slow) {
            slow = slow.next;
            fast = fast.next;
        }
        //必定相遇在入环点
        return slow;
    }

    /**
     * 返回两个单链表相交的点，不相交则返回null
     *
     * @param head1
     * @param head2
     * @return
     */
    public static Node getIntersectNode(Node head1, Node head2) {
        if (head1 == null || head2 == null) return null;
        Node loopEntry1 = checkIsLoop(head1);
        Node loopEntry2 = checkIsLoop(head2);
        if (loopEntry1 == null && loopEntry2 == null) {
            return noLoop(head1, head2);
        }
        //若是都有环且还想要有交点(必然共一个环)，那环必定是在交点之后
        //但是考虑到可能会有共有一个环且入环点不同的情况，所以入环点是否相同不能绝对的作为划分是否相交的条件
        //但是入环点相同必然有交点，交点在入环点或者在其之前
        if (loopEntry2 != null && loopEntry1 != null) {
            if (loopEntry1 == loopEntry2) {
                return bothLoop1(head1, head2, loopEntry1);
            } else {
                return bothLoop2(head1, head2, loopEntry1, loopEntry2);
            }
        }
        return null;//只有一个有环必不会有交点，返回null
    }

    /*
        入环点相同:必然有交点，在入环点或者在入环点之前
        找交点方式和之前那种noLoop的方式一样，由于交点后它们的长度都相同，所以长的先走个长度差然后短的也开始走就
        会在交点相遇
     */
    private static Node bothLoop1(Node head1, Node head2, Node loopEntry) {
        Node head1Help = head1,head2Help = head2;
        int i = 0;
        while(head1Help!=loopEntry){
            head1Help = head1Help.next;
            i++;
        }
        //i为head1到入环点的距离
        while (head2Help!=loopEntry){
            head2Help = head2Help.next;
            i--;
        }
        //i为距离差
        return getCrossFrontLoop(head1,head2,i);
    }
    /*
        入环点不同
     */

    /**
     * @param loopEntry1 链表1的入环点
     * @param loopEntry2 链表2的入环点
     * @return
     */
    private static Node bothLoop2(Node head1, Node head2, Node loopEntry1, Node loopEntry2) {
        //让一个入环点开始走，若是在走回自己之前没碰到另一个入环点表示它们不是公用一个环
        Node loopEntry2Help = loopEntry2.next;
        while (loopEntry2Help != loopEntry2) {
            if (loopEntry2Help == loopEntry1) {
                //表示共用了一个环
                return loopEntry1;//随便返回一个入环点都是第一个相交点
            }
            loopEntry2Help = loopEntry2Help.next;
        }
        return null;
    }

    private static Node noLoop(Node head1, Node head2) {
        Node head1Help = head1;
        int i = 0;
        while (head1Help.next != null) {
            head1Help = head1Help.next;
            i++;
        }
        //head1Help到链表1的结尾，然后i记录了链表的长度
        Node head2Help = head2;
        while (head2Help.next != null) {
            head2Help = head2Help.next;
            i--;
        }
        if (head1Help != head2Help) return null;//两个不同结尾肯定没交点
        //head2Help到链表2的结尾，然后i的绝对值表示两个链表的长度差，i为负表示链表2长
        return getCrossFrontLoop(head1, head2, i);
    }

    /**
     * 返回两个链表在成环之前相交情况下的交点
     *
     * @param head1
     * @param head2
     * @param i     head1的长度减去head2的长度
     * @return
     */
    private static Node getCrossFrontLoop(Node head1, Node head2, int i) {
        Node head1Help;
        Node head2Help;
        head1Help = head1;
        head2Help = head2;
        if (i < 0) {
            //链表2先走i绝对值步
            for (int j = 0; j < Math.abs(i); j++)
                head2Help = head2Help.next;
        } else {
            //链表1先走i绝对值步
            for (int j = 0; j < Math.abs(i); j++)
                head1Help = head1Help.next;
        }
        //同时走到相遇即为交点
        while (head1Help != head2Help) {
            head1Help = head1Help.next;
            head2Help = head2Help.next;
        }
        return head1Help;
    }

//    public static void main(String[] args) {
//        Node n1 = new Node(1);
//        Node n2 = new Node(2);
//        Node n3 = new Node(3);
//        Node n4 = new Node(4);
//        n1.next = n2;
//        n2.next = n3;
//        n3.next = n4;
//        n4.next = n2;
//        System.out.println(checkIsLoop(n1));
//    }
    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(getIntersectNode(head1, head2).value);

        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);
    }
}
