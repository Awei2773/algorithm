package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-08 8:18
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 741. 摘樱桃
 *
 * 一个N x N的网格(grid) 代表了一块樱桃地，每个格子由以下三种数字的一种来表示：
 *
 *     0 表示这个格子是空的，所以你可以穿过它。
 *     1 表示这个格子里装着一个樱桃，你可以摘到樱桃然后穿过它。
 *     -1 表示这个格子里有荆棘，挡着你的路。
 *
 * 你的任务是在遵守下列规则的情况下，尽可能的摘到最多樱桃：
 *
 *     从位置 (0, 0) 出发，最后到达 (N-1, N-1) ，只能向下或向右走，
 *     并且只能穿越有效的格子（即只可以穿过值为0或者1的格子）；
 *     当到达 (N-1, N-1) 后，你要继续走，直到返回到 (0, 0) ，只能向上或向左走，并且只能穿越有效的格子；
 *     当你经过一个格子且这个格子包含一个樱桃时，你将摘到樱桃并且这个格子会变成空的（值变为0）；
 *     如果在 (0, 0) 和 (N-1, N-1) 之间不存在一条可经过的路径，则没有任何一个樱桃能被摘到。
 *
 * 示例 1:
 *
 * 输入: grid =
 * [[0, 1, -1],
 *  [1, 0, -1],
 *  [1, 1,  1]]
 * 输出: 5
 * 解释：
 * 玩家从（0,0）点出发，经过了向下走，向下走，向右走，向右走，到达了点(2, 2)。
 * 在这趟单程中，总共摘到了4颗樱桃，矩阵变成了[[0,1,-1],[0,0,-1],[0,0,0]]。
 * 接着，这名玩家向左走，向上走，向上走，向左走，返回了起始点，又摘到了1颗樱桃。
 * 在旅程中，总共摘到了5颗樱桃，这是可以摘到的最大值了。
 *
 * 说明:
 *     grid 是一个 N * N 的二维数组，N的取值范围是1 <= N <= 50。
 *     每一个 grid[i][j] 都是集合 {-1, 0, 1}其中的一个数。
 *     可以保证起点 grid[0][0] 和终点 grid[N-1][N-1] 的值都不会是 -1。
 */
