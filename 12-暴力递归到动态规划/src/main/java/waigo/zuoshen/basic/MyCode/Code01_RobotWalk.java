package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-03-05 9:30
 */

import java.util.HashMap;

/**
 * 假设有排成一行的N个位置，记为1~N，N 一定大于或等于 2
 * 开始时机器人在其中的M位置上(M 一定是 1~N 中的一个)
 * 如果机器人来到1位置，那么下一步只能往右来到2位置；
 * 如果机器人来到N位置，那么下一步只能往左来到 N-1 位置；
 * 如果机器人来到中间位置，那么下一步可以往左走或者往右走；
 * 规定机器人必须走 K 步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
 * 给定四个参数 N、M、K、P，返回方法数。
 */
public class Code01_RobotWalk {
    //递归函数，返回从M位置走K步到达P位置的方法数
    public static int getMethodNums(int N, int P, int M, int K) {
        if (K == 0) return M == P ? 1 : 0;//BASE CASE
        if (M == 1) return getMethodNums(N, P, M + 1, K - 1);//1位置只能往右走
        if (M == N) return getMethodNums(N, P, M - 1, K - 1);//N位置只能往左走
        return getMethodNums(N, P, M + 1, K - 1) + getMethodNums(N, P, M - 1, K - 1);
    }

    //傻缓存动态规划(记忆化搜索)
    public static int getMethodNums1(int N, int P, int M, int K) {
        HashMap<String, Integer> dp = new HashMap<>();//key:"M_K",value:methodNums
        return process(N, P, M, K, dp);
    }

    public static int process(int N, int P, int M, int K, HashMap<String, Integer> dp) {
        String dpKey = M + "_" + K;
        if (dp.containsKey(dpKey)) return dp.get(dpKey);
        if (K == 0) {
            dp.put(dpKey, M == P ? 1 : 0);
            return dp.get(dpKey);//BASE CASE
        }
        if (M == 1) {
            dp.put(dpKey, process(N, P, M + 1, K - 1, dp));
            return dp.get(dpKey);//1位置只能往右走
        }
        if (M == N) {
            dp.put(dpKey, process(N, P, M - 1, K - 1, dp));
            return dp.get(dpKey);//N位置只能往左走
        }
        dp.put(dpKey, process(N, P, M + 1, K - 1, dp) + process(N, P, M - 1, K - 1, dp));
        return dp.get(dpKey);

    }

    //改经典动态规划，符合第一原则(可变参数都是int),可变参数对应的结果集展开一张二维表，随便带入一个测试数据就可以发现是存在多次求子过程的值的
    public static int getMethodNums2(int N, int P, int M, int K) {//需要考虑这个K的取值，太大的话精细化的动态规划不划算
        int[][] dp = new int[N + 1][K + 1];//M取值范围1~N，K取值范围0~K
        //使用一个简单例子可以知道这张表肯定是从左到右，从上到下填的
        //先初始化，将第一列base case给填好
        dp[P][0] = 1;//其实就一个地方是1，就是M==P那个位置，所以不用遍历
        for (int col = 1; col <= K; col++) {//注意现在row表示M，col表示K,别赋值的时候还用上了M,K
            for (int row = 1; row <= N; row++) {//M取值范围1~N，K取值范围0~K
                if (row == 1) dp[row][col] = dp[row + 1][col - 1];//1位置只能往右走
                else if (row == N) dp[row][col] = dp[row - 1][col - 1];//N位置只能往左走
                else dp[row][col] = dp[row + 1][col - 1] + dp[row - 1][col - 1];
            }
        }
        return dp[M][K];
    }

    //for test
    public static int[] generalRandomNPMK(int maxN) {
        int N = (int) (Math.random() * maxN) + 2;//2~maxN+1
        int M = (int) (Math.random() * N) + 1;//1~N
        int P = (int) (Math.random() * N) + 1;//1~N
        int K = (int) (Math.random() * maxN) + 1;//1~maxN步
        return new int[]{N, M, P, K};
    }

    public static void main(String[] args) {
        int maxN = 10, testTimes = 100000;
        int i = 0;
        for (; i < testTimes; i++) {
            int[] datas = generalRandomNPMK(maxN);
            int N = datas[0];
            int M = datas[1];
            int P = datas[2];
            int K = datas[3];
            int ans_force = getMethodNums(N, P, M, K);
            int ans_fool_dp = getMethodNums1(N, P, M, K);
            int ans_classic_dp = getMethodNums2(N, P, M, K);
            if ((ans_force != ans_fool_dp) || (ans_force != ans_classic_dp)) {
                System.out.println("ans_force:" + ans_force + ",ans_fool_dp:" + ans_fool_dp + ",ans_classic_dp:" + ans_classic_dp);
                break;
            }
        }
        System.out.println(i == testTimes ? "finish!!!" : "fucking");
    }
}
