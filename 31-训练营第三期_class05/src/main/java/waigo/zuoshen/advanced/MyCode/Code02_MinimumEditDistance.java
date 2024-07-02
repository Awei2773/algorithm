package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-15 19:16
 */

/**
 * 给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。
 * 你可以对一个单词进行如下三种操作：
 * <p>
 * 插入一个字符
 * 删除一个字符
 * 替换一个字符
 * 示例 1：
 * <p>
 * 输入：word1 = "horse", word2 = "ros"
 * 输出：3
 * 解释：
 * horse -> rorse (将 'h' 替换为 'r')
 * rorse -> rose (删除 'r')
 * rose -> ros (删除 'e')
 * 示例 2：
 * <p>
 * 输入：word1 = "intention", word2 = "execution"
 * 输出：5
 * 解释：
 * intention -> inention (删除 't')
 * inention -> enention (将 'i' 替换为 'e')
 * enention -> exention (将 'n' 替换为 'x')
 * exention -> exection (将 'n' 替换为 'c')
 * exection -> execution (插入 'u')
 *  
 * 提示：
 * 0 <= word1.length, word2.length <= 500
 * word1 和 word2 由小写英文字母组成
 */
public class Code02_MinimumEditDistance {
    public static int minDistance(String word1, String word2) {
        if (word1.equals(word2)) return 0;
        int N1 = word1.length();
        int N2 = word2.length();
        if (N1 == 0) return N2;
        if (N2 == 0) return N1;
        return N2 > N1 ? process(word1.toCharArray(), word2.toCharArray()) : process(word2.toCharArray(), word1.toCharArray());
    }

    //右边那个len>=左边那个>=1
    private static int process(char[] origin, char[] target) {
        int rows = origin.length;
        int cols = target.length;
        //dp[row][col]表示origin[0...row]变成target[0...col]的最小操作数
        int[][] dp = new int[rows][cols];
        dp[0][0] = origin[0] == target[0] ? 0 : 1;
        //第一行row=0
        for (int col = 1; col < cols; col++) {
            dp[0][col] = origin[0] == target[col] ? col : 1 + dp[0][col - 1];
        }
        //第一列col=0
        for (int row = 1; row < rows; row++) {
            dp[row][0] = origin[row] == target[0] ? row : 1 + dp[row - 1][0];
        }
        //填表,左->右
        for(int row=1;row<rows;row++){
            for(int col=1;col<cols;col++){
                if(origin[row]==target[col]){
                    dp[row][col] = dp[row-1][col-1];
                }else{
                    int p1 = dp[row-1][col-1];//将最后一个替换相同
                    int p2 = dp[row-1][col];//将origin剪短一个
                    int p3 = dp[row][col-1];//将origin后面补上一个target[col]
                    dp[row][col]=Math.min(Math.min(p1,p2),p3)+1;
                }
            }
        }
        return dp[rows-1][cols-1];
    }

    public static void main(String[] args) {
        System.out.println(minDistance("horse","ros"));
    }
}
