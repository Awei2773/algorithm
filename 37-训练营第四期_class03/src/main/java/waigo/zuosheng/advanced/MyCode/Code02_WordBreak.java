package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-09 12:28
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 给定一个非空字符串 s 和一个包含非空单词的列表 wordDict，判定 s 是否可以被空格拆分为一个或多个在字典中出现的单词。
 * 说明：
 * <p>
 * 拆分时可以重复使用字典中的单词。
 * 你可以假设字典中没有重复的单词。
 * <p>
 * 示例 1：
 * <p>
 * 输入: s = "leetcode", wordDict = ["leet", "code"]
 * 输出: true
 * 解释: 返回 true 因为 "leetcode" 可以被拆分成 "leet code"。
 * <p>
 * 示例 2：
 * <p>
 * 输入: s = "applepenapple", wordDict = ["apple", "pen"]
 * 输出: true
 * 解释: 返回 true 因为 "applepenapple" 可以被拆分成 "apple pen apple"。
 * 注意你可以重复使用字典中的单词。
 * <p>
 * 示例 3：
 * <p>
 * 输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 * 输出: false
 */
public class Code02_WordBreak {

    public static boolean wordBreak(String s, List<String> wordDict) {
        if (s == null || s.length() == 0) return wordDict.size() == 0;
        HashSet<String> words = new HashSet<>(wordDict);
        return process(s, 0, words);
    }

    private static boolean process(String s, int cur, HashSet<String> words) {
        if (cur == s.length()) return true;
        for (int end = cur; end < s.length(); end++) {
            //[cur...end]做前缀是否能够拆出来
            String substring = s.substring(cur, end + 1);
            if (words.contains(substring) && process(s, end + 1, words)) {
                return true;
            }
        }
        return false;
    }
    public static boolean wordBreak2(String s, List<String> wordDict) {
        if (s == null || s.length() == 0) return wordDict.size() == 0;
        HashSet<String> words = new HashSet<>(wordDict);
        int N = s.length();
        boolean[] dp = new boolean[N+1];
        dp[N] = true;
        for (int cur = N-1; cur >=0; cur--) {
            for (int end = cur; end < s.length(); end++) {
                //[cur...end]做前缀是否能够拆出来
                String substring = s.substring(cur, end + 1);
                if (words.contains(substring) && dp[end+1]) {
                    dp[cur] = true;
                    break;
                }
            }
        }
        return dp[0];
    }
    //上面的方法中
    public static void main(String[] args) {
        String str = "leetcode";
        List<String> words = new ArrayList<>();
        words.add("leet");
        words.add("code");
        System.out.println(wordBreak2(str,words));
    }
}
