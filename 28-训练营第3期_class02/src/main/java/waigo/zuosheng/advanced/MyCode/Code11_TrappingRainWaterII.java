package waigo.zuosheng.advanced.MyCode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * author waigo
 * create 2021-05-10 8:46
 */

/*
Given an m row n integer matrix heightMap representing the height of each unit
 cell in a 2D elevation map, return the volume of water it can trap after raining.

Example 1:


Input: heightMap = [[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]]
Output: 4
Explanation: After the rain, water is trapped between the blocks.
We have two small pounds 1 and 3 units trapped.
The total volume of water trapped is 4.
Example 2:


Input: heightMap = [[3,3,3,3,3],[3,2,2,2,3],[3,2,1,2,3],[3,2,2,2,3],[3,3,3,3,3]]
Output: 10
 

Constraints:

m == heightMap.length
n == heightMap[i].length
1 <= m, n <= 200
0 <= heightMap[i][j] <= 2 * 10^4

思路：
二维地形接水和一维直方图接水的思路是一样的，既然你要我求整块地形能接多少水，那么我求出所有位置能接的水量，
再加起来结果肯定对。所以问题就变成了某个位置的水量如何计算？注意本题的数据量，这个地形的大小最大有40000
个格子，所以O(N*M)或者O((N*M)*log(N*M))的算法才能拿下，其中N为地形的长，M为地形的宽

接下来考虑一个位置的水量咋算，和二维的问题一样，想要结算一个位置的水量我得找到一个参照位置，就是找一个
木桶中那块最短的板在哪里，高度多少？

四周位置肯定存不住水的，跳过。思考随意一个位置，它四周的情况是不清楚的，参考二维问题，假如说我能够
知道我四周看过去此时的水平面的高度中找一个最小的一个拿来和我此时的位置进行pk，那我不就能算出此位置的
结果了嘛。如果是遍历看过去肯定不合适，因为地形中每一个位置看一遍都已经O(N*M)了，你再看一下四周
肯定超时了。所以考虑用预处理结构优化，整四个二维数组，能够得知在某个位置往指定方向看过去的水平面。

但是这样真的能解决问题了嘛？
你确实能够找到各个方向上的水平面高度，但是，我水可能还没到你那边就已经从别的地方溜走了，
所以这种方法行不通，就算是把周围8个方向上的水平面找出来都无法解决问题，无奈放弃.
-------------------------------------------------------------------------------------
逆向思考，既然每个位置我无法找到水平面来结算，那是否能找到一个泄水口来结算一批位置呢？
就是能不能找到这么个位置，它四周肯定是以它作为水平面的？

这个好像是可以的，比如说外围一圈，那个最短的地方肯定能结算它四周的位置，因为雨下的足够大，你不可能会
低于这个短板，高于这个短板又会溢出，所以这个结算肯定是正确的。

欸，这样的话，我每次都找外围的短板，然后结算它四周的位置，不断的缩小这个外围不就能把水量算出来了嘛！！！
等等，短板用一次就丢好么？如果这个短板比中间所有的位置都要高，那你这圈就是再咋缩短板也不能变呀。

所以短板得有个淘汰机制，什么情况下短板就没用了，那肯定你此时结算的圈的短板比之前的短板要高的时候了，
我当前圈的短板更高，那我这个圈里的水都给我hold住了，不可能从之前的短板里溜出去了。

1.外围不断在变化，咋样能快速知道每次的短板是哪个呢？很明显用小根堆可以解决。
2.如何确保一个位置不被重复结算？用set？反正肯定最后所有位置都被结算一次，直接用个boolean数组就好了
3.然后再整个变量存一下此时短板的高度
4.堆里存的是当前外圈的板子，那么需要存那些信息呢？我要知道你多高，height跑不掉，我需要结算你四周的水量，
所以你的位置也得知道，所以加上x,col

不行，鉴于很多惨痛的经验，还不能下手，分析分析时间复杂度能不能过了？
每个位置进一次堆，进堆的时候结算一次，每次短板出堆的时候结算一下自己的四周，嗯，O(N*M)可以了，
那就开撸。
 */
public class Code11_TrappingRainWaterII {
    public static class Location {
        int row, col, height;

        public Location(int row, int col, int height) {
            this.row = row;
            this.col = col;
            this.height = height;
        }
    }

    public static int trapRainWater(int[][] heightMap) {
        int rows = heightMap.length;//宽
        int cols = heightMap[0].length;//长
        boolean[][] isResolve = new boolean[rows][cols];
        //构造外围圈，初始化
        PriorityQueue<Location> locations = new PriorityQueue<>(Comparator.comparingInt(x -> x.height));//堆里存的
        for (int col = 0; col < cols; col++) {
            locations.add(new Location(0, col, heightMap[0][col]));
            isResolve[0][col] = true;
        }
        //第一列
        for (int row = 1; row < rows - 1; row++) {
            locations.add(new Location(row, 0, heightMap[row][0]));
            isResolve[row][0] = true;
        }
        //最后一列
        for (int row = 1; row < rows - 1; row++) {
            locations.add(new Location(row, cols - 1, heightMap[row][cols - 1]));
            isResolve[row][cols - 1] = true;
        }
        //最后一行
        for (int col = 0; col < cols; col++) {
            locations.add(new Location(rows - 1, col, heightMap[rows - 1][col]));
            isResolve[rows - 1][col] = true;
        }
        //OK，开始找短板
        int shortSlap = 0;
        int waterAll = 0;
        while (!locations.isEmpty()) {
            Location curCircleShortSlap = locations.poll();
            shortSlap = Math.max(shortSlap, curCircleShortSlap.height);//决出短板
            //结算短板四周的水量
            //左
            int row = curCircleShortSlap.row;
            int col = curCircleShortSlap.col;
            if ((col - 1 >= 0) && (!isResolve[row][col - 1])) {
                waterAll += Math.max(0, shortSlap - heightMap[row][col - 1]);//结算水量，用短板减去这里的地势，差值就是hold住的水
                isResolve[row][col - 1] = true;
                locations.add(new Location(row, col - 1, heightMap[row][col - 1]));//你该做边界了
            }
            //上
            if ((row - 1 >= 0) && (!isResolve[row - 1][col])) {
                waterAll += Math.max(0, shortSlap - heightMap[row - 1][col]);//结算水量，用短板减去这里的地势，差值就是hold住的水
                isResolve[row - 1][col] = true;
                locations.add(new Location(row - 1, col, heightMap[row - 1][col]));//你该做边界了
            }
            //右
            if ((col + 1 < cols) && (!isResolve[row][col + 1])) {
                waterAll += Math.max(0, shortSlap - heightMap[row][col + 1]);//结算水量，用短板减去这里的地势，差值就是hold住的水
                isResolve[row][col + 1] = true;
                locations.add(new Location(row, col + 1, heightMap[row][col + 1]));//你该做边界了
            }
            //下
            if ((row + 1 < rows) && (!isResolve[row + 1][col])) {
                waterAll += Math.max(0, shortSlap - heightMap[row + 1][col]);//结算水量，用短板减去这里的地势，差值就是hold住的水
                isResolve[row + 1][col] = true;
                locations.add(new Location(row + 1, col, heightMap[row + 1][col]));//你该做边界了
            }
        }

        return waterAll;
    }

    public static void main(String[] args) {
        int[][] a = {{1, 4, 3, 1, 3, 2}, {3, 2, 1, 3, 2, 4}, {2, 3, 3, 2, 3, 1}};
        System.out.println(trapRainWater(a));
    }
}
