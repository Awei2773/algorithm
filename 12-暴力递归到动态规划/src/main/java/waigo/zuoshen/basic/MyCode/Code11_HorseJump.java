package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-03-14 8:25
 */
/*
已知一个 NxN 的国际象棋棋盘，棋盘的行号和列号都是从 0 开始。即最左上角的格子记为 (0, 0)，最右下角的记为 (N-1, N-1)。 
现有一个 “马”（也译作 “骑士”）位于 (r, c) ，并打算进行 K 次移动。 
如下图所示，国际象棋的 “马” 每一步先沿水平或垂直方向移动 2 个格子，然后向与之相垂直的方向再移动 1 个格子，共有 8 个可选的位置。
现在 “马” 每一步都从可选的位置（包括棋盘外部的）中独立随机地选择一个进行移动，直到移动了 K 次或跳到了棋盘外面。
求移动结束后，“马” 仍留在棋盘上的概率。
*/
//额，第一版作答理解错题意了，原来所有的跳法就是8^K，就是跳出去了还能接着跳，不过只要跳出去的就不会跳回来了、
//这样倒是和马踏棋盘一样了，只用看有几种成功跳的

//N 的取值范围为 [1, 25] 注意数据状况，不能最后再除以8^K,因为8^K会数据溢出的，所以应该每次都除以8，除以K次就变相算是除以了8^K了
//K 的取值范围为 [0, 100]
//开始时，“马” 总是位于棋盘上
public class Code11_HorseJump {
    public static double horseJumpKLivePro(int N, int K, int r, int c) {
        int[] info = process(N, K, r, c);
        System.out.println("truth:" + info[0] + ",failure:" + info[1]);
        return (info[0] * 1.0) / (info[0] + info[1]);
    }

    //从r,c位置跳K步，跳出和不跳出棋盘的走法存在一个数组中返回
    //int[0] = 走成功的走法，int[1] = 不成功的走法
    private static int[] process(int N, int K, int r, int c) {
        boolean checkBound = r < 0 || r >= N || c < 0 || c >= N;
        if (K == 0) {
            int truth = checkBound ? 0 : 1;
            int failure = truth == 1 ? 0 : 1;
            return new int[]{truth, failure};
        }
        if (checkBound) {
            //没走完，但是已经越界
            return new int[]{0, 1};
        }
        int[] l1 = process(N, K - 1, r - 2, c - 1);
        int[] l2 = process(N, K - 1, r - 1, c - 2);
        int[] l3 = process(N, K - 1, r + 1, c - 2);
        int[] l4 = process(N, K - 1, r + 2, c - 1);
        int[] r1 = process(N, K - 1, r - 2, c + 1);
        int[] r2 = process(N, K - 1, r - 1, c + 2);
        int[] r3 = process(N, K - 1, r + 1, c + 2);
        int[] r4 = process(N, K - 1, r + 2, c + 1);
        return new int[]{l1[0] + l2[0] + l3[0] + l4[0] + r1[0] + r2[0] + r3[0] + r4[0], l1[1] + l2[1] + l3[1] + l4[1] + r1[1] + r2[1] + r3[1] + r4[1]};
    }

    public static double horseJumpKLivePro1(int N, int K, int r, int c) {
        return process1(N, K, r, c);
    }

    //从r,c位置跳K步，返回留在棋盘中的概率
    private static double process1(int N, int K, int r, int c) {
        boolean checkBound = r < 0 || r >= N || c < 0 || c >= N;
        if (K == 0) {
            return checkBound ? 0.0 : 1.0;
        }
        if (checkBound) {
            return 0.0;
        }

        return (process1(N, K - 1, r - 2, c - 1)
                + process1(N, K - 1, r - 1, c - 2)
                + process1(N, K - 1, r + 1, c - 2)
                + process1(N, K - 1, r + 2, c - 1)
                + process1(N, K - 1, r - 2, c + 1)
                + process1(N, K - 1, r - 1, c + 2)
                + process1(N, K - 1, r + 1, c + 2)
                + process1(N, K - 1, r + 2, c + 1))/8.0;
    }

    public static double dp(int N, int K, int r, int c) {
        double[][][] dp = new double[K + 1][N + 4][N + 4];//r=0-->r=-2,offset = 2;
        int offset = 2;
        //base case打表
        for (int row = offset; row < N + offset; row++) {
            for (int col = offset; col < N + offset; col++) {
                dp[0][row][col] = 1;
            }
        }
        for (int step = 1; step <= K; step++) {
            for (int row = offset; row < N + offset; row++) {
                for (int col = offset; col < N + offset; col++) {
                    dp[step][row][col] = (dp[step - 1][row - 2][col - 1]
                            + dp[step - 1][row - 1][col - 2]
                            + dp[step - 1][row + 1][col - 2]
                            + dp[step - 1][row + 2][col - 1]
                            + dp[step - 1][row - 2][col + 1]
                            + dp[step - 1][row - 1][col + 2]
                            + dp[step - 1][row + 1][col + 2]
                            + dp[step - 1][row + 2][col + 1])/8.0;
                }
            }
        }
        return dp[K][r+offset][c+offset];
    }

    public static void main(String[] args) {
//        System.out.println(dp(8, 30, 6, 4));
//        System.out.println(quickPow(8,30));
        System.out.println(dp(25, 100, 6, 4));
    }
}
