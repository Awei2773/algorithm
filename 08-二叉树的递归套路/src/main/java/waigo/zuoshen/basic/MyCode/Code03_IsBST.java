package waigo.zuoshen.basic.MyCode;

import java.util.ArrayList;

/**
 * author waigo
 * create 2021-02-23 20:27
 */
//给定一棵二叉树的头节点head，返回这颗二叉树是不是搜索二叉树
//搜索二叉树:左树和右树(如果存在左右树)都是搜索二叉树，而且左树的最大值(小于等于)头节点，右树最小值(大于等于)头节点
//例如
//      5
//   1     7
// 0  2  6  8
//需要子树提供的信息：1）是否是BST 2）最大值 3）最小值,对所有子树一视同仁好处理，取信息合集
public class Code03_IsBST {
    public static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    //暴力法:搜索二叉树定义下，对任意子树，左树的值比头小，头比右树值小，就是中序遍历是升序
    //将节点中序序列存入容器，然后判断是否升序即可
    public static boolean isBST1(Node head) {
        ArrayList<Node> list = new ArrayList<>();
        in(head, list);
        for (int i = 0; i < list.size() - 1; i++)
            if (list.get(i).value > list.get(i + 1).value) return false;
        return true;
    }

    private static void in(Node head, ArrayList<Node> list) {
        if (head == null) return;
        in(head.left, list);
        list.add(head);
        in(head.right, list);
    }

    public static boolean isBST2(Node head) {
        if (head == null) return true;//注意head为空process(head)返回null造成空指针
        return process(head).isBST;
    }

    private static Info process(Node head) {
        if (head == null) {
            //为空时候最小和最大值不好给，那就索性返回空，但是使用信息的时候需要进行判空
            return null;
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        //加工leftInfo == rightInfo若是都为空，那么是BST，这个子树就一个节点
        boolean isBST =
                //左树若是存在，那么需要它是BST，而且左树的最大值小于头的值，右树同理
                (leftInfo == null || (leftInfo.isBST && leftInfo.max <= head.value))
              &&
                (rightInfo == null || (rightInfo.isBST && rightInfo.min >= head.value));
        int max = head.value;
        int min = head.value;
        if (leftInfo != null) {
            max = Math.max(leftInfo.max, max);
            min = Math.min(leftInfo.min, min);
        }
        if (rightInfo != null) {
            max = Math.max(rightInfo.max, max);
            min = Math.min(rightInfo.min, min);
        }
        return new Info(isBST, max, min);
    }

    public static class Info {
        private boolean isBST;
        private int max;
        private int min;

        public Info(boolean isBST, int max, int min) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
        }
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        int i = 0;
        for (; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isBST1(head) != isBST2(head)) {
                break;
            }
        }
        System.out.println(i == testTimes ? "finish!" : "Oops!");
    }

}
