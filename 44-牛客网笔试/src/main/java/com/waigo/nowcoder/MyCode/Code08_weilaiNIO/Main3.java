package com.waigo.nowcoder.MyCode.Code08_weilaiNIO;

/**
 * author waigo
 * create 2021-08-31 19:53
 */
public class Main3 {
    //动态规划
    //第一行，第一列，只要末位不同就为1，相同就为0
    //普通位置，如果不同就为1，如果相同就看左上角
    //dp[i][j]表示必须以i，j结尾的子串合法与否
    public int countSubstr (String s, String t) {
        if(s==null||t==null||s.length()==0||t.length()==0) return 0;
        int rows = s.length();
        int cols = t.length();
        //先求一个dp,dp[i][j]表示以i，j结尾，相等子串长度
        int[][] eq = getEq(s,t,rows,cols);
        //s做行，t做列
        int[][] dp = new int[rows+1][cols+1];
        int res = 0;
        //第一行
        for(int row = 1;row<=rows;row++){
            for(int col = 1;col<=cols;col++){
                if(s.charAt(row-1)!=t.charAt(col-1)){
                    //看一下前面相等的长度，一次收集完
                    int frontEq = eq[row-1][col-1];
                    dp[row][col] = 1 + frontEq;
                }else if(dp[row-1][col-1]>0){//当前相等就看前一位是否相等
                    dp[row][col] = dp[row-1][col-1];
                }
                res+=dp[row][col];
            }
        }
        return res;
    }

    private int[][] getEq(String s, String t, int rows, int cols) {
        int[][] eq = new int[rows+1][cols+1];
        for(int row = 1;row<=rows;row++){
            for(int col = 1;col<=cols;col++){
                if(s.charAt(row-1)==t.charAt(col-1)){
                    eq[row][col] = 1 + eq[row-1][col-1];
                }
            }
        }
        return eq;
    }

    public static void main(String[] args) {
//        System.out.println(new Main3().a());
    }

}
