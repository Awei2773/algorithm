package com.waigo.newcoder;

import java.util.Arrays;

/**
 * author waigo
 * create 2021-08-22 21:06
 */
public class NC126_minMoney {
    public int minMoney (int[] arr, int aim) {
        if(arr==null||arr.length==0) return arr.length==0?0:-1;
        //1.aim   2.货币数   3.数组下标  4.货币组成的值
        //idx:0~n-1
        //aim:0~aim
        int[][] dp = new int[arr.length][aim+1];
        for(int i = 0;i<arr.length;i++){
            for(int j = 0;j<=aim;j++){
                dp[i][j] = -2;//-2表示没填过
            }
        }
        return process(0,aim,arr,dp);
    }
    //返回最少货币数，aim表示需要组成的大小,[0,idx)都看过了
    //为了保证只要能够凑出就比凑不出更优，约定凑不出为Integer.MAX_VALUE;(错误，因为涉及到加法，需要小心加法溢出)
    public int process(int idx,int aim,int[] arr,int[][] dp){
        if(aim<=0||idx==arr.length){//小于0就组不成了，等于0直接返回0
            return aim==0?0:-1;
        }
        if(dp[idx][aim]!=-2){
            return dp[idx][aim];
        }
        //当前用多少张？aim/arr[idx]张，就是不让aim<0
        int n = aim/arr[idx];
        int min = Integer.MAX_VALUE;
        int cur;
        for(int i = 0;i<=n;i++){
            cur = process(idx+1,aim-(i*arr[idx]),arr,dp);//一定！！！一定！！！注意返回值范围
            cur = cur==-1?-1:cur+i;
            if(min==-1){//bug:cur==-1,而min不为-1
                min = cur;
            }else if(cur==-1){
                min = min==Integer.MAX_VALUE?cur:min;
            }else{
                min = Math.min(min,cur);
            }
        }
        dp[idx][aim] = min;
        return min;
    }
    //自顶向下：
    //自顶向上：dp[i]，需要拼出i元的时候最少的张数
    //dp[0] = 0,dp[i] = 1+dp[每种尝试用一张后的钱]
    //做规定，没有答案就设置为Integer.MAX_VALUE;
    /*public int minMoney (int[] arr, int aim) {
        //i：0~aim
        if(arr==null||arr.length==0) return aim==0?0:-1;
        int[] dp = new int[aim+1];
        Arrays.fill(dp,Integer.MAX_VALUE);
        dp[0] = 0;
        for(int i = 1;i<=aim;i++){
            for(int j = 0;j<arr.length;j++){
                int curYuan = i - arr[j];
                dp[i] = Math.min(dp[i],curYuan<0||dp[curYuan]==Integer.MAX_VALUE?Integer.MAX_VALUE:1+dp[curYuan]);
            }
        }
        return dp[aim]==Integer.MAX_VALUE?-1:dp[aim];
    }*/
    public static void main(String[] args) {
        System.out.println(new NC126_minMoney().minMoney(new int[]{5,2,3},17));
    }
}
