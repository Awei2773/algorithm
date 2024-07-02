package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-27 12:45
 */

/**
 * 给定一个整数N，代表你有1～N这些数字。在给定一个整数K。你可以随意排列这些数字，但是每一种排列都有若干个逆序对。
 * 返回有多少种排列，正好有K个逆序对
 *
 * 例子1:
 * Input: n = 3, k = 0
 * Output: 1
 * 解释：
 * 只有[1,2,3]这一个排列有0个逆序对。
 *
 * 例子2:
 * Input: n = 3, k = 1
 * Output: 2
 * 解释
 * [3,2,1]有(3,2)、(3,1)、(2,1)三个逆序对，所以不达标。
 * 达标的只有：
 * [1,3,2]只有(3,2)这一个逆序对，所以达标。
 * [2,1,3]只有(2,1)这一个逆序对，所以达标。
 */
//首先记住，这个逆序对，都称之为对了，绝对就是两个数，不要理解成逆序的序列了
//上来一看有两个输入参数，猜测是行列对应模型，然后再从行列对应出发去思考
public class Code02_KInversePairs {
    //返回1~N这N个数的全排列中有K个逆序对的个数
    public static int getWays(int N,int K){
        if(N<=1||K<0) return 0;
        //将N作为行，K作为列
        int rows = N+1;//第0行无效全是0
        int cols = K+1;
        int[][] dp = new int[rows][cols];
        for(int row = 1;row<rows;row++){
            dp[row][0] = 1;
        }
        for(int row = 2;row<rows;row++){
            for(int col = 1;col<cols;col++){
                /*for(int i = 0;i<Math.min(row,col+1);i++){
                    dp[row][col]+=dp[row-1][col-i];
                }*/
                //优化枚举，每个位置的dp等于左边那个位置加上上边那个位置
                //K>=N,dp[row][col] = dp[row][col-1]-dp[row-1][col-row+1]+dp[row-1][col]
                //K<N,dp[row][col] = dp[row][col-1]+dp[row-1][col]
                dp[row][col] = dp[row][col-1]-(col>=row?dp[row-1][col-row]:0)+dp[row-1][col];
            }
        }
        return dp[N][K];
    }

    public static void main(String[] args) {
        System.out.println(getWays(8,27));
    }
}
