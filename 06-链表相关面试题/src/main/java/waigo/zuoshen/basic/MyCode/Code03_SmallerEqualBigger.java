package waigo.zuoshen.basic.MyCode;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-19 11:41
 */
/*
将单向链表按某值划分成左边小、中间相等、右边大的形式

1）把链表放入数组里，在数组上做partition（笔试用）

2）分成小、中、大三部分，再把各个部分之间串起来（面试用）
*/
@SuppressWarnings("Duplicates")
public class Code03_SmallerEqualBigger {
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

    public static void printList(Node head, String info) {
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
        Logger.getGlobal().info(info + builder.toString());
    }

    public static Node generalRandomList(int maxLen, int maxValue) {
        if (maxLen <= 0) return null;
        Node head = null;
        while (maxLen-- > 0) {
            Node newNode = new Node((int) (Math.random() * maxLen) - (int) (Math.random() * maxLen));
            if (head == null) {
                head = newNode;
            } else {
                newNode.next = head.next;
                head.next = newNode;
            }
        }
        return head;
    }

    /*
    将单向链表按某值划分成左边小、中间相等、右边大的形式(需要换头的问题)
    这就是在链表上玩荷兰国旗的问题，笔试的时候可以将链表中的元素全放到数组中，然后在数组中玩荷兰国旗
    这种方法时间复杂度可以O(N),但是需要O(N)的空间复杂度
    */
    public static Node listPartition1(Node head, int pivot) {
        if (head == null || head.next == null) return head;
        ArrayList<Node> list = new ArrayList<>();
        /**
         * 插一个ArrayList的知识点，通过看源码得知，ArrayList在使用remove()方法的时候
         * 将指定位置的元素删除，然后在ArrayList维护的数组中放在指定位置后面的元素复制
         * 到前面来，比如说[1,2,3,4]删除了下标2的元素，它会将4复制过来变成[1,2,4],然后
         * 让原来3下标处指向元素4的指针指向null
         */
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        //荷兰国旗
        int smallTop = -1, bigLow = list.size();//这里提供了pivot，不需要用最后一个做pivot
        for (int i = 0; i < bigLow; i++) {
            if (list.get(i).value < pivot) {
                swap(list, ++smallTop, i);//检查下一个
            } else if (list.get(i).value > pivot) {
                swap(list, --bigLow, i--);//留在原地，从高处来的还没看过
            }
        }
        //已经完成荷兰国旗了，现在要拼成链表
        head = list.get(0);
        Node cur = head;
        for (int i = 1; i < list.size(); i++) {
            cur.next = list.get(i);
            cur = cur.next;
        }
        //cur指向了最后一个节点，这里一定要将最后一个节点的next指向null不然可能出现回环，会出问题的
        //我在尝试打印链表的时候因为有回环，所以不断的添加已经添加过的节点，然后堆就溢出了
        cur.next = null;
        return head;
    }

    public static <T> void swap(ArrayList<T> list, int aIdx, int bIdx) {
        T temp = list.get(aIdx);
        list.set(aIdx, list.get(bIdx));
        list.set(bIdx, temp);
    }

    /*笔试的时候，除了追求时间复杂度之外，还要尽量减少空间复杂度，
      思路：使用三条链表，一个小于，一个等于，一个大于
      分别将所有的元素通过尾插法插入到这三条链表中然后再将它们三个连起来就可以了
    */
    public static Node listPartition2(Node head, int pivot) {
        if (head == null || head.next == null) return head;
        Node sH = null, sT = null, eH = null, eT = null, bH = null, bT = null;
        Node next;
        while (head != null) {
            next = head.next;
            head.next = null;
            if (head.value < pivot) {
                //插入小于链表
                if (sH == null) {
                    sH = head;
                    sT = head;
                } else {
                    sT.next = head;
                    sT = head;
                }
            } else if (head.value > pivot) {
                if (bH == null) {
                    bH = head;
                    bT = head;
                } else {
                    bT.next = head;
                    bT = head;
                }
            } else {
                if (eH == null) {
                    eH = head;
                    eT = head;
                } else {
                    eT.next = head;
                    eT = head;
                }
            }
            head = next;//head move
        }
        //将繁杂的判断条件化简的关键点是，我们只需要将三个链表连起来，而不需要知道接口处都是什么节点
        if (sH != null) {
            sT.next = eH;
            eT = eT == null ? sT : eT;
        }
        //不管上面执行没有eT都是现在需要去判断的点
        if (eT != null) {
            eT.next = bH;
        }
        //到了这里都不知道谁是尾了，而且都保证不了是否为null，所以在前面需要将每个节点拆出来的时候next都设置为Null,
        //防止出现之前的回环问题
        return sH == null ? (eH == null ? bH : eH) : sH;

    }

    public static boolean isListEql(Node h1, Node h2) {
        Node help1 = h1, help2 = h2;
        while (help1 != null && help2 != null) {
            if (help1.value != help2.value) return false;
            help1 = help1.next;
            help2 = help2.next;
        }
        return help1 == help2;
    }

    public static Node cpyList(Node originHead) {
        if (originHead == null) return null;
        Node head = new Node(originHead.value);
        Node cur1 = head, cur2 = originHead.next;
        while (cur2 != null) {
            cur1.next = new Node(cur2.value);
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return head;
    }

    /*
    特别注意链表问题，人家传头进来如果不是要换头一定不要动人家的头
    做测试的时候没有复制个链表，导致一个头经过一个方法处理之后不再是链表的头了，可能指向原来链表的子链表，
    就不完整了，这使得测试出现了错误，以后要注意，不同测试应该复制一份再做比对。
     */
    public static void main(String[] args) {
        Node head1 = new Node(7);
        head1.next = new Node(9);
        head1.next.next = new Node(1);
        head1.next.next.next = new Node(8);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(2);
        head1.next.next.next.next.next.next = new Node(5);
        printList(head1,"list:");
        // head1 = listPartition1(head1, 4);
        head1 = listPartition2(head1, 5);
        printList(head1,"listPartition2:");
    }
}
