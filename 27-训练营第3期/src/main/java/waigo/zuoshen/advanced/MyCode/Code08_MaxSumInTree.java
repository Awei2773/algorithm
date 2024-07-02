package waigo.zuoshen.advanced.MyCode;

import javax.swing.tree.TreeNode;

/**树形问题三连
 * author waigo
 * create 2021-05-07 7:34
 */
/*
给定一个二叉树的头节点head，路径的规定有以下三种不同的规定：

1）路径必须是头节点出发，到叶节点为止，返回最大路径和

2）路径可以从任何节点出发，但必须往下走到达任何节点，返回最大路径和

3）路径可以从任何节点出发，到任何节点，返回最大路径和


*/
public class Code08_MaxSumInTree {
    //节点的val表示这个点的路径
    public static class TreeNode {
        int val;
        TreeNode left,right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    //1.路径必须是头节点出发，到叶节点为止，返回最大路径和
    //思路，枚举所有的路径，找到最大的那条，使用递归套路，信息量为每棵树头结点到叶子节点的最大路径和
    public static int getMaxSumInTree1(TreeNode head){
        if(head==null) return 0;
        return process(head);
    }

    //返回这棵树的最大路径和，自己保证head别进null
    private static int process(TreeNode head) {
        if(head.left==null&&head.right==null) return head.val;//到叶子节点了
        int maxSum = Integer.MIN_VALUE;
        if(head.left!=null){
            maxSum = Math.max(maxSum,process(head.left));
        }
        if(head.right!=null){
            maxSum = Math.max(maxSum,process(head.right));
        }
        return maxSum+head.val;
    }
    //方法二：使用一个全局变量记录一下当前最长的路径和，这样只管往下走，只要超了最长的就更新，不用在回到头的时候再更新
    private int maxSum2 = Integer.MIN_VALUE;
    public int getMaxSumInTree2(TreeNode head){
        if(head==null) return 0;
        process2(head,0);
        return maxSum2;
    }

    private void process2(TreeNode head,int pre) {
        if(head.left==null&&head.right==null){
            maxSum2 = Math.max(maxSum2,pre+head.val);
        }

        if(head.left!=null){
            process2(head.left,pre+head.val);
        }
        if(head.right!=null){
            process2(head.right,pre+head.val);
        }
    }

    //2）路径可以从任何节点出发，但必须往下走到达任何节点，返回最大路径和
    public static class Info{
        private int maxSumFromHead;
        private int maxSumNoHead;

        public Info(int maxSumFromHead, int maxSumNoHead) {
            this.maxSumFromHead = maxSumFromHead;
            this.maxSumNoHead = maxSumNoHead;
        }
    }
    public static int getMaxSum4(TreeNode head){
        Info info= process3(head);
        return info==null?0:Math.max(info.maxSumFromHead,info.maxSumNoHead);
    }

    private static Info process3(TreeNode head) {
        if(head==null) return null;
        Info leftInfo = process3(head.left);
        Info rightInfo = process3(head.right);
        int maxSumFromHead = head.val;
        int maxSumNoHead = Integer.MIN_VALUE;
        if(leftInfo!=null){
            maxSumFromHead = Math.max(maxSumFromHead,leftInfo.maxSumFromHead+head.val);
            maxSumNoHead = Math.max(leftInfo.maxSumNoHead,leftInfo.maxSumFromHead);
        }
        if(rightInfo!=null){
            maxSumFromHead = Math.max(maxSumFromHead,rightInfo.maxSumNoHead+head.val);
            maxSumNoHead = Math.max(maxSumNoHead,rightInfo.maxSumNoHead);
            maxSumNoHead = Math.max(maxSumNoHead,rightInfo.maxSumFromHead);
        }
        return new Info(maxSumFromHead,maxSumNoHead);
    }
    /*
    路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。同一个节点在一条路径序列中 至多出现一次 。
    该路径 至少包含一个 节点，且不一定经过根节点。路径和 是路径中各节点值的总和。给你一个二叉树的根节点 root ，返回其 最大路径和 。
    */
    public static class Info1{
        private int maxSumFromHead;
        private int maxSumAllTree;

        public Info1(int maxSumFromHead, int maxSumAllTree) {
            this.maxSumFromHead = maxSumFromHead;
            this.maxSumAllTree = maxSumAllTree;
        }
    }
    /*
        递归套路，厘清所有情况,注意：是分析所有能够得到答案的情况，然后剖析需要哪些信息
        1,结果包含头
            1.1 头就是最大
            1.2 头往左走，需要知道必须以左树头开始的最大路径
            1.3 头往右走，需要知道必须以右树头开始的最大路径
            1.4 头连接两边
        2，结果不包含头
            2.1 左树的最长就是最长
            2.2 右树的最长就是最长

    */
    public static int maxPathSum(TreeNode root) {
        Info1 info = process4(root);
        return info==null?Integer.MIN_VALUE:info.maxSumAllTree;
    }

    private static Info1 process4(TreeNode root) {
        if(root==null) return null;
        Info1 leftInfo = process4(root.left);
        Info1 rightInfo = process4(root.right);
        int maxSumFromHead = root.val;
        int maxSumAllTree = root.val;//头不走了
        if(leftInfo!=null){
            maxSumFromHead = Math.max(maxSumFromHead,leftInfo.maxSumFromHead+root.val);
            maxSumAllTree = Math.max(leftInfo.maxSumAllTree,maxSumAllTree);//左树最长就是最长
            maxSumAllTree = Math.max(leftInfo.maxSumFromHead+root.val,maxSumAllTree);//头往左走
        }
        if(rightInfo!=null){
            maxSumFromHead = Math.max(maxSumFromHead,rightInfo.maxSumFromHead+root.val);
            maxSumAllTree = Math.max(maxSumAllTree,rightInfo.maxSumAllTree);//右树最长就是最长
            maxSumAllTree = Math.max(maxSumAllTree,rightInfo.maxSumFromHead+root.val);//头往右走
        }
        if(leftInfo!=null&&rightInfo!=null){
            maxSumAllTree = Math.max(maxSumAllTree,root.val+leftInfo.maxSumFromHead+rightInfo.maxSumFromHead);
        }
        return new Info1(maxSumFromHead,maxSumAllTree);
    }

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(5);
        node1.left = new TreeNode(4);
        node1.right = new TreeNode(8);
        node1.right.left = new TreeNode(13);
        node1.right.right = new TreeNode(4);
        node1.right.right.right = new TreeNode(1);
        node1.left.left = new TreeNode(11);
        node1.left.left.left = new TreeNode(7);
        node1.left.left.right = new TreeNode(2);
        System.out.println(maxPathSum(node1));
    }
}
