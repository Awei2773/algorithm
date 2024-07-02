package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-19 9:26
 */

/**
 * 求完全二叉树节点的个数
 * 要求时间复杂度低于O(N)
 */
public class Code03_CompleteTreeNodeSum {
    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }
    public int countNodes(TreeNode root) {
        return process(root);
    }

    //返回完全二叉树节点个数
    private int process(TreeNode root) {
        if(root==null) return 0;
        //一直往左一眼下去，必是树高
        int treeHeight = getTreeHeight(root);
        //右树一眼看下去，找到右树树高
        int rightTreeHeight = getTreeHeight(root.right);
        //1.完全二叉树过到右树了，说明左树是满的
        if(treeHeight==rightTreeHeight+1){
            return (1<<(treeHeight-1))+process(root.right);
        }
        //2.没过右树，右树是满的
        return (1<<rightTreeHeight)+process(root.left);
    }

    private int getTreeHeight(TreeNode root) {
        TreeNode cur = root;
        int treeHeight = 0;
        while (cur!=null){
            treeHeight++;
            cur = cur.left;
        }
        return treeHeight;
    }


}
