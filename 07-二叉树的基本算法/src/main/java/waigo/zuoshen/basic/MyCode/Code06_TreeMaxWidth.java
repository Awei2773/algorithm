package waigo.zuoshen.basic.MyCode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * author waigo
 * create 2021-02-22 19:48
 */
//求二叉树最宽的层有多少个节点
//使用层序遍历，每一层的宽度都抓一下抓到最大的宽度
public class Code06_TreeMaxWidth {
    public static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static int maxWidthUseMap(Node head) {
        if (head == null) return 0;
        HashMap<Node, Integer> nodeMapToLevel = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        int curLevel = 1;
        int curLevelNodes = 0;
        int max = 0;
        nodeMapToLevel.put(head, 1);
        while (!queue.isEmpty()) {
            head = queue.poll();
            Integer headLevel = nodeMapToLevel.get(head);
            if (head.left != null) {
                queue.add(head.left);
                nodeMapToLevel.put(head.left, headLevel + 1);
            }
            if (head.right != null) {
                queue.add(head.right);
                nodeMapToLevel.put(head.right, headLevel + 1);
            }
            if (headLevel != curLevel) {
                max = Math.max(curLevelNodes, max);
                curLevelNodes = 0;
                curLevel = headLevel;
            }
            curLevelNodes++;

        }
        return Math.max(curLevelNodes,max);
    }

    public static int maxWidthNoMap(Node head) {
        if (head == null) return 0;
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        Node curEnd = head;
        Node nextEnd = null;
        int max = 0;
        int curLevelNodes = 0;
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
            if (head == curEnd) {
                curEnd = nextEnd;
                max = Math.max(max, curLevelNodes);
                curLevelNodes = 0;
            }
        }
        return max;
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) return null;
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level+1, maxLevel, maxValue);
        head.right = generate(level+1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxWidthUseMap(head) != maxWidthNoMap(head)) {
                System.out.println("Oops!");
                return;
            }
        }
        System.out.println("finish!");

    }
}
