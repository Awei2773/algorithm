package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-05 10:33
 */

/**
 * 给定一个字符串 s 和一个字符串 t ，计算在 s 的子序列中 t 出现的个数。
 * 字符串的一个 子序列 是指，通过删除一些（也可以不删除）字符且不干扰剩余字符相对位置所组成的新字符串。
 * （例如，"ACE" 是 "ABCDE" 的一个子序列，而 "AEC" 不是）
 * 题目数据保证答案符合 32 位带符号整数范围。
 * 示例 1：
 *
 * 输入：s = "rabbbit", t = "rabbit"
 * 输出：3
 * 解释：
 * 如下图所示, 有 3 种可以从 s 中得到 "rabbit" 的方案。
 * (上箭头符号 ^ 表示选取的字母)
 * rabbbit
 * ^^^^ ^^
 * rabbbit
 * ^^ ^^^^
 * rabbbit
 * ^^^ ^^^
 * 思路：一个样本做行一个样本做列的对应模型
 */
public class Code04_DistinctSubseq {
    public static int numDistinct(String s, String t) {
        if(s==null||t==null||s.length()==0||t.length()==0||t.length()>s.length()) return 0;
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        int cols = s.length();
        int rows = t.length();
        int[][] dp = new int[rows][cols];
        dp[0][0] = sChars[0]==tChars[0]?1:0;
        for (int col = 1; col < cols; col++) {
            dp[0][col] = (sChars[col]==tChars[0]?1:0)+dp[0][col-1];
        }
        for (int col = 1; col < cols; col++) {
            for (int row = 1; row < rows; row++) {
                if(row>col) break;//下面都是0，剪枝
                //可能性汇总
                //末尾相同
                dp[row][col] = dp[row][col-1]+(sChars[col]==tChars[row]?dp[row-1][col-1]:0);
            }
        }
        return dp[rows-1][cols-1];
    }
    public static void main(String[] args) {
        System.out.println(numDistinct("babgbag","bag"));
    }
}
