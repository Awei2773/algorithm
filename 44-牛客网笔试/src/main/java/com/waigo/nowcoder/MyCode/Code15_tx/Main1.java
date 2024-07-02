package com.waigo.nowcoder.MyCode.Code15_tx;

import java.util.Scanner;

/**
 * author waigo
 * create 2021-09-26 20:07
 */
public class Main1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = Integer.valueOf(sc.nextLine());
        for(int i = 0;i<T;i++){
            int n = Integer.valueOf(sc.nextLine());
            int[] curArr = new int[n];
            String[] s = sc.nextLine().split(" ");
            for(int j = 0;j<n;j++){
                curArr[j] = Integer.valueOf(s[j]);
            }
            System.out.println(getMaxScore(curArr,n));
        }
    }

    //从n-1位置开始，每个dp[i]表示从i位置开始跳，到离开数组最大的积分
    private static int getMaxScore(int[] curArr, int n) {
        int[] dp = new int[n];
        int max = 0;
        for(int i = n-1;i>=0;i--){
            //吃了i位置的积分再开跳
            int nextPos = i + curArr[i];
            int curRes = curArr[i] + (nextPos >= n ? 0 : dp[nextPos]);
            max = Math.max(max, curRes);
            dp[i] = curRes;
        }
        return max;
    }
}
