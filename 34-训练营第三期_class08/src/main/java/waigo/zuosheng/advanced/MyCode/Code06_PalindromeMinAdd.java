package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-01 9:23
 */

import java.util.Arrays;

/**
 * 给定一个字符串，如果只能在后面添加字符，最少添加几个能让字符串整体都是回文串。
 */
public class Code06_PalindromeMinAdd {
    public static int minAdd(String str) {
        if (str == null || str.length() < 2) return 0;
        return process2(str.toCharArray(), 0, str.length() - 1);
    }

    //只能在后面添加
    private static int process(char[] strChars, int l, int r) {
        if (l >= r) return 0;
        return strChars[l] == strChars[r] ? process(strChars, l + 1, r - 1) : 1 + process(strChars, l + 1, r);
    }

    //前后都可以添加
    private static int process2(char[] strChars, int l, int r) {
        if (l >= r) return 0;
        return strChars[l] == strChars[r] ? process2(strChars, l + 1, r - 1) : 1 + Math.min(process2(strChars, l + 1, r), process2(strChars, l, r - 1));
    }

    //dp
    public static int minInsertions(String s) {
        if (s == null || s.length() < 2) return 0;
        char[] strChars = s.toCharArray();
        int N = s.length();
        int[][] dp = new int[N][N];
        //一条斜线一条斜线的填，斜线从右下到左上，斜线整体从左到右
        for (int row = N - 2; row >= 0; row--) {
            //定位一条斜线
            int tempRow = row;
            for (int col = N-1; col >=N-row-1 ; col--) {
                dp[tempRow][col] = strChars[tempRow] == strChars[col] ?
                        dp[tempRow + 1][col - 1] : 1 + Math.min(dp[tempRow + 1][col],dp[tempRow][col-1]);
                tempRow--;
            }
        }
        /**
         * 直接将补全的字符串打印出来
         */
        char[] res = new char[N+dp[0][N - 1]];
        int resl=0,resr=res.length-1;//两个写指针
        int sl=0,sr=N-1;//两个读字符串的指针
        while (sl<=sr){
            if(strChars[sl]==strChars[sr]){
                res[resl++] = strChars[sl++];
                res[resr--] = strChars[sr--];
            }else if(dp[sl][sr-1]<dp[sl+1][sr]){
                res[resl++] = strChars[sr];//在左边补上右边那个字符
                res[resr--] = strChars[sr--];
            }else{
                res[resr--] = strChars[sl];
                res[resl++] = strChars[sl++];
            }
        }
        System.out.println(String.valueOf(res));
        /*--------------------------------------*/
        return dp[0][N - 1];
    }

    public static void main(String[] args) {
        System.out.println(minInsertions("leetcode"));
    }
}
