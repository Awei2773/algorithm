package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-17 12:31
 */

/**
 * 请注意区分子串和子序列的不同，
 * 给定两个字符串str1和str2，求两个字符串的最长公共子串
 * 动态规划的空间压缩技巧！
 * 思路：
 * 枚举以某个位置结尾的最长公共子串长度，所有位置中最大那个必是答案。
 *
 * 一个做行，一个做列的对应模型
 * str1做行，str2做列
 * 一：base case
 * 1.第一行、第一列
 * 只看两个字符串的结尾位置是否相同，不同为0，相同为1
 * 2.普通位置
 * 结尾位置相同为1+左上角，结尾位置不同为0
 */
public class Code07_LCSubString {
    //建二维dp表
    public static String lcst1(String str1, String str2) {
        int rows = str1.length();
        int cols = str2.length();
        int[][] dp = new int[rows][cols];
        char[] str1Chars = str1.toCharArray();
        char[] str2Chars = str2.toCharArray();
        int maxLen = 0;
        int end = 0;
        for(int col = 0;col<cols;col++){
            dp[0][col] = str1Chars[0]==str2Chars[col]?1:0;
            if(dp[0][col]>maxLen){
                maxLen=dp[0][col];
                end = col;
            }
        }
        for(int row = 1;row<rows;row++){
            dp[row][0] = str1Chars[row]==str2Chars[0]?1:0;
            if(dp[row][0]>maxLen){
                maxLen=dp[row][0];
                end = 0;
            }
        }
        for(int row = 1;row<rows;row++){
            for(int col=1;col<cols;col++){
                dp[row][col] = str1Chars[row]==str2Chars[col]?1+dp[row-1][col-1]:0;
                if(dp[row][col]>maxLen){
                    maxLen=dp[row][col];
                    end = col;
                }
            }
        }
        return String.valueOf(str2Chars,end-maxLen+1,maxLen);
    }
    //空间优化技巧，每个位置最多依赖自己左上角一个位置，所以定义一个变量记住左上角的值
    //然后一条斜线一条斜线的求解就能够将答案求出，空间复杂度变成O(1)
    public static String lcst2(String str1, String str2) {
        int rows = str1.length();
        int cols = str2.length();
        char[] str1Chars = str1.toCharArray();
        char[] str2Chars = str2.toCharArray();
        //移动的时候，开始位置一开始在cols-1,然后向左走，走到最后一个位置就向下走
        int col = cols-1;
        int row = 0;
        int max = 0;
        int last = 0;
        int end = 0;
        while (row<rows){
            //开始位置求解，初始化变量
            int row1 = row;
            int col1 = col;
            //求一个斜线
            while (row<rows&&col<cols){
                last = str1Chars[row]==str2Chars[col]?
                        1+(row==0||col==0?0:last):0;
                if(last>max){
                    max = last;
                    end = col;
                }
                row++;
                col++;
            }
            row = row1;//回到求解前的位置
            col = col1;

            if(col-1>=0){
                col--;
            }else{
                row++;
            }
        }
        return String.valueOf(str2Chars,end-max+1,max);
    }

    public static void main(String[] args) {
        System.out.println(lcst2("hellosdfsdxsdf world","sdfx wsdfxcvorlsdfsdx"));
        System.out.println(lcst1("hellosdfsdxsdf world","sdfx wsdfxcvorlsdfsdx"));
    }
}
