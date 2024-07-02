package waigo.zuoshen.basic.MyCode;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-24 8:55
 */
/*
给定一棵二叉树的头节点head，
返回这颗二叉树中最大的二叉搜索子树的大小

思考：当前这棵树(任意的子树)是否是二叉搜索树
1）不是：
    1.1）左边右边的二叉树搜索子树最大值
2）是：
    2.1）那么最大值就是所有节点
    信息：1）子树的节点数
         2）子树是不是二叉搜索树
         3）子树的最大值和最小值
         4）子树的最大二叉搜索树大小
*/
public class Code04_MaxSubBSTSize {
    public static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }
    }
    //for test
    //暴力法:直接找到最大的搜索子树，判断一棵树是不是搜索二叉树直接将中序序列放到list中，然后看这个list是不是升序
    public static int getMaxSubBSTSize1(Node head) {
        if (head == null) return 0;
        return process1(head);
    }

    //当前树若是搜索二叉树，那最大的肯定是当前树，不然就得找找看左右树谁的大、
    //返回最大搜索二叉子树
    public static int process1(Node head) {
        if (head == null) return 0;
        int headBSTSize = getBSTSize(head);
        if (headBSTSize != 0) return headBSTSize;
        return Math.max(process1(head.left), process1(head.right));
    }

    public static int getBSTSize(Node head) {
        if (head == null) return 0;
        ArrayList<Node> list = new ArrayList<>();
        in(head, list);
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).value > list.get(i + 1).value) return 0;
        }
        return list.size();
    }

    private static void in(Node head, ArrayList<Node> list) {
        if (head == null) return;
        in(head.left, list);
        list.add(head);
        in(head.right, list);
    }
    //套路法：

    public static int getMaxSubBSTSize2(Node head) {
        if (head == null) return 0;
        return process2(head).maxSubBSTSize;
    }

    private static Info process2(Node head) {
        //n==1
        if (head == null) return null;
        //n==m
        Info leftInfo = process2(head.left);
        Info rightInfo = process2(head.right);
        //n==m+1,加工
        int nodes = (leftInfo == null ? 0 : leftInfo.nodes) + (rightInfo == null ? 0 : rightInfo.nodes) + 1;
        boolean isBST = ((leftInfo == null || (leftInfo.isBST && leftInfo.max <= head.value)) && (rightInfo == null || (rightInfo.isBST && rightInfo.min >= head.value)));
        int max = Math.max(
                    Math.max(
                            leftInfo == null ? Integer.MIN_VALUE : leftInfo.max,
                            rightInfo == null ? Integer.MIN_VALUE : rightInfo.max
                    ),
                    head.value
                  );
        int min = Math.min(Math.min(leftInfo == null ? Integer.MAX_VALUE : leftInfo.min, rightInfo == null ? Integer.MAX_VALUE : rightInfo.min), head.value);
        int maxSubBSTSize = isBST ? nodes : Math.max(leftInfo == null ? Integer.MIN_VALUE : leftInfo.maxSubBSTSize, rightInfo == null ? Integer.MIN_VALUE : rightInfo.maxSubBSTSize);
        return new Info(nodes, isBST, max, min, maxSubBSTSize);
    }

    public static class Info {
        private int nodes;
        private boolean isBST;
        private int max;
        private int min;
        private int maxSubBSTSize;

        public Info(int nodes, boolean isBST, int max, int min, int maxSubSize) {
            this.nodes = nodes;
            this.isBST = isBST;
            this.max = max;
            this.min = min;
            this.maxSubBSTSize = maxSubSize;
        }
    }
    //for test
    public static Node generalRandomTree(int maxFloor, int maxValue) {
        return pre(maxFloor, maxValue, 1);
    }
    //for test
    private static Node pre(int maxFloor, int maxValue, int curFloor) {
        if (curFloor > maxFloor || Math.random() < 0.35) return null;
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = pre(maxFloor, maxValue, curFloor + 1);
        head.right = pre(maxFloor, maxValue, curFloor + 1);
        return head;
    }

    public static void main(String[] args) {
        int maxFloor = 4, maxValue = 100, testTimes = 1000000;
        int i = 0;
        for (; i < testTimes; i++) {
            Node head = generalRandomTree(maxFloor, maxValue);
            if (getMaxSubBSTSize1(head) != getMaxSubBSTSize2(head)) break;
        }
        Logger.getGlobal().info(i == testTimes ? "finish!!!" : "oops!!!");
    }
}
