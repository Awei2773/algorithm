package waigo.zuoshen.advance.MyCode;

import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * author waigo
 * create 2021-03-13 21:58
 */
/*
检查子树。你有两棵非常大的二叉树：T1，有几万个节点；T2，有几万个节点。设计一个算法，判断 T2 是否为 T1 的子树。
如果 T1 有这么一个节点 n，其子树与 T2 一模一样，则 T2 为 T1 的子树，也就是说，从节点 n 处把树砍断，得到的树与 T2 完全相同
*/
public class Code03_TreeEqual {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    //暴力解：遍历所有的子树去看看是否两棵子树相等，时间复杂度O(N*M),T1节点数为N，T2节点数为M,M<=N
    public static boolean checkSubTree(TreeNode t1, TreeNode t2) {
        if (t2 == null) return true;
        if (t1 == null) return false;
        return isTreeEql(t1, t2) || checkSubTree(t1.left, t2) || checkSubTree(t1.right, t2);
    }

    private static boolean isTreeEql(TreeNode t1, TreeNode t2) {
        if (t1 == t2) return true;
        if (t1 == null || t2 == null) return false;
        return t1.val == t2.val && isTreeEql(t1.left, t2.left) && isTreeEql(t1.right, t2.right);
    }
    //KMP算法解：1.先拿到两个树的先序序列化2.接下来就是看看t1产生的str数组是否能匹配t2产生的match数组
    public static void preSerial(TreeNode head,ArrayList<String> preList){
        if(head==null){
            preList.add("null");
            return;
        }
        preList.add(head.val+"");
        preSerial(head.left,preList);
        preSerial(head.right,preList);
    }
    public static boolean checkSubTree1(TreeNode t1, TreeNode t2) {
        if (t2 == null) return true;
        if (t1 == null) return false;
        ArrayList<String> str = new ArrayList<>();
        preSerial(t1,str);
        ArrayList<String> match = new ArrayList<>();
        preSerial(t2,match);
        return getIndexOf(str,match)!=-1;
    }

    private static int getIndexOf(ArrayList<String> str, ArrayList<String> match) {
        if(match.size()>str.size()) return  -1;
        int x = 0,y = 0;
        int[] nexts = getNextArray(match);
        while (x<str.size()&&y<match.size()){
            if(str.get(x).equals(match.get(y))){
                x++;y++;
            }else if(nexts[y]==-1){
                x++;
            }else{
                y = nexts[y];
            }
        }
        return y==match.size()?x-y:-1;
    }

    private static int[] getNextArray(ArrayList<String> match) {
        if(match.size()==1) return new int[]{-1};
        int[] next = new int[match.size()];
        next[0] = -1;
        next[1] = 0;
        int preNext = 0;
        int cur = 2;
        while (cur<match.size()){
            if(match.get(preNext).equals(match.get(cur))){
                next[cur++] = ++preNext;
            }else if(preNext>0){
                preNext = next[preNext];
            }else{
                next[cur++] = 0;
            }
        }
        return next;
    }

    public static TreeNode generalRandomTree(int maxFloor,int maxVal,int curFloor){
        if(curFloor==maxFloor+1||Math.random()<0.3) return null;
        TreeNode head = new TreeNode((int) (Math.random()*maxVal));
        head.left = generalRandomTree(maxFloor,maxVal,curFloor+1);
        head.right = generalRandomTree(maxFloor,maxVal,curFloor+1);
        return head;
    }
    public static void preSerialTree(TreeNode head, StringBuilder builder){
        if(head==null) {
            builder.append("-null-");
            return;
        }
        builder.append("-").append(head.val).append("-");
        preSerialTree(head.left,builder);
        preSerialTree(head.right,builder);
    }
    public static void main(String[] args) {
        int testTimes = 10000;
        int i = 0;
        int t1MaxFloor = 10,t2MaxFloor = 10;
        int t1MaxVal = 2,t2MaxVal = 10;

        for(;i<testTimes;i++){
            TreeNode t1 = generalRandomTree(t1MaxFloor, t1MaxVal, 1);
            TreeNode t2 = generalRandomTree(t2MaxFloor, t2MaxVal, 1);
            boolean res1 = checkSubTree(t1, t2);
            boolean res2 = checkSubTree1(t1, t2);
            StringBuilder t1Str = new StringBuilder();
            StringBuilder t2Str = new StringBuilder();
            if(res1!=res2) {
                preSerialTree(t1,t1Str);
                System.out.println("t1:"+t1Str.toString());
                ArrayList<String> list = new ArrayList<>();
                preSerial(t1,list);

                preSerialTree(t2,t2Str);
                System.out.println("t2:"+t2Str.toString());
                System.out.println("res1:"+res1+",res2:"+res2);
                break;
            }
        }
        System.out.println(i==testTimes?"finish!!!":"fucking!!!");

    }
}
