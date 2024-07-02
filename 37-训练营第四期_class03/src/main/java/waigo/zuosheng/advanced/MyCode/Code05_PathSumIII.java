package waigo.zuosheng.advanced.MyCode;


import java.util.HashMap;
import java.util.Map;

/**
 * author waigo
 * create 2021-06-11 11:00
 */
public class Code05_PathSumIII {
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

    //eclHead=res[0],iclHead=res[1]
    public static int pathSum(TreeNode root, int targetSum) {
        if (root == null) return 0;
        int[] info = process(root, targetSum);
        return info[0] + info[1];
    }
    //递归套路法复杂度太高，关键在于重复多次向下进行求值，自顶向下的，可以利用前缀和加速，
    //到一个位置就结算一下这个位置结尾能够有多少条路径
    private static int[] process(TreeNode root, int targetSum) {
        int iclHead=root.val==targetSum?1:0;//包头
        int eclHead=0;//不包头
        if (root.left == null && root.right == null) {

            return new int[]{eclHead, iclHead};
        }
        if (root.left != null) {
            int[] leftInfoK_N = process(root.left, targetSum - root.val);
            iclHead+=leftInfoK_N[1];
            int[] leftInfo = process(root.left, targetSum);
            eclHead+=leftInfo[1]+leftInfo[0];
        }
        if (root.right != null) {
            int[] rightInfoK_N = process(root.right, targetSum - root.val);
            iclHead+=rightInfoK_N[1];
            int[] rightInfo = process(root.right, targetSum);
            eclHead+=rightInfo[1]+rightInfo[0];
        }
        return new int[]{eclHead,iclHead};
    }
    public static int pathSum1(TreeNode root, int targetSum) {
        if (root == null) return 0;
        HashMap<Integer, Integer> prefixNum = new HashMap<>();
        prefixNum.put(0,1);
        return process2(root,targetSum,0, prefixNum);
    }

    private static int process2(TreeNode root, int targetSum,int prefix, HashMap<Integer, Integer> prefixNum) {
        int res = 0;
        if(root!=null){
            prefix+=root.val;
            res+=prefixNum.getOrDefault(prefix-targetSum,0);
            prefixNum.put(prefix,prefixNum.getOrDefault(prefix,0)+1);
            res+=process2(root.left,targetSum,prefix,prefixNum);
            res+=process2(root.right,targetSum,prefix,prefixNum);
            prefixNum.put(prefix,prefixNum.get(prefix)-1);
        }
        return res;
    }
    public static TreeNode generalRandomTree(int curFloor,int maxFloor,int maxVal){
        if(curFloor==maxFloor||Math.random()<0.2) return null;
        TreeNode root = new TreeNode((int) (Math.random()*maxVal)-(int) (Math.random()*maxVal));
        root.left = generalRandomTree(curFloor+1,maxFloor,maxVal);
        root.right = generalRandomTree(curFloor+1, maxFloor, maxVal);
        return root;
    }
    public static void print(TreeNode root){
        if(root==null){
            System.out.println("null,");
            return;
        }
        print(root.left);
        System.out.println(root.val+",");
        print(root.right);
    }

    public static void main(String[] args) {
        int maxVal = 5;
        int maxFloor = 18;
        int curFloor = 0;
        int testTimes = 1;
        int i = 0;
        for (; i < testTimes; i++) {
            TreeNode root = generalRandomTree(curFloor, maxFloor, maxVal);
            int k = (int) (Math.random()*maxVal);
            int res1 = pathSum(root, k);
            int res2 = pathSum1(root, k);
            if(res1!=res2){
                System.out.println("oops");
                System.out.println("res1"+res1);
                System.out.println("res2"+res2);
                print(root);
                break;
            }
        }
        System.out.println(i==testTimes?"finish!!!":"oops!!!");
    }

}
