package waigo.zuoshen.basic.MyCode;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * author waigo
 * create 2021-03-30 9:47
 */
/*
序列化是将一个数据结构或者对象转换为连续的比特位的操作，进而可以将转换后的数据存储在一个文件或者内存中，同时也可以通过网络传输到另一个计算机环境，
采取相反方式重构得到原数据。请设计一个算法来实现二叉树的序列化与反序列化。这里不限定你的序列 / 反序列化算法执行逻辑，你只需要保证一个二叉树可以
被序列化为一个字符串并且将这个字符串反序列化为原始的树结构。
提示: 输入输出格式与 LeetCode 目前使用的方式一致，详情请参阅 LeetCode 序列化二叉树的格式。你并非必须采取这种方式，
你也可以采用其他的方法解决这个问题。
*/
public class Code12_SerialAndReBuildTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    //1.前序方式
    // Encodes a tree to a single string.
    public static String serializePre(TreeNode root) {
        StringBuilder res = new StringBuilder();
        pres(res, root);
        res.deleteCharAt(0);
        return res.toString();
    }

    private static void pres(StringBuilder res, TreeNode root) {
        if (root == null) {
            res.append("/n");
            return;
        }
        res.append("/").append(root.val);
        pres(res, root.left);
        pres(res, root.right);
    }

    // Decodes your encoded data to tree.
    public static TreeNode deserializePre(String data) {
        if (data == null || "".equals(data)) return null;
        return preBuild(new LinkedList<>(Arrays.asList(data.split("/"))));
    }

    public static TreeNode preBuild(Queue<String> vals) {
        TreeNode head = generalTreeNode(vals.poll());//流程控制下不可能出现容器空还poll的情况
        if (head == null) return null;
        head.left = preBuild(vals);
        head.right = preBuild(vals);
        return head;
    }

    public static TreeNode generalTreeNode(String s) {
        return "n".equals(s) ? null : new TreeNode(Integer.valueOf(s));
    }

    public static TreeNode generalRandomTree(int maxFloor, int curFloor, int maxVal) {
        if (curFloor > maxFloor || Math.random() < 0.3) return null;
        TreeNode head = new TreeNode((int) (Math.random() * maxVal) - (int) (Math.random() * maxVal));
        head.left = generalRandomTree(maxFloor, curFloor + 1, maxVal);
        head.right = generalRandomTree(maxFloor, curFloor + 1, maxVal);
        return head;
    }

    public static boolean isTreeEql(TreeNode t1, TreeNode t2) {
        if (t1 == t2) return true;
        if (t1 == null || t2 == null) return false;
        return t1.val == t2.val && isTreeEql(t1.left, t2.left) && isTreeEql(t1.right, t2.right);
    }

    //2.后序方式
    public static String serializePos(TreeNode root) {
        StringBuilder res = new StringBuilder();
        pos(res, root);
        res.deleteCharAt(0);
        return res.toString();
    }

    private static void pos(StringBuilder res, TreeNode root) {
        if (root == null) {
            res.append("/n");
            return;
        }
        pos(res, root.left);
        pos(res, root.right);
        res.append("/").append(root.val);
    }

    public static TreeNode deserializePos(String data) {
        if (data == null || "".equals(data)) return null;
        Stack<String> vals = new Stack<>();
        for (String s : data.split("/"))
            vals.push(s);
        return posBuild(vals);
    }

    public static TreeNode posBuild(Stack<String> vals) {
        TreeNode head = generalTreeNode(vals.pop());//流程控制下不可能出现容器空还弹的情况
        if (head == null) return null;
        head.right = posBuild(vals);
        head.left = posBuild(vals);
        return head;
    }

    //3.层序方式
    public static String serializeLevel(TreeNode root) {
        StringBuilder res = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode curNode;
        while (!queue.isEmpty()) {
            curNode = queue.poll();
            if (curNode == null) {
                res.append("/n");
            } else {
                res.append("/").append(curNode.val);
                queue.add(curNode.left);
                queue.add(curNode.right);
            }
        }
        res.deleteCharAt(0);
        return res.toString();
    }

    public static TreeNode deserializeLevel(String data) {
        if (data == null || "".equals(data)) return null;
        Queue<String> vals = new LinkedList<>(Arrays.asList(data.split("/")));
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode head = generalTreeNode(vals.poll());
        if (head != null) {
            queue.add(head);
            TreeNode curNode;
            while (!queue.isEmpty() && !vals.isEmpty()) {
                curNode = queue.poll();
                curNode.left = generalTreeNode(vals.poll());
                curNode.right = generalTreeNode(vals.poll());
                if (curNode.left != null) {
                    queue.add(curNode.left);
                }
                if (curNode.right != null) {
                    queue.add(curNode.right);
                }
            }
        }
        return head;
    }


    public static void main(String[] args) {
        //前序对数器
        /*int maxFloor = 6,maxVal = 10,testTime = 1000000;
        int i = 0;
        for(;i<testTime;i++){
            TreeNode treeNode = generalRandomTree(maxFloor, 1, maxVal);
            String s = serializePre(treeNode);
            TreeNode treeNode1 = deserializePre(s);
            if(!isTreeEql(treeNode,treeNode1)) break;
        }
        System.out.println(i==testTime?"finish!!!":"fucking!!!");*/
        //后序对数器
        /*int maxFloor = 6,maxVal = 10,testTime = 1000000;
        int i = 0;
        for(;i<testTime;i++){
            TreeNode treeNode = generalRandomTree(maxFloor, 1, maxVal);
            String s = serializePos(treeNode);
            TreeNode treeNode1 = deserializePos(s);
            if(!isTreeEql(treeNode,treeNode1)) break;
        }
        System.out.println(i==testTime?"finish!!!":"fucking!!!");*/
        //层序对数器
        int maxFloor = 6,maxVal = 10,testTime = 100000;
        int i = 0;
        for(;i<testTime;i++){
            TreeNode treeNode = generalRandomTree(maxFloor, 1, maxVal);
            String s = serializeLevel(treeNode);
            TreeNode treeNode1 = deserializeLevel(s);
            if(!isTreeEql(treeNode,treeNode1)) break;
        }
        System.out.println(i==testTime?"finish!!!":"fucking!!!");
    }
}
