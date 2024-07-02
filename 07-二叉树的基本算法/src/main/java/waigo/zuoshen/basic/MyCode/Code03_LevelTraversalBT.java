package waigo.zuoshen.basic.MyCode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * author waigo
 * create 2021-02-22 7:56
 */
public class Code03_LevelTraversalBT {
    public static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    //层序遍历，层序是先上后下，一层一层的来，所以不能用栈，每层左边的先处理，所以使用队列
    public static void levelTraversal(Node head) {
        if (head != null) {
            Queue<Node> queue = new LinkedList<>();
            queue.add(head);
            while (!queue.isEmpty()) {
                head = queue.poll();
                System.out.print(head.value + "-");
                if (head.left != null) {
                    queue.add(head.left);
                }
                if (head.right != null) {
                    queue.add(head.right);
                }
            }
        }
    }

    //    面试题：找出最宽的那一层的数量
    //使用hashmap实现
    public static int getMaxWidthLevel(Node head) {
        if (head == null) return 0;
        int curLevel = 0;
        int curLevelNodes = 0;
        int max = 0;
        HashMap<Node, Integer> nodeToLevel = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        nodeToLevel.put(head, 0);
        while (!queue.isEmpty()) {
            head = queue.poll();
            Integer curNodeLevel = nodeToLevel.get(head);
            if (head.left != null) {
                queue.add(head.left);
                nodeToLevel.put(head.left, curNodeLevel + 1);
            }
            if (head.right != null) {
                queue.add(head.right);
                nodeToLevel.put(head.right, curNodeLevel + 1);
            }
            //结算一下宽度
            if (curLevel != curNodeLevel) {
                //新层开始了
                max = Math.max(max, curLevelNodes);
                curLevel++;
                curLevelNodes = 0;//重置curLevelNodes
            }
            //还是旧层
            curLevelNodes++;
        }
        //最后结算一次
        return Math.max(max, curLevelNodes);
    }

    //不使用Map，其实不用记到了第几层了，只要拿到最宽层就完事了
    public static int getMaxWidthLevel1(Node head) {
        if (head == null) return 0;
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        Node curEnd = head;
        Node nextEnd = null;
        int curLevelNodes = 0;
        int max = 0;
        while (!queue.isEmpty()) {
            head = queue.poll();
            if (head.left != null) {
                queue.add(head.left);
                nextEnd = head.left;
            }
            if (head.right != null) {
                queue.add(head.right);
                nextEnd = head.right;
            }
            curLevelNodes++;
            if (curEnd == head) {//到本层结束了，结算
                max = Math.max(max, curLevelNodes);
                curEnd = nextEnd;
                curLevelNodes = 0;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.left.left = new Node(14);
        head.right.right = new Node(7);
        head.right.right.right = new Node(17);
        head.right.right.left = new Node(27);
        System.out.println("========");
        levelTraversal(head);
        System.out.println();
        System.out.println(getMaxWidthLevel(head));
        System.out.println(getMaxWidthLevel1(head));
    }
}
