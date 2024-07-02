package waigo.zuoshen.advance.MyCode;

/**
 * author waigo
 * create 2021-03-20 19:30
 */
/*
如果不用左树要点信息，右树要点信息然后自己整合后上传的就可以用Morris遍历，空间复杂度低

输入一棵二叉树的根节点，求该树的深度。从根节点到叶节点依次经过的节点（含根、叶节点）形成树的一条路径，最长路径的长度为树的深度。
例如：
给定二叉树 [3,9,20,null,null,15,7]，
*/
public class Code02_MaxHeight {

    //Definition for a binary tree node.
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static int maxDepth(TreeNode root) {
        TreeNode cur = root;
        TreeNode mostRight;
        int maxDepth = 0;
        int curLevel = 1;//难点在于从下面往回跳的时候应该减多少，其实这个值就是左子树最右节点的高度，所以记录一下这个值就可以了
        int mostRightHeight;
        while (cur != null) {
            if(cur.left==null&&cur.right==null) maxDepth = Math.max(maxDepth,curLevel);
            if (cur.left != null) {
                mostRight = cur.left;
                mostRightHeight = 1;
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                    mostRightHeight++;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    curLevel++;//每次在节点移动的时候维护层数
                    cur = cur.left;
                    continue;
                }
                //来到这里是已经跳回上面了，赶紧更新一下curLevel
                mostRight.right = null;
                if(mostRight.left==null){
                    maxDepth = Math.max(maxDepth,curLevel-1);//这时候第二次回到头，此时curLevel等于mostRight的高度+1
                }
                curLevel -= mostRightHeight;
            } else {
                curLevel++;//没有左子树的时候直接层数加一，放在else中和往回跳逻辑错开
            }
            cur = cur.right;
        }
        return maxDepth;
    }
    public static int maxDepth1(TreeNode head){
        return process(head);
    }
    //返回head这棵树的深度
    public static int process(TreeNode head){
        if(head==null) return 0;
        int leftHeight = process(head.left);
        int rightHeight = process(head.right);
        return 1+Math.max(leftHeight,rightHeight);
    }
    public static TreeNode generalRandomTree(int maxFloor,int maxVal,int curFloor){
        if(curFloor>maxFloor||Math.random()<0.3) return null;
        TreeNode head = new TreeNode((int) (Math.random()*maxVal));
        head.left = generalRandomTree(maxFloor,maxVal,curFloor+1);
        head.right = generalRandomTree(maxFloor,maxVal,curFloor+1);
        return head;
    }
    public static void main(String[] args) {
        int maxFloor = 5,maxVal = 10,testTimes = 10000;
        int i = 0;
        for(;i< testTimes;i++){
            TreeNode head = generalRandomTree(maxFloor, maxVal, 1);
            int res1 = maxDepth(head);
            int res2 = maxDepth1(head);
            if(res1 != res2){
                System.out.println("res1:"+res1);
                System.out.println("res2:"+res2);
                break;
            }
        }
        System.out.println(i==testTimes?"finish!!!":"fucking!!!");

    }
}
