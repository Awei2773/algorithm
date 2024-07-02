package waigo.zuoshen.basic.MyCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * author waigo
 * create 2021-03-27 23:51
 */
/*
给定一个二叉树的根节点 root ，返回它的 中序 遍历。
两种非递归方法：
1.压栈
2.Morris遍历
*/
public class Code10_NoRecurInTranTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    //方法一：压栈
    //将树分解成左斜线，一路压入左边界，压不了则弹出然后存结果,并且右子树也这么处理
    public static List<Integer> inorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> res = new ArrayList<>();
        TreeNode cur = root;
        //每次遍历一个新树的时候不是直接压，而是对你这个节点判空，这样就省去了之后左右都得判空了
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {//妙就妙在这里，直接取消掉左右的区别了，每次都是像是处理一个全新的树，反正头先给我压进去
                stack.push(cur);//然后处理你左子树去，每棵树都这么处理，到了叶子节点，左树空了，弹出那个肯定是它的头，然后处理右子树
                cur = cur.left;//每棵树都这样整体就是中序
            } else {
                cur = stack.pop();
                res.add(cur.val);
                cur = cur.right;
            }
        }
        return res;
    }
    //方法二：Morris序改造中序
    //如有左树，找到左树最右一个节点
    //  1.如果right指向null,让它right指向cur,表示左树还没遍历，cur = cur.left
    //  2.如果right指向cur,让它指向null，cur = cur.right
    //没有左树，往右走
    public static List<Integer> morrisInOrder(TreeNode root) {
        TreeNode cur = root,mostRight;
        List<Integer> res = new ArrayList<>();
        while (cur!=null){
            //如有左树
            if(cur.left!=null){
                 mostRight = cur.left;
                 while (mostRight.right!=null&&mostRight.right!=cur){
                     mostRight = mostRight.right;
                 }
                 if(mostRight.right==null){
                     mostRight.right = cur;
                     cur = cur.left;
                     continue;
                 }
                 res.add(cur.val);
                 mostRight.right = null;
            }else{
                res.add(cur.val);
            }
            cur = cur.right;
        }
        return res;
    }

    public static void main(String[] args) {
        TreeNode head = new TreeNode(1);
        head.right = new TreeNode(2);
        head.right.left = new TreeNode(3);
        System.out.println(Arrays.toString(morrisInOrder(head).toArray()));
    }
}
