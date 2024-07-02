package com.waigo.offer;

import java.util.Stack;

/**
 * author waigo
 * create 2021-10-24 19:35
 */
public class T07_rebuildTree {
    public static class TreeNode{
        TreeNode left,right;
        int val;

        public TreeNode(int val) {
            this.val = val;
        }
    }
    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder==null||inorder==null||preorder.length!=inorder.length||preorder.length==0)
            return null;
        //preorder[0]肯定是树头，压入栈中
        //一次鲁完左边一条路上的节点，有点像是单栈中序遍历的算法
        //左边啥时候算完了呢？看中序当前的位置，碰到了就说明这条路走完了，该去走右边了
        TreeNode root = new TreeNode(preorder[0]);
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        int inorderIdx = 0,preorderIdx = 1;
        int pN = preorder.length;
        int iN = inorder.length;
        while(!stack.isEmpty()&&preorderIdx!=pN&&inorderIdx!=iN){
            TreeNode node = stack.peek();
            if(node.val!=inorder[inorderIdx]){
                //证明左子树没处理完
                node.left = new TreeNode(preorder[preorderIdx++]);
                stack.push(node.left);
            }else{
                //左子树处理完了，一直弹到一个有右子树的
                node = stack.pop();
                while(!stack.isEmpty()&&node.val==inorder[inorderIdx]){
                    inorderIdx++;//如果空了这里的下标就会少移动一次了
                    node = stack.pop();
                }
                node.right = new TreeNode(preorder[preorderIdx++]);
                stack.push(node.right);
            }
        }
        return root;
    }

    public static void main(String[] args) {
        TreeNode treeNode = buildTree(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7});
    }
}
