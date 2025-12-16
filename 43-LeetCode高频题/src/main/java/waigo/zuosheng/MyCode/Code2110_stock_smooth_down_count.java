package waigo.zuosheng.MyCode;

/**
 * author waigo
 * create 2025-12-15 23:15
 */
public class Code2110_stock_smooth_down_count {
   public long getDescentPeriods(int[] prices) {
      //定义一个数组，smoothDown[i]表示以第i天为平滑下降的最后一天，总共有多少个平滑下降阶段
      int[] smoothDown = new int[prices.length];
      smoothDown[0] = 1;
      long result = 1;

      for(int i = 1; i < prices.length; i++) {
         if (prices[i - 1] - prices[i] == 1) {
            smoothDown[i] = smoothDown[i - 1] + 1;
         }
         else {
            smoothDown[i] = 1;
         }

         result += smoothDown[i];
      }

      return result;
   }

   public static void main(String[] args) {
      Code2110_stock_smooth_down_count code2110_stock_smooth_down_count = new Code2110_stock_smooth_down_count();
      int most = 100000;
      int[] prices = new int[most];
      int start = most;
      for (int i = 0; i < most; i++) {
         prices[i] = start--;
      }
      long descentPeriods = code2110_stock_smooth_down_count.getDescentPeriods(prices);
      if (descentPeriods != 36) {
         System.out.println("fucking");
      }
   }
}
