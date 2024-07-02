package waigo.zuosheng.advanced.MyCode;


/**
 * author waigo
 * create 2021-06-11 8:36
 */
public class Code03_PathSumI {
    public class TreeNode {
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

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root==null) return false;
        return process(root,targetSum);
    }
    //返回包括root是否能走出一条k路径
    private boolean process(TreeNode root, int targetSum) {
        if (root.left == null&&root.right==null) {
            return root.val==targetSum;
        }
        boolean leftRes = false;
        if(root.left!=null){
            leftRes = process(root.left,targetSum-root.val);
        }
        boolean rightRes = false;
        if(root.right!=null){
            rightRes = process(root.right,targetSum-root.val);
        }
        return leftRes||rightRes;
    }
}
