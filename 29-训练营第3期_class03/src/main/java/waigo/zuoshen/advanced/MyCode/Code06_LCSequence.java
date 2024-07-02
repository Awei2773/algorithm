package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-17 12:31
 */

/**
 * 请注意区分子串和子序列的不同，给定两个字符串str1和str2，
 * 求两个字符的最长公共子序列
 * 动态规划的空间压缩技巧！
 *
 * 思路：
 * 一个样本做行一个样本做列的对应模型
 * dp[row][col]表示str1的[0...row]这一段和str2[0...col]这一段的最长公共子序列的长度
 *
 * 一、base case:
 * 第一行：结尾位置相同为1，不同看左一个位置
 * 第一列：结尾位置相同为1，不同看上一个位置
 * 二、普通位置
 * 1.结尾相同
 * 答案为1+左上角
 * 2.结尾不同
 * 答案有三种可能性p1:左边格子p2:上面格子p3:左上角格子，取最大那个
 *
 * step 1:
 * 整出这样一张dp表
 * step 2:
 * 从末尾位置开始，尽量往不会减小最长子序列的左或者上走，如果说往左或者往上都会使得最长子序列减小，
 * 那么说明当前位置必然行列是相等的，而且指向了同一个子序列。然后这个位置收一个字符就向左上角走，
 * 已经和当前位置没关系了。
 */
public class Code06_LCSequence {
    public static String lcse(String str1, String str2) {
        int rows = str1.length();
        int cols = str2.length();
        int[][] dp = new int[rows][cols];
        char[] str1Chars = str1.toCharArray();
        char[] str2Chars = str2.toCharArray();
        for (int row = 0; row < rows; row++) {
            dp[row][0] = str1Chars[row]==str2Chars[0]?1:(row-1>=0?dp[row-1][0]:0);
        }
        for (int col = 1; col < cols; col++) {
            dp[0][col] = str1Chars[0]==str2Chars[col]?1:dp[0][col-1];
        }
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                if(str1Chars[row]==str2Chars[col]){
                    dp[row][col] = 1+dp[row-1][col-1];
                }else{
                    int p1 = dp[row-1][col];
                    int p2 = dp[row][col-1];
                    int p3 = dp[row-1][col-1];
                    dp[row][col] = Math.max(p1,Math.max(p2,p3));
                }
            }
        }
        //造答案
        char[] resStr = new char[dp[rows-1][cols-1]];
        int row = rows-1;
        int col = cols-1;
        int write = resStr.length-1;
        while (row>=0&&col>=0){
            if(row-1>=0&&dp[row-1][col]==dp[row][col]){
                row--;
            }else if(col-1>=0&&dp[row][col-1]==dp[row][col]){
                col--;
            }else{
                resStr[write--] = str1Chars[row];
                row--;
                col--;
            }
        }
        return String.valueOf(resStr,0,resStr.length);
    }

    public static void main(String[] args) {
        String str1 = "A1BC2D3EFGH45I6JK7LMN";
        String str2 = "12OPQ3RST4U5V6W7XYZ";
        System.out.println(lcse(str1, str2));
    }
}
