package waigo.zuoshen.basic.MyCode;

import java.util.ArrayList;

/**
 * author waigo
 * create 2021-04-16 14:12
 */
//设计一个算法，找出二叉搜索树中指定节点的“下一个”节点（也即中序后继）。
//如果指定节点没有对应的“下一个”节点，则返回null。

//    这题和Code07不同点是这里没有parent指针，所以必须从上往下找
//    不过思路一样，我就看看从头到下面的每个点是否左边最右一个节点是p就好了,注意这棵树是二叉搜索树
public class Code13_InorderSuccessor {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return "" + val ;
        }
    }

    public static TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root == null || p == null) return null;
        if (p.right != null) {
            //有右孩子，下一跳肯定是右孩子上最左一个节点
            return mostLeft(p.right);
        }
        //没有右孩子，那么这个节点如果有后继的话说明他肯定在某个左子树的最右一个节点上
        TreeNode parent = root;
        while (parent != null) {
            if (parent.left!=null&&p == mostRight(parent.left)) return parent;
            parent = p.val <= parent.val ? parent.left : parent.right;
        }
        return null;
    }

    private static TreeNode mostRight(TreeNode node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    private static TreeNode mostLeft(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}
