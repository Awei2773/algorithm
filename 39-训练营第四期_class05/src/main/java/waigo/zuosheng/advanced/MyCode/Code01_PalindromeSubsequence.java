package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-25 9:11
 */

import java.util.Arrays;

/**
 * 516. 最长回文子序列
 * 给定一个字符串 s ，找到其中最长的回文子序列，并返回该序列的长度。可以假设 s 的最大长度为 1000 。
 * <p>
 * <p>
 * <p>
 * 示例 1:
 * 输入:
 * <p>
 * "bbbab"
 * 输出:
 * <p>
 * 4
 * 一个可能的最长回文子序列为 "bbbb"。
 * <p>
 * 示例 2:
 * 输入:
 * <p>
 * "cbbd"
 * 输出:
 * <p>
 * 2
 * 一个可能的最长回文子序列为 "bb"。
 * <p>
 * 提示：
 * <p>
 * 1 <= s.length <= 1000
 * s 只包含小写英文字母
 */
public class Code01_PalindromeSubsequence {
    //    解法一：行列对应模型
    public int longestPalindromeSubseq1(String s) {
        if (s == null || s.length() == 0) return 0;
        return longestCommonSubseq(reverse(s), s.toCharArray());
    }

    //将最长回文子串改成最长公共子序列问题
    public int longestCommonSubseq(char[] a, char[] b) {
        int rows = a.length;//a做行
        int cols = b.length;//b做列
        //dp[row][col]表示a[0...row]和b[0...col]的最长公共子序列
        int[][] dp = new int[rows][cols];
        dp[0][0] = a[0] == b[0] ? 1 : 0;
        //行
        for (int col = 1; col < cols; col++) {
            dp[0][col] = a[0] == b[col] ? 1 : dp[0][col - 1];
        }
        //列
        for (int row = 1; row < rows; row++) {
            dp[row][0] = a[row] == b[0] ? 1 : dp[row - 1][0];
        }
        //普通位置
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                dp[row][col] = a[row] == b[col] ? (1 + dp[row - 1][col - 1]) : (Math.max(Math.max(dp[row - 1][col], dp[row][col - 1]), dp[row - 1][col - 1]));
            }
        }
        return dp[rows - 1][cols - 1];
    }

    public char[] reverse(String s) {
        char[] sa = s.toCharArray();
        int N = sa.length;
        char[] re = new char[N];
        int w = 0;
        for (int i = sa.length - 1; i >= 0; i--) {
            re[w++] = sa[i];
        }
        return re;
    }

    //    解法二：范围尝试模型
    public int longestPalindromeSubseq(String s) {
        char[] sChars = s.toCharArray();
        //范围尝试模型，0~len-1
        int rows = sChars.length;
        int cols = rows;
        //dp[row][col]表示sChars[row...col]的最长回文子序列row:L,col:R
        int[][] dp = new int[rows][cols];
        //中斜线开始往右上角填
        int row = 0, col = 0;
        while (row < rows && col < cols) {
            int tRow = row;
            int tCol = col;
            //一条斜线
            while (tRow < rows && tCol < cols) {
                if (tRow == tCol) {
                    dp[tRow][tCol] = 1;
                } else if (sChars[tRow] == sChars[tCol]) {
                    dp[tRow][tCol] = 2 + (tRow + 1 <= tCol - 1 ? dp[tRow + 1][tCol - 1] : 0);
                } else {
                    int p1 = tRow + 1 <= tCol ? dp[tRow + 1][tCol] : 0;
                    int p2 = tCol - 1 >= tRow ? dp[tRow][tCol - 1] : 0;
                    int p3 = tRow + 1 <= tCol && tCol - 1 >= tRow ? dp[tRow + 1][tCol - 1] : 0;
                    dp[tRow][tCol] = Math.max(Math.max(p1, p2), p3);
                }
                tRow++;
                tCol++;
            }
            row = 0;
            col++;
        }
        return dp[0][cols - 1];
    }

    public static void main(String[] args) {
        System.out.println("#x#x#x".replaceAll("#",""));
    }
}
