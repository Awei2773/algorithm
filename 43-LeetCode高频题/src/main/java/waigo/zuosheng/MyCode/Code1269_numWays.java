package waigo.zuosheng.MyCode;

/**
 * author waigo
 * create 2024-12-05 22:09
 * https://leetcode.cn/problems/number-of-ways-to-stay-in-the-same-place-after-some-steps/
 */
public class Code1269_numWays {
   public int numWays(int steps, int arrLen) {
      return numWays(0, steps, arrLen);
   }

   //当前在idx位置，还剩下steps可以走，返回走到idx = 0的所有方案
   public int numWays(int idx, int steps, int arrLen) {
      if (steps == 0) {
         return idx == 0 ? 1 : 0;
      }
      //1. 向右走
      int res = 0;

      if (idx + 1 < arrLen) {
         res = getModeSum(res, numWays(idx + 1, steps - 1, arrLen));
      }

      //2. 向左走
      if (idx - 1 >= 0) {
         res = getModeSum(res, numWays(idx - 1, steps - 1, arrLen));
      }

      //3. 留在原地
      res = getModeSum(res, numWays(idx, steps - 1, arrLen));

      return res;
   }

   public int getModeSum(int res, int addSum) {
      return (res + addSum) % 1_000_000_007;
   }

   public int numWaysDp(int steps, int arrLen) {
      int[][] dp = new int[2][arrLen + 2];
      dp[0][1] = 1;
      int last = 0;
      int write = 1;
      for (int step = 1; step <= steps; step++) {
         for (int idx = 1; idx <= Math.min(steps, arrLen); idx++) {
            dp[write][idx] = getModeSum(getModeSum(dp[last][idx - 1], dp[last][idx]), dp[last][idx + 1]);
         }

         last = last == 0 ? 1 : 0;
         write = write == 0 ? 1 : 0;
      }

      return dp[last][1];
   }

   //优化点1: 观察最远可到达距离，超过steps / 2，就回不来了
   //优化点2: 滚动数组的指向下标可以更加优雅， int a = i & 1, b = (i + 1) & 1;， 或者是两数交换通过位运算来
   public int numWaysDpOptimism1(int steps, int arrLen) {
      //左边增加一列，避免找左上结果时超出数组
      //右边增加一列，避免找右上结果时超过数组
      //dp[y]的含义是，序号在arrLen位置的方案数
      int last = 0;
      int write = 1;
      int maxIdx = Math.min((steps >> 1) + 1, arrLen);

      int[][] dp = new int[2][maxIdx + 2];
      dp[0][1] = 1;
      for (int step = 1; step <= steps; step++) {
         for (int idx = 1; idx <= maxIdx; idx++) {
            dp[write][idx] = getModeSum(getModeSum(dp[last][idx - 1], dp[last][idx]), dp[last][idx + 1]);
         }

         last = step & 1;
         write = (step + 1) & 1;
      }

      return dp[last][1];
   }

   int mod = (int) (1e9 + 7);
   //优化点3: 滚动数组优化单维，可以发现每次依赖左上，上，右上时，完成计算填入当前位置后可以先存下原来的值，为下个位置的计算做准备，每次都用一个变量存着左上的值
   public int numWaysDpOptimism2(int steps, int arrLen) {
      //左边增加一列，避免找左上结果时超出数组
      //右边增加一列，避免找右上结果时超过数组
      //dp[y]的含义是，序号在arrLen位置的方案数
      int maxIdx = Math.min((steps >> 1) + 1, arrLen);

      int[]dp = new int[maxIdx + 2];
      dp[1] = 1;
      for (int step = 1; step <= steps; step++) {
         int leftTop = 0;
         for (int idx = 1; idx <= maxIdx; idx++) {
            int tmp = dp[idx];
            dp[idx] = ((leftTop + dp[idx]) % mod + dp[idx + 1]) % mod;
            leftTop = tmp;
         }
      }

      return dp[1];
   }
}
