package waigo.zuoshen.basic.TeaCode;
//求两个字符串的最长公共子序列的长度
//思路：将两个字符串a,b长度一个做x，一个做y展开一张二维表，每个格子意义表示a的子串长度<=x,b的子串长度<=y的最长子序列长度
//列base case
//1.x=0,y=0~length-1,两字符串只有一个有字符，那么最长公共子序列长度肯定为0
//2.同理：y=0,x = 0~length-1,最长公共子序列为0
//3.返回的值是x=length-1,y=length-1位置的最长子序列，也就是dp[a.length]dp[b.length]
//4.分析一个普通位置(x,y)，将它的问题往下抛
//4.1如果两个子串的最长子序列不以x位置和y位置结尾，比如求123ab和123cd中(4,4)的最长子序列，很明显最长子序列为123，
// 子序列结尾处是3，注意x，y表示的是长度不是下标，那么dp[x][y] = dp[x-1][y-1]
//4.2 两个最长子序列都以这个位置结尾，那么这个位置就不再重要，因为确定了这个字符dp[x][y] = dp[x-1][y-1]+1,1就是确定的字符
// 让长度都减一后的最长子序列长度加上1就等于此时的最长长度，此时判断条件是最后一位字符相同
//4.3 a字符串以这个位置结尾，b字符串不以这个位置结尾，dp[x][y] = dp[x][y-1]
//4.4 b字符串以这个位置结尾，a字符串不以这个位置结尾，dp[x][y] = dp[x-1][y]
//总结，最后一个位置只会有这四种可能，都能将问题下抛，变成求左边，左上角，上边的值。
//所以这张二维表应该从第二行，第二列开始填值，最终充满表后返回右下角那个位置的值，问题得解
public class Code05_LongestCommonSubSequcece {

	public static int lcse(char[] str1, char[] str2) {
		int[][] dp = new int[str1.length][str2.length];
		dp[0][0] = str1[0] == str2[0] ? 1 : 0;
		for (int i = 1; i < str1.length; i++) {
			dp[i][0] = Math.max(dp[i - 1][0], str1[i] == str2[0] ? 1 : 0);
		}
		for (int j = 1; j < str2.length; j++) {
			dp[0][j] = Math.max(dp[0][j - 1], str1[0] == str2[j] ? 1 : 0);
		}
		for (int i = 1; i < str1.length; i++) {
			for (int j = 1; j < str2.length; j++) {
				dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				if (str1[i] == str2[j]) {
					dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
				}
			}
		}
		return dp[str1.length - 1][str2.length - 1];
	}

	public static void main(String[] args) {

	}

}
