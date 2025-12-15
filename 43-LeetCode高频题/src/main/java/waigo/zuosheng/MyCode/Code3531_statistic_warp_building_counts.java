package waigo.zuosheng.MyCode;

import java.util.Arrays;
import java.util.Comparator;

/**
 * author waigo
 * create 2025-12-11 23:39
 */
public class Code3531_statistic_warp_building_counts {
   /**
    * 输入: n = 3, buildings = [[1,2],[2,2],[3,2],[2,1],[2,3]]
    *
    * 输出: 1
    *
    * 解释:
    *
    * 只有建筑 [2,2] 被覆盖，因为它在每个方向上都至少存在一个建筑：
    * 上方 ([1,2])
    * 下方 ([3,2])
    * 左方 ([2,1])
    * 右方 ([2,3])
    * 因此，被覆盖的建筑数量是 1。
    */
   public int countCoveredBuildings(int n, int[][] buildings) {
      //暴力解法
      //1. buildings根据x从小到大排序
      //2. x相同y从小到大排序
      //3. 当前判定buildings[i]是否被覆盖的做法
      //4. 判定左边， lx < x, ly = y,以当前下标位置为右边界，对buildings[0] ~ buildings[i]做查找，找到等于y的位置
      //5. 判定上边， lx = x, ly > y, 看buildings[i + 1]， buildings[i + 1]如果rx = x，那么ry > y
      //6. 判定右边,  lx > x, ly = y, 以当前下标为左边界，对buildings[i] ~ buildings[length]做查找，找到等于y的位置
      //7. 判定下边， lx = x, ly < y, 看buildings[i - 1]， buildings[i - 1]如果lx = x，那么ly < y
      Arrays.sort(buildings, (o1, o2) -> {
         if (o1[0] < o2[0]) {
            return -1;
         }
         else if (o1[0] > o2[0]) {
             return 1;
         }
         else {
            return o1[1] - o2[1];
         }
      });

      int count = 0;
      int[] leftCountCache = new int[n + 1];
      leftCountCache[buildings[0][1]]++;

      int[] rightCountCache = new int[n + 1];
      for (int i = 1; i < buildings.length; i++) {
         rightCountCache[buildings[i][1]]++;
      }

      for (int current = 1; current < buildings.length; current++) {
         //判定左边
         int currentY = buildings[current][1];
         //把当前位置在左右缓存中调整好
         int leftYCount = leftCountCache[currentY]++;
         int rightYCount = --rightCountCache[currentY];

         if (leftYCount == 0) {
            continue;
         }

         //判定上边
         int currentX = buildings[current][0];
         if (current + 1 >= buildings.length || buildings[current + 1][0] != currentX) {
            continue;
         }

         //判定右边,先减掉自己的计数
         if (rightYCount == 0) {
            continue;
         }

         //判定下边
         if (buildings[current - 1][0] == currentX) {
            count++;
         }
      }

      return count;
   }

   //模拟方法：只要自己的上下左右都有点，就能保证自己肯定是被包围的
   public int countCoveredBuildings_well(int n, int[][] buildings) {
      //只要自己的上下左右的max都有更大值，那就能快速判断当前点是否是被包围的
      //左边是否还有数据：y相同,x的最小值是多少？
      int[] minimumXwithY = new int[n + 1];
      //右边是否还有数据：y相同,x的最大值是多少？
      int[] maximumXwithY = new int[n + 1];
      //下边是否还有数据: x相同,y的最小值是多少？
      int[] minimumYwithX = new int[n + 1];
      //上边是否还有数据: x相同，y的最大值是多少？
      int[] maximumYwithX = new int[n + 1];

      //填充四个集合,取min的集合需要注意，默认值不能为0，否则会取到0
      Arrays.fill(minimumYwithX, n + 1);
      Arrays.fill(minimumXwithY, n + 1);

      for (int[] building : buildings) {
         int x = building[0];
         int y = building[1];

         minimumXwithY[y] = Math.min(minimumXwithY[y], x);
         maximumXwithY[y] = Math.max(maximumXwithY[y], x);
         minimumYwithX[x] = Math.min(minimumYwithX[x], y);
         maximumYwithX[x] = Math.max(maximumYwithX[x], y);
      }

      int count = 0;
      for (int[] building : buildings) {
         //左边还有数据
         int y = building[1];
         int x = building[0];
         if (x > minimumXwithY[y] && x < maximumXwithY[y]
         && y > minimumYwithX[x] && y < maximumYwithX[x]) {
            count++;
         }
      }

      return count;
   }

   public static void main(String[] args) {
      //对数器
      int n = 3;
      //[[1,2],[2,1],[3,1],[1,1],[3,3],[3,2]]
      //[[1,2],[3,1],[1,1],[2,3],[2,2],[3,2]]
      int[][] buildings = {{1,2}, {3,1},{1,1},{2,3},{2,2},{3,2}};

      int i = new Code3531_statistic_warp_building_counts().countCoveredBuildings(3, buildings);
      int i1 = new Code3531_statistic_warp_building_counts().countCoveredBuildings_well(3, buildings);
      System.out.println(i == i1);
   }
}
