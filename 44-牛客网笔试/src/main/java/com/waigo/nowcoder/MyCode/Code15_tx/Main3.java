package com.waigo.nowcoder.MyCode.Code15_tx;

/**
 * author waigo
 * create 2021-09-26 21:08
 */

import java.util.*;

class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;
    }
}

public class Main3 {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * @param root TreeNode类 指向量子树的根
     * @param b    int整型二维数组 表示每次交换的两个子树
     * @return TreeNode类
     */
    public TreeNode solve(TreeNode root, int[][] b) {
        if (root == null || b == null || b.length == 0) return root;
        //一次遍历将b中所有涉及到的节点给定下来
        int rows = b.length;
        int cols = b[0].length;
        //数映射到节点
        HashMap<Integer, TreeNode> intToNode = new HashMap<>();
        //每个节点的父节点也得保存
        HashMap<Integer, TreeNode> nodeToFather = new HashMap<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                intToNode.put(b[row][col], null);
            }
        }
        //遍历树将这些节点固定
        getNodes(root, intToNode, nodeToFather, null, 0);
        //进行交换
        swapSubTree(intToNode, nodeToFather, b);
        return root;//不可能会换头
    }

    public void getNodes(TreeNode root, HashMap<Integer, TreeNode> intToNode
                ,HashMap<Integer, TreeNode> nodeToFather, TreeNode father, int floor) {
        if (root == null) return;
        if (intToNode.containsKey(root.val)) {
            intToNode.put(root.val, root);
        }
        nodeToFather.put(root.val, father);
        getNodes(root.left, intToNode, nodeToFather, root, floor + 1);
        getNodes(root.right, intToNode, nodeToFather, root, floor + 1);
    }

    public void swapSubTree(HashMap<Integer, TreeNode> intToNode,
                             HashMap<Integer, TreeNode> nodeToFather, int[][] b) {
        for (int[] step : b) {
            int xVal = step[0];
            int yVal = step[1];
            //不互为祖先关系可交换
            TreeNode x = intToNode.get(xVal);
            TreeNode y = intToNode.get(yVal);
            if (checkIsValid(nodeToFather, x, y)) {
                TreeNode fx = nodeToFather.get(xVal);
                TreeNode fy = nodeToFather.get(yVal);
                boolean xIsLeft = x == fx.left;
                boolean yIsLeft = y == fy.left;
                //x和fx的边断开，y与fy断开
                //x连接fy,y连接fx
                if (xIsLeft) {
                    fx.left = y;
                } else {
                    fx.right = y;
                }
                if (yIsLeft) {
                    fy.left = x;
                } else {
                    fy.right = x;
                }
                //注意交换完成之后父节点那个map也得变
                nodeToFather.put(xVal, fy);
                nodeToFather.put(yVal, fx);
            }
        }
    }

    private boolean checkIsValid(HashMap<Integer, TreeNode> nodeToFather, TreeNode x, TreeNode y) {
        TreeNode tmp = x;
        int xfloor=0,yfloor=0;
        TreeNode parent;
        while((parent=nodeToFather.get(tmp.val))!=null){
            tmp = parent;
            xfloor++;
        }
        tmp = y;
        while((parent=nodeToFather.get(tmp.val))!=null){
            tmp = parent;
            yfloor++;
        }
        if (xfloor == yfloor) return true;
        TreeNode bigFloorOne = xfloor < yfloor ? y : x;
        TreeNode smallFloorOne = bigFloorOne == x ? y : x;
        //一路撸上去，看是否会碰到另一个
        while ((parent = nodeToFather.get(bigFloorOne.val)) != null) {
            if(parent==smallFloorOne) return false;
            bigFloorOne = parent;
        }
        return true;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        int[][] b = {{1, 2}, {2, 3}, {1, 7}};
        new Main3().solve(root, b);
    }
}