package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-03-02 22:26
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一棵二叉树的头节点head，
 * 返回这颗二叉树中最大的二叉搜索子树的头节点
 * 二叉搜索树：任意子树都是二叉搜索数，而且任意子树的左子树最大值都小于等于头结点值小于等于右子树最小值
 * <p>
 * 信息：
 * 子树最大二叉搜索树的头结点，左子树最大值，右子树最小值，最大二叉搜索子树的节点数,子树的节点数，
 * 信息整合：
 * 最大二叉搜索子树的头结点，树的最大和最小值
 */
public class Code05_MaxSubBSTHead {
    public static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node generalRandomBinaryTree(int maxLay, int maxVal) {
        if (maxLay <= 0) return null;
        int len = (int) (Math.random() * maxLay) + 1;
        return inCreateBinaryTree(len, maxVal, 1);
    }

    private static Node inCreateBinaryTree(int maxLay, int maxVal, int curFloor) {
        if (curFloor == maxLay + 1 || Math.random() < 0.1) return null;
        Node left = inCreateBinaryTree(maxLay, maxVal, curFloor + 1);
        Node head = new Node((int) (Math.random() * maxVal));
        head.left = left;
        head.right = inCreateBinaryTree(maxLay, maxVal, curFloor + 1);
        return head;
    }

    public static void printBinaryTree(Node head) {
        if (head == null) return;
        insPrint(head, 1, "H");
    }

    private static void insPrint(Node head, int curLay, String type) {
        if (head == null) return;
        insPrint(head.right, curLay + 1, "v");
        System.out.println(formatNodeStr(head, type, curLay));
        insPrint(head.left, curLay + 1, "^");

    }

    private static String formatNodeStr(Node node, String type, int curLay) {
        String str = type + node.value + type;
        int space = (17 - str.length()) / 2;
        return getSpace((curLay - 1) * 15) + getSpace(space) + str;
    }

    private static String getSpace(int len) {
        if (len <= 0) return "";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++)
            builder.append(" ");
        return builder.toString();
    }

    //暴力递归法找到最大搜索子树的头
    public static Node getMaxSubBSTHead_fr(Node head) {
        if (head == null) return null;
        if (getMaxSubBSTSize(head) != 0) return head;
        Node leftAns = getMaxSubBSTHead_fr(head.left);
        Node rightAns = getMaxSubBSTHead_fr(head.right);
        return getMaxSubBSTSize(leftAns) > getMaxSubBSTSize(rightAns) ? leftAns : rightAns;
    }

    public static int getMaxSubBSTSize(Node head) {
        if (head == null) return 0;
        List<Integer> insValList = new ArrayList<>();
        ins(head, insValList);
        for (int i = 0; i < insValList.size() - 1; i++) {
            if (insValList.get(i) > insValList.get(i + 1)) return 0;
        }
        return insValList.size();
    }

    public static class Info {
        private Node maxSubBSTHead;
        private int max;
        private int min;
        private int maxSubBSTNodes;

        public Info(Node maxSubBSTHead, int max, int min, int maxSubBSTNodes) {
            this.maxSubBSTHead = maxSubBSTHead;
            this.max = max;
            this.min = min;
            this.maxSubBSTNodes = maxSubBSTNodes;
        }
    }

    public static Node getMaxSubBSTHead_tl(Node head) {
        if (head == null) return null;
        return process(head).maxSubBSTHead;
    }

    private static Info process(Node head) {
        if (head == null) return null;
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        boolean isBST = ((leftInfo == null || (leftInfo.maxSubBSTHead == head.left && leftInfo.max <= head.value)) && (rightInfo == null || (rightInfo.maxSubBSTHead == head.right && rightInfo.min >= head.value)));
        int max = head.value;
        int min = head.value;
        int maxSubBSTNodes = 0;
        Node maxSubBSTHead = null;
        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
            maxSubBSTHead = leftInfo.maxSubBSTHead;
            maxSubBSTNodes = leftInfo.maxSubBSTNodes;
        }
        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
            if (maxSubBSTNodes <= rightInfo.maxSubBSTNodes) {
                maxSubBSTNodes = rightInfo.maxSubBSTNodes;
                maxSubBSTHead = rightInfo.maxSubBSTHead;
            }
        }
        maxSubBSTNodes = isBST ? (leftInfo == null ? 0 : leftInfo.maxSubBSTNodes) + (rightInfo == null ? 0 : rightInfo.maxSubBSTNodes) + 1 : maxSubBSTNodes;
        maxSubBSTHead = isBST ? head : maxSubBSTHead;
        return new Info(maxSubBSTHead, max, min, maxSubBSTNodes);
    }

    private static void ins(Node head, List<Integer> insSeq) {
        if (head == null) return;
        ins(head.left, insSeq);
        insSeq.add(head.value);
        ins(head.right, insSeq);
    }


    //套路法

    public static void main(String[] args) {
        int maxLay = 5, maxVal = 109999, testTimes = 100000;
        int i = 0;
        for (; i < testTimes; i++) {
            Node head = generalRandomBinaryTree(maxLay, maxVal);
            Node maxSubBSTHead_fr = getMaxSubBSTHead_fr(head);
            Node maxSubBSTHead_tl = getMaxSubBSTHead_tl(head);
            if (maxSubBSTHead_fr != maxSubBSTHead_tl) {
                printBinaryTree(head);
                System.out.println("Node[" + maxSubBSTHead_fr.value + "]");
                System.out.println("Node[" + maxSubBSTHead_tl.value + "]");
                break;
            }
        }
        System.out.println(i == testTimes ? "finish!!!" : "fucking");

    }
}
