package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-24 11:45
 */

/**
 * 给定三个字符串str1、str2和aim，如果aim包含且仅包含来自str1和str2的所有字符，
 * 而且在aim中属于str1的字符之间保持原来在str1中的顺序，属于str2的字符之间保持
 * 原来在str2中的顺序，那么称aim是str1和str2的交错组成。实现一个函数，判断aim是
 * 否是str1和str2交错组成
 * 【举例】 str1="AB"，str2="12"。那么"AB12"、"A1B2"、"A12B"、"1A2B"和"1AB2"等都是
 * str1 和 str2 的 交错组成
 */
public class Code08_StringCross {
    public static boolean isStringCross(String str1,String str2,String aim){
        if(aim.length()!=str1.length()+str2.length()) return false;
        if(aim.length()==0) return true;
        if (aim.length()==1&&(aim.equals(str1)||aim.equals(str2))) return true;
        return process(str1.toCharArray(),0,str2.toCharArray(),0,aim.toCharArray());

    }
    //str1已经看过[0...s1-1]了，现在看s1
    //str2已经看过[0...s2-1]了，现在看s2
    //是否能够组成aim
    private static boolean process(char[] str1, int s1, char[] str2, int s2, char[] aim) {
        if(s1==str1.length&&s2==str2.length) return true;
        //aim此时要的是s1+s2处的值
        //两种情况，s1处能够满足aim
        boolean res = false;
        if(s1<str1.length&&str1[s1]==aim[s1+s2]){
            res = process(str1,s1+1,str2,s2,aim);
        }
        if(s2<str2.length&&str2[s2]==aim[s1+s2]){
            res |= process(str1,s1,str2,s2+1,aim);
        }
        return res;
    }
    //dp
        public static boolean dpWays(String s1,String s2,String s3){
        if(s3.length()!=s1.length()+s2.length()) return false;
        if(s3.length()==0) return true;
        if (s3.length()==1&&(s3.equals(s1)||s3.equals(s2))) return true;
        int rows = s1.length() + 1;
        int cols = s2.length() + 1;
        char[] str1Chars = s1.toCharArray();
        char[] str2Chars = s2.toCharArray();
        char[] aimChars = s3.toCharArray();
        boolean[][] dp = new boolean[rows][cols];//s1:0~N，s2:0~N
        dp[rows-1][cols-1] = true;
        //最后一行
        for (int col = cols-2; col >=0; col--) {
            if(str2Chars[col]==aimChars[rows-1+col]){
                dp[rows-1][col] = dp[rows-1][col+1];
            }
        }
        //最后一列
        for(int row = rows-2;row>=0;row--){
            if(str1Chars[row]==aimChars[row+cols-1]){
                dp[row][cols-1] = dp[row+1][cols-1];
            }
        }
        for (int row = rows-2; row >=0; row--) {
            for (int col = cols-2; col >=0 ; col--) {
                if(str1Chars[row]==aimChars[row+col]){
                    dp[row][col] |= dp[row+1][col];
                }
                if(str2Chars[col]==aimChars[row+col]){
                    dp[row][col] |= dp[row][col+1];
                }
            }
        }
        return dp[0][0];
    }
    public static void main(String[] args) {
        System.out.println(dpWays("AAACBBC12","3AAAACDR23","3AAAAAAACBBCCDR1223"));
    }
}
