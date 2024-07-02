package waigo.zuoshen.advance.MyCode;

/**
 * author waigo
 * create 2021-03-13 20:27
 */
/*
输入两棵二叉树A和B，判断B是不是A的子结构。(约定空树不是任意一个树的子结构)
B是A的子结构， 即 A中有出现和B相同的结构和节点值。
https://leetcode-cn.com/problems/shu-de-zi-jie-gou-lcof/
注意：这道题说的是子结构不是子树，所以不能用KMP来做，能够使用KMP肯定是从某个长的数组中匹配某一段长度相同，
而相同树结构它的先序序列化或者后续序列化是肯定的，所以可以从A树序列化中找出B树的序列化则表示B树是A树的子树

         3
        / \           4
 树A：  4   5 树B：   /    这是表示树B算是树A的子结构，而很明显树B不是树A的子树
      / \           1
     1   2
*/
public class Code02_TreeSubStructEqual {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    //暴力解，使用递归看看A每个子树是否和B结构一样
    public static boolean isSubStructure(TreeNode A, TreeNode B) {
        if (B == null || A == null) return false;
        //head2和head1都不为空
        return isSameStruct(A, B)||isSubStructure(A.left, B) || isSubStructure(A.right, B);
    }

    //判断head2是否是head1的子结构
    private static boolean isSameStruct(TreeNode head1, TreeNode head2) {
        //如果head2为空，则不用比较了，说明同head2的某个子树左或右孩子为空，不管相同位置上head1的这个节点是否为空肯定都是true
        if(head2==null) return true;
        //head2不为空，而head1为空，则肯定是false,结构不相同
        if (head1 == null) return false;
        return head1.val == head2.val && isSameStruct(head1.left, head2.left) && isSameStruct(head1.right, head2.right);
    }

    public static TreeNode generalRandomTree(int maxFloor, int maxVal, int curFloor) {
        if (curFloor == maxFloor ) {
            return null;
        }
        TreeNode head = new TreeNode((int)(Math.random()*maxVal));
        head.left = generalRandomTree(maxFloor,maxVal,curFloor+1);
        head.right = generalRandomTree(maxFloor,maxVal,curFloor+1);
        return head;
    }
    public static void preSerialTree(TreeNode head,StringBuilder builder){
        if(head==null) {
            builder.append("-null-");
            return;
        }
        builder.append("-").append(head.val).append("-");
        preSerialTree(head.left,builder);
        preSerialTree(head.right,builder);
    }

    public static void main(String[] args) {
        StringBuilder builder1 = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();
        int maxVal = 3;
        int maxFloor = 3;
        TreeNode head1 = generalRandomTree(maxFloor, maxVal, 1);
        TreeNode head2 = generalRandomTree(2, maxVal, 1);
        preSerialTree(head1, builder1);
        preSerialTree(head2, builder2);
        System.out.println("head1："+builder1);
        System.out.println("head2："+builder2);
        System.out.println(isSubStructure(head1,head2));
    }
}