public class Code06_CherryPickUp {
    //失败的贪心，吸取教训
    public static int cherryPickup(int[][] grid) {
        if(grid==null||grid.length==0||grid[0]==null||grid[0].length==0) return 0;
        int rows = grid.length;
        int cols = grid[0].length;
        if(grid[0][0]==-1||grid[rows-1][cols-1]==-1) return 0;
        int[][] dp = new int[rows][cols];
        //dp[row][col]表示来到row,col位置，还没登上去，返回登上去并且去到右下角位置能摘到的最多樱桃数
        dp[rows-1][cols-1] = grid[rows-1][cols-1]==1?1:0;
        for (int col = cols-2; col >=0 ; col--) {
            if(grid[rows-1][col]==-1||dp[rows-1][col+1]==-1){
                dp[rows-1][col] = -1;
            }else{
                dp[rows-1][col] = grid[rows-1][col]+dp[rows-1][col+1];
            }
        }
        for (int row = rows-2; row >= 0 ; row--) {
            if(grid[row][cols-1]==-1||dp[row+1][cols-1]==-1){
                dp[row][cols-1] = -1;
            }else{
                dp[row][cols-1] = grid[row][cols-1]+dp[row+1][cols-1];
            }
        }
        for (int row = rows-2; row >=0 ; row--) {
            for (int col = cols-2; col >=0 ; col--) {
                if((grid[row][col]==-1)||(dp[row+1][col]==-1&&dp[row][col+1]==-1)){
                    dp[row][col] = -1;
                }else{
                    dp[row][col] = grid[row][col]+Math.max(dp[row+1][col],dp[row][col+1]);
                }
            }
        }
        int res = dp[0][0];
        if(res==-1) return 0;
        //把捡到最多樱桃的路径标出来，沿途樱桃格子填0
        int pathRow = 0;
        int pathCol = 0;
        while (pathRow<rows-1||pathCol<cols-1){
            grid[pathRow][pathCol] = 0;
            if(pathRow==rows-1){//只能右走了
                pathCol++;
            }else if(pathCol==cols-1){
                pathRow++;
            }else{
                if(dp[pathRow+1][pathCol]>dp[pathRow][pathCol+1]){
                    pathRow++;
                }else{
                    pathCol++;
                }
            }
        }
        grid[rows-1][cols-1] = 0;
        //从右下角再捡回去
        dp[0][0] = 0;//有也是当初捡走了
        //现在dp[row][col]的意思是来到[row][col]位置，想要走回[0][0]位置最多能捡到多少樱桃
        for (int row = 1; row < rows; row++) {
            if(grid[row][0]==-1||dp[row-1][0]==-1){
                dp[row][0] = -1;
            }else{
                dp[row][0] = grid[row][0]+dp[row-1][0];
            }
        }
        for (int col = 1; col < cols; col++) {
            if(grid[0][col]==-1||dp[0][col-1]==-1){
                dp[0][col] = -1;
            }else{
                dp[0][col] = grid[0][col]+dp[0][col-1];
            }
        }
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                if((grid[row][col]==-1)||(dp[row-1][col]==-1&&dp[row][col-1]==-1)){
                    dp[row][col] = -1;
                }else{
                    dp[row][col] = grid[row][col]+Math.max(dp[row-1][col],dp[row][col-1]);
                }
            }
        }
        res+=dp[rows-1][cols-1];
        return res;
    }

    public static int cherryPickup2(int[][] grid){
        if(grid==null||grid.length==0||grid[0]==null||grid[0].length==0) return 0;
        int rows = grid.length;
        int cols = grid[0].length;
        HashMap<String,Integer> cache = new HashMap<>();
        int res = process(grid, 0, 0, 0, rows, cols, cache);
        return res ==-1?0:res;
    }
    //贪心贪不出答案，因为答案不一定是两次的最优的叠加，吃一次亏可能会收获更大
    //尝试，用两个小人从开头走到结尾，两个人收集的樱桃的最大累加和
    //两个人同时走的，所以进入这个函数的时候两个人走的步数一定一样,这就可以省略一个参数bc = ar+ac-br
    private static int process(int[][] grid, int Ar, int Ac, int Br, int rows, int cols, Map<String,Integer> cache) {
        if(Ar==rows-1&&Ac==cols-1){
            return grid[Ar][Ac];//一个到了，另一个肯定也到了
        }
        String key = Ar+"_"+Ac+"_"+Br;
        if(cache.containsKey(key)){
            return cache.get(key);
        }
        //A  B
        //下 右
        int aDownBRight = -1;
        int Bc = Ar+Ac-Br;
        if(Ar+1<rows&&grid[Ar+1][Ac]!=-1&&Bc+1<cols&&grid[Br][Bc+1]!=-1){
            aDownBRight = process(grid,Ar+1,Ac,Br,rows,cols,cache);
        }
        //下 下
        int aDownBDown= -1;
        if(Ar+1<rows&&grid[Ar+1][Ac]!=-1&&Br+1<rows&&grid[Br+1][Bc]!=-1){
            aDownBDown = process(grid,Ar+1,Ac,Br+1,rows,cols,cache);
        }
        //右 下
        int aRightBDown = -1;
        if(Ac+1<cols&&grid[Ar][Ac+1]!=-1&&Br+1<rows&&grid[Br+1][Bc]!=-1){
            aRightBDown = process(grid,Ar,Ac+1,Br+1,rows,cols,cache);
        }
        //右 右
        int aRightBRight = -1;
        if(Ac+1<cols&&grid[Ar][Ac+1]!=-1&&Bc+1<cols&&Bc+1<cols&&grid[Br][Bc+1]!=-1){
            aRightBRight = process(grid,Ar,Ac+1,Br,rows,cols,cache);
        }
        int nextRes = Math.max(Math.max(aDownBRight, aDownBDown), Math.max(aRightBDown, aRightBRight));
        if(nextRes==-1) return -1;
        int res = grid[Ar][Ac] + (Ar == Br && Ac == Bc ? 0 : grid[Br][Bc]) + nextRes;
        cache.put(key,res);
        return res;
    }
    public static int cherryPickup3(int[][] grid){
        if(grid==null||grid.length==0||grid[0]==null||grid[0].length==0) return 0;
        int rows = grid.length;
        int cols = grid[0].length;
        int[][][] cache = new int[rows][cols][rows];
        for (int z = 0; z < rows; z++) {
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    cache[row][col][z] = -2;
                }
            }
        }
        int res = process2(grid, 0, 0, 0, rows, cols, cache);
        return res ==-1?0:res;
    }
    //贪心贪不出答案，因为答案不一定是两次的最优的叠加，吃一次亏可能会收获更大
    //尝试，用两个小人从开头走到结尾，两个人收集的樱桃的最大累加和
    //两个人同时走的，所以进入这个函数的时候两个人走的步数一定一样,这就可以省略一个参数bc = ar+ac-br
    private static int process2(int[][] grid, int Ar, int Ac, int Br, int rows, int cols, int[][][] cache) {
        if(Ar==rows-1&&Ac==cols-1){
            return grid[Ar][Ac];//一个到了，另一个肯定也到了
        }
        if(cache[Ar][Ac][Br]!=-2){
            return cache[Ar][Ac][Br];
        }
        //A  B
        //下 右
        int aDownBRight = -1;
        int Bc = Ar+Ac-Br;
        if(Ar+1<rows&&grid[Ar+1][Ac]!=-1&&Bc+1<cols&&grid[Br][Bc+1]!=-1){
            aDownBRight = process2(grid,Ar+1,Ac,Br,rows,cols,cache);
        }
        //下 下
        int aDownBDown= -1;
        if(Ar+1<rows&&grid[Ar+1][Ac]!=-1&&Br+1<rows&&grid[Br+1][Bc]!=-1){
            aDownBDown = process2(grid,Ar+1,Ac,Br+1,rows,cols,cache);
        }
        //右 下
        int aRightBDown = -1;
        if(Ac+1<cols&&grid[Ar][Ac+1]!=-1&&Br+1<rows&&grid[Br+1][Bc]!=-1){
            aRightBDown = process2(grid,Ar,Ac+1,Br+1,rows,cols,cache);
        }
        //右 右
        int aRightBRight = -1;
        if(Ac+1<cols&&grid[Ar][Ac+1]!=-1&&Bc+1<cols&&Bc+1<cols&&grid[Br][Bc+1]!=-1){
            aRightBRight = process2(grid,Ar,Ac+1,Br,rows,cols,cache);
        }
        int nextRes = Math.max(Math.max(aDownBRight, aDownBDown), Math.max(aRightBDown, aRightBRight));
        if(nextRes==-1) {
            cache[Ar][Ac][Br]=-1;
            return -1;
        }
        int res = grid[Ar][Ac] + (Ar == Br && Ac == Bc ? 0 : grid[Br][Bc]) + nextRes;
        cache[Ar][Ac][Br]=res;
        return res;
    }

    public static void main(String[] args) {
        int[][] grid = {
                {1,1,1,-1,0,0,0},
                {0,0,0,-1,0,0,0},
                {0,0,0,-1,0,0,1},
                {-1,-1,-1,1,0,0,0},
                {0,0,0,1,0,0,0},
                {0,0,0,1,0,0,0},
                {0,0,0,1,1,1,1}};
        System.out.println(cherryPickup3(grid));
        System.out.println(cherryPickup2(grid));
    }
}
