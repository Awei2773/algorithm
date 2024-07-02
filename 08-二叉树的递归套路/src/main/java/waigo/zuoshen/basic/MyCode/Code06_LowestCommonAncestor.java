package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-03-15 7:25
 */
/*
题目：
给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，
满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
*/
/*
解题思路：按使用二叉树递归套路，左右整合信息
信息：
是否存在p，是否存在q，p,q的最近公共祖先是什么
*/
public class Code06_LowestCommonAncestor {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return "TreeNode{" + "val=" + val + '}';
        }
    }
    public static class Info{
        private boolean isExistP;
        private boolean isExistQ;
        private TreeNode lowestCommonAncestor;

        public Info(boolean isExistP, boolean isExistQ, TreeNode lowestCommonAncestor) {
            this.isExistP = isExistP;
            this.isExistQ = isExistQ;
            this.lowestCommonAncestor = lowestCommonAncestor;
        }
    }
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return process(root,p,q).lowestCommonAncestor;
    }

    private static Info process(TreeNode root, TreeNode p, TreeNode q) {
        if(root==null){
            return new Info(false,false,null);
        }
        boolean isExistP;
        boolean isExistQ;
        TreeNode lowestCommonAncestor = null;
        Info leftInfo = process(root.left, p, q);
        Info rightInfo = process(root.right,p,q);
        isExistP = root==p||leftInfo.isExistP||rightInfo.isExistP;
        isExistQ = root==q||leftInfo.isExistQ||rightInfo.isExistQ;
        if(isExistP&&isExistQ){//这棵树都凑不齐两个节点就肯定不会有祖先
            //拢共三种情况
            //1.头节点是最近公共祖先
            lowestCommonAncestor = root;
            //2.公共祖先在左子树
            if(leftInfo.lowestCommonAncestor!=null)
                lowestCommonAncestor = leftInfo.lowestCommonAncestor;
            //3.公共祖先在右子树
            if(rightInfo.lowestCommonAncestor!=null)
                lowestCommonAncestor = rightInfo.lowestCommonAncestor;

        }
        return new Info(isExistP,isExistQ,lowestCommonAncestor);

    }

    public static void main(String[] args) {
        TreeNode head = new TreeNode(3);
        TreeNode h1 = new TreeNode(5);
        head.left = h1;
        head.left.left = new TreeNode(6);
        head.left.right = new TreeNode(2);
        head.left.right.left = new TreeNode(7);
        TreeNode h2 = new TreeNode(4);
        head.left.right.right = h2;
        head.right = new TreeNode(1);
        head.right.left = new TreeNode(0);
        TreeNode h3 = new TreeNode(8);
        head.right.right = h3;
        System.out.println(lowestCommonAncestor(head, h3, h2));

    }


}
