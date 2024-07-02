package waigo.zuoshen.basic.MyCode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author waigo
 * create 2021-02-22 8:20
 */
public class Code04_SerializeAndReconstructTree {
    public static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    //想要能够恢复一模一样的树，必须用Null填满所有空位，就是null从下面包裹整个树，若不是这样恢复不了结构
    public static Queue<String> preSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        pres(head, ans);
        return ans;
    }

    public static void pres(Node head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            ans.add(String.valueOf(head.value));
            pres(head.left, ans);
            pres(head.right, ans);
        }
    }

    public static boolean isPresEql(Node head1, Node head2) {
        if (head1 == head2) return true;
        if (head1 != null && head2 != null && head1.value == head2.value) {
            if (isPresEql(head1.left, head2.left)) {
                return isPresEql(head1.right, head2.right);
            }
            return false;
        }
        return false;
    }

    //咋序列化就咋反序列化,队列这个弹出如果没有元素了就会返回Null，不会报错
    public static Node buildTreeByPresQueue(Queue<String> presQueue) {
        Node head = generalNodeByString(presQueue.poll());
        if (head != null) {
            head.left = buildTreeByPresQueue(presQueue);
            head.right = buildTreeByPresQueue(presQueue);
        }
        return head;
    }

    public static Queue<String> inSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        ins(head, ans);
        return ans;
    }

    public static void ins(Node head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            ins(head.left, ans);
            ans.add(String.valueOf(head.value));
            ins(head.right, ans);

        }
    }

    public static Queue<String> posSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        pos(head, ans);
        return ans;
    }

    private static void pos(Node head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            pos(head.left, ans);
            pos(head.right, ans);
            ans.add(String.valueOf(head.value));
        }
    }

    public static Queue<String> levelSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        levels(head, ans);
        return ans;
    }

    private static void levels(Node head, Queue<String> ans) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            head = queue.poll();
            ans.add(generalNodeString(head));
            if (head != null) {
                queue.add(head.left);
                queue.add(head.right);
            }
        }
    }

    public static Node buildTreeByLevelSerial(Queue<String> treeList) {
        Queue<Node> queue = new LinkedList<>();
        Node head = generalNodeByString(treeList.poll());
        queue.add(head);
        Node cur;
        while (!queue.isEmpty()) {
            cur = queue.poll();
            Node left = generalNodeByString(treeList.poll());
            Node right = generalNodeByString(treeList.poll());
            cur.left = left;
            cur.right = right;
            if (left != null) queue.add(left);
            if (right != null) queue.add(right);
        }
        return head;
    }

    private static String generalNodeString(Node node) {
        return node == null ? null : String.valueOf(node.value);
    }

    private static Node generalNodeByString(String nodeVal) {
        return nodeVal == null ? null : new Node(Integer.valueOf(nodeVal));
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);

        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);
        Queue<String> queue = preSerial(head);
        for (String next : queue) {
            System.out.print(next + "-");
        }
        if (!isPresEql(buildTreeByPresQueue(queue), head)) {
            System.out.print("\noops!!!");
        } else {
            System.out.print("\nfinish!!!");
        }
        System.out.println("\n++++++++++++++++++++++");
        Queue<String> queue1 = inSerial(head);
        for (String next : queue1) {
            System.out.print(next + "-");
        }
        System.out.println("\n++++++++++++++++++++++");
        Queue<String> queue2 = posSerial(head);
        for (String next : queue2) {
            System.out.print(next + "-");
        }
        System.out.println("\n++++++++++++++++++++++");
        Queue<String> queue3 = levelSerial(head);
        System.out.println("queue3:");
        for (String next : queue3) {
            System.out.print(next + "-");
        }
        if(!isPresEql(buildTreeByLevelSerial(queue3),head)){
            System.out.print("\noops!!!");
        }else{
            System.out.print("\nfinish!!!");
        }
    }
}
/*
* 先序和层序可以完成序列化和反序列化，但是中序和后序无法完成反序列化
* */
