package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-30 10:36
 */

/**
 * 在一个字符串中找到没有重复字符子串中最长的长度。
 * 例如:
 * abcabcbb没有重复字符的最长子串是abc，长度为3
 * bbbbb，答案是b，长度为1
 * pwwkew，答案是wke，长度是3
 * 要求:答案必须是子串，"pwke" 是一个子字符序列但不是一个子字符串。
 *
 * 思路：
 * 枚举以每个位置结尾的时候的最长无重复字符的子串，答案必在其中。
 *
 * base case:
 * 第一个位置，必然为1，只有一个字符咋可能重复？
 * 普通位置:
 * 1.长度囊括的区域不可能比它前一个字符的答案还要长，不然前一个字符就搂不住限定了
 * 2.前一个字符囊括的区域中可能有和当前位置字符相同的
 *
 * 解决方法：
 * dp[i]意义：i位置包括在内往前点不重复的字符个数
 * 整一张表存一下每种字符最近出现的位置，然后Math.min(i-dp[i-1],i-lastAppear)
 */
public class Code06_LongestNoRepeat {
    public static int getLNRCSSLen(String str){
        if(str==null||str.length()<1) return 0;
        int[] lastAppear = new int[256];
        int N = str.length();
        char[] strChars = str.toCharArray();
        int[] dp = new int[N];//0~length-1位置的答案
        for (int i = 0; i < 256; i++) {
            lastAppear[i] = -1;
        }
        dp[0] = 1;
        lastAppear[strChars[0]] = 0;
        int ans = 1;
        String res = "";
        for (int i = 1; i < N; i++) {
            dp[i] = Math.min(dp[i-1]+1,i-lastAppear[strChars[i]]);
            if(dp[i]>ans){
                ans = dp[i];
                res = String.valueOf(strChars,i-ans+1,ans);
            }
            lastAppear[strChars[i]] = i;
        }
//        System.out.println(res);
        return ans;
    }

}
