package waigo.zuoshen.advance.MyCode;

/**
 * author waigo
 * create 2021-03-21 13:23
 */
/*
「快乐前缀」是在原字符串中既是 非空 前缀也是后缀（不包括原字符串自身）的字符串。
给你一个字符串 s，请你返回它的 最长快乐前缀。
如果不存在满足题意的前缀，则返回一个空字符串。
*/
public class Code04_MostHappyPrefix {
    public static String longestPrefix(String s) {
        if (s == null || s.length() < 2) return "";
        char[] sChars = s.toCharArray();
        int[] nexts = new int[s.length() + 1];
        nexts[0] = -1;
        nexts[1] = 0;
        int cur = 2;
        int preNext = 0;
        while (cur < nexts.length) {
            if (sChars[cur - 1] == sChars[preNext]) {
                nexts[cur++] = ++preNext;
            } else if (preNext > 0) {
                preNext = nexts[preNext];
            } else {
                nexts[cur++] = 0;
            }
        }
        return String.valueOf(sChars,0,nexts[nexts.length-1]);
    }

    public static void main(String[] args) {
        System.out.println(longestPrefix("2leve2l"));
    }
}
