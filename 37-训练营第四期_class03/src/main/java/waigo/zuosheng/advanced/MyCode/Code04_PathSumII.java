package waigo.zuosheng.advanced.MyCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * author waigo
 * create 2021-06-11 10:12
 */

public class Code04_PathSumII {
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
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> res = new ArrayList<>();
        if(root==null) return res;
        process(root,targetSum,res,new ArrayList<>());
        return res;
    }

    private void process(TreeNode root, int targetSum, List<List<Integer>> res,List<Integer> path) {
        path.add(root.val);
        if(root.left==null&&root.right==null){
            if(targetSum==root.val){
                List<Integer> dest = new ArrayList<>(path.size());
                dest.addAll(path);
                res.add(dest);
            }
            path.remove(path.size()-1);//轨迹擦除
            return;
        }
        if(root.left!=null){
            process(root.left,targetSum-root.val,res,path);
        }
        if(root.right!=null){
            process(root.right,targetSum-root.val,res,path);
        }
        path.remove(path.size()-1);//轨迹擦除
    }
}
