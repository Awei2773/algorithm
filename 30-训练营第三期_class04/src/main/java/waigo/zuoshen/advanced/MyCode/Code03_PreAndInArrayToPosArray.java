package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-15 10:07
 */

import java.util.Arrays;

/**
 * 已知一棵二叉树中没有重复节点，并且给定了这棵树的中序遍历数组和先序遍历 数组，返回后序遍历数组。
 * 比如给定:
 * int[] pre = { 1, 2, 4, 5, 3, 6, 7 };
 * int[] in = { 4, 2, 5, 1, 6, 3, 7 }; 返回:
 * {4,5,2,6,7,3,1}
 */
public class Code03_PreAndInArrayToPosArray {
    private static void getPosArray(
            int[] pre,int preStart,int preEnd,
            int[] in,int inStart,int inEnd,
            int[] pos,int posStart,int postEnd
            ){
        if(preStart<0||inStart<0||posStart<0) return;
        if(preStart>preEnd||inStart>inEnd||posStart>postEnd) return;
        pos[postEnd] = pre[preStart];
        //在中序中找到此时的头
        int i = inStart;
        for (; i <= inEnd; i++) {
            if(in[i]==pre[preStart]) break;
        }
        //inStart~i-1是左树范围,i+1~inEnd是右树范围
        int leftTreeLen = i-inStart;
        int rightTreeLen = inEnd-i;
        //左树处理好
        int leftEnd = leftTreeLen - 1 + posStart;
        getPosArray(pre,preStart+1,leftTreeLen+preStart,
                in,inStart,i-1,
                pos,posStart, leftEnd);
        //右树处理好
        getPosArray(pre,preEnd+1-rightTreeLen,preEnd,
                in,i+1,inEnd,
                pos,leftEnd+1,postEnd-1);
    }
    public static void easyGetPos(int[] pre,int[] in,int[] pos){
        getPosArray(pre,0,pre.length-1,in,0,in.length-1,pos,0,pos.length-1);
    }
    public static void main(String[] args) {
        int[] pre = { 1, 2, 4, 5, 3, 6, 7 };
        int[] in = { 4, 2, 5, 1, 6, 3, 7 };
        int[] pos = new int[pre.length];
        easyGetPos(pre,in,pos);
        System.out.println(Arrays.toString(pos));
    }
}
