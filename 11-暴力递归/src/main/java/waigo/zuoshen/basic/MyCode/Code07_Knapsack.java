package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-27 14:05
 */

/**
 * 给定两个长度都为N的数组weights和values，
 * weights[i]和values[i]分别代表 i号物品的重量和价值。
 * 给定一个正数bag，表示一个载重bag的袋子，
 * 你装的物品不能超过这个重量。
 * 返回你能装下最多的价值是多少?
 * <p>
 * 从左到右的尝试模型2
 * 和之前的模型不同，这个有一个不同策略的比较。
 */
public class Code07_Knapsack {
    public static int getMaxValue1(int[] weights, int[] values, int bag) {
        if (weights == null || values == null || weights.length != values.length) return 0;
        return process1(weights, values, 0, 0, bag);
    }

    //返回从index开始，当前已经装载了payLoad的东西，最终能装的最大价值
    private static int process1(int[] weights, int[] values, int index, int payLoad, int bag) {
        //已经超出数组范围了，肯定装不了了，返回0,合理
        if (index == weights.length) return 0;
        int res1 = process1(weights, values, index + 1, payLoad, bag);//不装当前下标的物品
        int res2 = 0;
        payLoad += weights[index];
        if (payLoad <= bag) {//能装下才有这种可能
            res2 = process1(weights, values, index + 1, payLoad, bag) + values[index];
        }
        return Math.max(res1, res2);
    }

    //思路2:
    public static int getMaxValue2(int[] weights, int[] values, int bag) {
        if (weights == null || values == null || weights.length != values.length) return 0;
        return process2(weights, values, 0, bag);
    }

    //从index开始，如果还能装remain重量的东西则最多价值多少
    private static int process2(int[] weights, int[] values, int index, int rest) {
        if (index >= weights.length) return 0;
        return Math.max(process2(weights, values, index + 1, rest),
                (rest - weights[index]) >= 0 ? process2(weights, values, index + 1, rest - weights[index]) + values[index] : 0);
    }

    /**
     * -----------------------------------------------思路2改经典细粒度动态规划------------------------------------
     */
    public static int getMaxValue_dp(int[] weights, int[] values, int bag) {
        if (weights == null || values == null || weights.length != values.length) return 0;
        int N = weights.length;
        int[][] dp = new int[N + 1][bag + 1];
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= bag; rest++) {
                dp[index][rest] = Math.max(dp[index + 1][rest],
                        (rest - weights[index]) >= 0 ? dp[index + 1][rest - weights[index]] + values[index] : 0);
            }
        }
        return dp[0][bag];
    }

    //两种思路其实都差不多，剩余质量就是bag-payLoad，就是两个可以合并
    public static void main(String[] args) {
        int[] weights = {1, 3, 5,4,2,5,2,11,23,4,5};
        int[] values = {1, 3, 5,2343,12,34,2345,23,1,2,4};
        int bag = 30;
        System.out.println(getMaxValue1(weights, values, bag));

        System.out.println(getMaxValue2(weights, values, bag));
        System.out.println(getMaxValue_dp(weights, values, bag));
    }
}
