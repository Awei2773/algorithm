package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-01 7:34
 */

/**
 * int[] d，d[i]：i号怪兽的能力
 * int[] p，p[i]：i号怪兽要求的钱
 * 开始时你的能力是0，你的目标是从0号怪兽开始，通过所有的怪兽。
 * 如果你当前的能力，小于i号怪兽的能力，你必须付出p[i]的钱，贿赂这个怪兽，然后怪兽就会加入你，他的能力直接累加到你的能力上；
 * 如果你当前的能力，大于等于i号怪兽的能力，你可以选择直接通过，你的能力并不会下降，你也可以选择贿赂
 * 这个怪兽，然后怪兽就会加入你,他的能力直接累加到你的能力上。
 * 返回通过所有的怪兽，需要花的最小钱数。
 */
public class Code04_MonsterProblem {
    public static int passTheGameMinimumMoney(int[] d, int[] p) {
        if (d == null || p == null || d.length != p.length || d.length == 0) return 0;
        return process2(d, p, 0, 0);
    }

    //暴力尝试憋的不行，其实你会发现花出去的钱你压根就不知道变化范围，而且压根就不会在子过程中用到，所以在父过程接收就好了
    private static int process(int[] monsters, int[] monsterWant, int faceTo, int nowPower, int nowCost) {
        if (faceTo == monsters.length) return nowCost;
        //1.比它强，干掉他，直接过，不花钱
        //2.比它强，收买他，要花钱
        //3.比它弱，收买他，要花钱
        int minCost = Integer.MAX_VALUE;
        if (nowPower >= monsters[faceTo]) {
            minCost = Math.min(minCost, process(monsters, monsterWant, faceTo + 1, nowPower, nowCost));
        }
        return Math.min(minCost, process(monsters, monsterWant, faceTo + 1, nowPower + monsters[faceTo], nowCost + monsterWant[faceTo]));
    }

    /**
     * @return 当前能力是nowPower，当前面对的是faceTo下标的怪兽，返回从当前到最后打败所有怪兽所需的最少钱
     */
    private static int process2(int[] monsters, int[] monsterWant, int faceTo, int nowPower) {
        if (faceTo == monsters.length) return 0;
        int minCost = Integer.MAX_VALUE;
        if (nowPower >= monsters[faceTo]) {
            minCost = Math.min(minCost, process2(monsters, monsterWant, faceTo + 1, nowPower));
        }
        return Math.min(minCost, monsterWant[faceTo] + process2(monsters, monsterWant, faceTo + 1, nowPower + monsters[faceTo]));
    }

    public static int dpWays(int[] d, int[] p) {
        if (d == null || p == null || d.length != p.length || d.length == 0) return 0;
        int rows = d.length + 1;
        int cols = d[0];
        for (int i = 1; i < d.length; i++) {
            cols += d[i];
        }
        cols++;
        int[][] dp = new int[rows][cols];//cols:0~powerMax,rows:0~len
        for (int row = rows - 2; row >= 0; row--) {
            for (int col = cols - 1; col >= 0; col--) {
                dp[row][col] = Integer.MAX_VALUE;
                if (col + d[row] >= cols) continue;
                if (col >= d[row]) {
                    dp[row][col] = Math.min(dp[row][col], dp[row + 1][col]);
                }
                dp[row][col] = Math.min(dp[row][col], p[row] + dp[row + 1][col + d[row]]);
            }
        }
        return dp[0][0];
    }

    public static void main(String[] args) {
        int[] d = {1, 5, 3, 2, 4, 5, 6, 1, 7};
        int[] p = {2, 4, 11, 111, 1111, 33, 21, 53, 100};
        System.out.println(dpWays(d, p));
    }
    /**
     * 总结：能看数据量就看数据量分析N^3的解行不行，一般情况下尽量憋N^2的解，对于子过程不是很紧要的数据看是否不用下传，
     * 像这道题，压根最后通过的时候不用知道当前通关的总钱数，反正递归回来父过程的时候可以结算，所以就不用下传了，不然
     * N^3的时间复杂度不说，还得加上遍历求钱数的问题，太浪费了，你这人家压根大多数情况都不会买多少怪兽，你倒好，直接
     * 上来整个三维的，把所有怪兽都买和不买都给整一遍，浪费性能。
     */
}
