package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-07-03 21:00
 */

import waigo.zuosheng.advanced.TeaCode.Code01_SDEandPM;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 给定一个非负数组arr，和一个正数m
 * 返回arr的所有子序列中累加和%m之后的最大值。
 */
public class Code01_MaxSubSeqModM {
    //1.方法一：
    //下标I 数组和SUM
    public int getSubSeqSumMaxModM(int[] arr, int m) {
        process(arr, 0, 0, m);
        return max;
    }

    //i:当前来到i位置，0~i-1都看过了
    //之前位置算出来的序列和为preSum
    int max;

    //时间复杂度O(MaxSum*N)
    private void process(int[] arr, int i, int preSum, int m) {
        if (i == arr.length) {
            max = Math.max(max, preSum % m);
            return;
        }
        //当前位置用或者不用
        process(arr, i + 1, preSum + arr[i], m);
        process(arr, i + 1, preSum, m);
    }

    //2.方法二：
    //下标I和M组一个二维表
    //时间复杂度O(M*N)
    public static int getSubSeqSumMaxModM1(int[] arr, int m) {
        int rows = m;
        int cols = arr.length;
        boolean[][] dp = new boolean[rows][cols];
        //1.第一列
        dp[arr[0] % m][0] = true;
        for (int col = 0; col < cols; col++) {
            dp[0][col] = true;
        }
        //2.普通位置
        //最后一个位置要么用，要么不用 dp[row][col-1]
        //要么用

        for (int col = 1; col < cols; col++) {
            for (int row = 1; row < rows; row++) {
                int cur = arr[col] % m;//(a+b)%m == (a%m+b%m)%m
                int p1 = row + m - cur;
                int p2 = row - cur;
                dp[row][col] = dp[row][col - 1] || (p1 >= 0 && p1 < rows && dp[p1][col - 1]) || (p2 >= 0 && p2 < rows && dp[p2][col - 1]);
            }
        }
        //最后一列，从下到上，第一个T就是答案
        int row = rows - 1;
        for (; row >= 0; row--) {
            if (dp[row][cols - 1]) break;

        }
        return row;
    }
    //第三种方法：NP思想
    public static int getSubSeqSumMaxModM2(int[] arr, int m){
        //左右拆一半，左边穷举所有序列和
        //右边穷举所有序列和
        //结果可能性分析：左边、右边、横跨
        //复杂度O(2^(N/2))
        TreeSet<Integer> leftSubSeqSum = new TreeSet<>();
        TreeSet<Integer> rightSubSeqSum = new TreeSet<>();
        //一半一半去处理
        int mid = (arr.length-1)>>1;
        process2(arr,leftSubSeqSum,0,mid,0,m);
        process2(arr,rightSubSeqSum,mid+1,arr.length-1,0,m);
        int max = 0;
        for(Integer ssum:leftSubSeqSum){
            Integer floor = rightSubSeqSum.floor(m - 1 - ssum);
            max = Math.max(max, (ssum+(floor==null?0:floor))%m);
        }
        return max;
    }

    private static void process2(int[] arr,TreeSet<Integer> subSeqSum, int cur, int end,int preSum,int m) {
        if(cur==end+1){
            subSeqSum.add(preSum%m);
            return;
        }
        process2(arr,subSeqSum,cur+1,end,preSum,m);
        process2(arr,subSeqSum,cur+1,end,preSum+arr[cur],m);
    }

    public static void main(String[] args) {
    }

}
