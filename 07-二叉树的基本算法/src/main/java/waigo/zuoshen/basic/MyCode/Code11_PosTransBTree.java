package waigo.zuoshen.basic.MyCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * author waigo
 * create 2021-03-28 9:56
 */
/*
给定一个二叉树，返回它的 后序 遍历。
进阶: 递归算法很简单，你可以通过迭代算法完成吗？
*/
public class Code11_PosTransBTree {
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

    //方法一：递归方式
    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        pos(root, res);
        return res;
    }

    private static void pos(TreeNode curNode, List<Integer> res) {
        if (curNode == null) return;
        pos(curNode.left, res);
        pos(curNode.right, res);
        res.add(curNode.val);
    }

    //方法二：压栈方式,用一个栈
    //这个由于不只是得判左右是否为空，还得判断左右是否是已经遍历过的，所以不能统一处理得直接压
    public static List<Integer> postorderTraversal1(TreeNode root) {
        if (root == null) return new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode curNode, lastTrans = root;
        while (!stack.isEmpty()) {
            curNode = stack.peek();
            if (curNode.left != null && lastTrans != curNode.left && lastTrans != curNode.right) {//没有遍历过左树
                stack.push(curNode.left);
            } else if (curNode.right != null && lastTrans != curNode.right) {//没有遍历过右树
                stack.push(curNode.right);
            } else {//遍历头
                res.add(stack.pop().val);
                lastTrans = curNode;
            }
        }
        return res;
    }

    //方法三：使用两个栈
    public static List<Integer> postorderTwoStack(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        //使用一个栈可以很容易得到前序遍历，如果先使用一个栈搞出一个头右左的顺序压入另一个栈再倒出来不就是后序了嘛
        Stack<TreeNode> tempStack = new Stack<>();
        Stack<TreeNode> resStack = new Stack<>();
        tempStack.push(root);
        TreeNode cur;
        while (!tempStack.isEmpty()){
             cur = tempStack.pop();
             if(cur.left!=null){//先压入左，后处理左，就能够头右左了
                 tempStack.push(cur.left);
             }
             if(cur.right!=null){
                 tempStack.push(cur.right);
             }
             resStack.push(cur);
        }
        //头右左进结果栈，出来就是左右头了
        while (!resStack.isEmpty())
            res.add(resStack.pop().val);
        return res;
    }

    //方法四：Morris遍历,将二叉树按右斜线分解(非递归一个栈搞中序是按左斜线分解),然后有左子树的节点第二次回到自己的时候逆序打印自己左孩子所在右斜线
    public static List<Integer> postorderMorris(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        TreeNode cur = root,mostRight;
        while (cur!=null){
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
                mostRight.right = null;//得先指回空，不然还是指向cur会把cur.right都给打印出来的
                reversePrintLine(cur.left,res);
            }
            cur = cur.right;
        }
        //最后一个斜线没有右边的头打印它，自己打印一遍
        reversePrintLine(root,res);
        return res;
    }

    private static void reversePrintLine(TreeNode head, List<Integer> res) {
        TreeNode newHead = reverseList(head);
        head = newHead;
        while (head!=null){
            res.add(head.val);
            head = head.right;
        }
        reverseList(newHead);//转回去
    }

    //为了逆序打印右斜线，需要反转链表
    public static TreeNode reverseList(TreeNode head){
        TreeNode pre = null,cur = head,next;
        while (cur!=null){
            next = cur.right;
            cur.right = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }
    public static void main(String[] args) {
        TreeNode head = new TreeNode(1);
        head.right = new TreeNode(2);
        head.right.left = new TreeNode(3);
        System.out.println(Arrays.toString(postorderMorris(head).toArray()));
    }
}
