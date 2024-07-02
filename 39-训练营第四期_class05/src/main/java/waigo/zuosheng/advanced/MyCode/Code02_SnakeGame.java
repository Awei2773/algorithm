package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-25 11:40
 */

/**
 * 给定一个二维数组matrix，每个单元都是一个整数，有正有负。最开始的时候小Q操纵
 * 一条长度为0的蛇蛇从矩阵最左侧任选一个单元格进入地图，蛇每次只能够到达当前位 置的
 * 右上相邻，右侧相邻和右下相邻的单元格。蛇蛇到达一个单元格后，自身的长度会 瞬间加上该单元格的数值，
 * 任何情况下长度为负则游戏结束。小Q是个天才，他拥有一 个超能力，可以在游戏开始的时候把地图中的
 * 某一个节点的值变为其相反数(注:最多 只能改变一个节点)。问在小Q游戏过程中，他的蛇蛇最长长度可以到多少?
 * 比如:
 * 1 -4  10
 * 3 -2 -1
 * 2 -1  0
 * 0  5 -2
 * 最优路径为从最左侧的3开始，3 -> -4(利用能力变成4) -> 10。所以返回17。
 */
@SuppressWarnings("Duplicates")
public class Code02_SnakeGame {
    public static int walk(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        int max = 0;
        int rows = matrix.length;
        int cols= matrix[0].length;
        for (int row = 0; row < rows; row++) {
            for(int col = 0;col<cols;col++){
                int[] res = process(matrix, row, col);
                max = Math.max(max,Math.max(res[0],res[1]));
            }
        }
        return max;
    }
    private static int[][] ways = {{1,-1},{0,-1},{-1,-1}};//左下，左，左上
    //return [用能力到row,col的长度,不用能力到],到不了返回负
    private static int[] process(int[][] matrix, int row, int col) {
        if(col==0){
            return new int[]{-matrix[row][col],matrix[row][col]};
        }
        //遍历三个可能来的方向
        //1.从三个子流程那里拿数据凑出自己当前的数据
        int no = -1;//默认不可达
        int yes = -1;
        for (int[] way : ways) {
            int newRow = row + way[0];
            int newCol = col + way[1];
            if(newRow<0||newRow>=matrix.length||newCol<0||newCol>=matrix[0].length) continue;
            int[] pcsRes = process(matrix, newRow, newCol);
            if(pcsRes[1]>=0){
                //2.当前的不用能力只能是之前没用，现在依然没用
                no = Math.max(no,pcsRes[1]+matrix[row][col]);
                yes = Math.max(yes,pcsRes[1]-matrix[row][col]);
            }
            if(pcsRes[0]>=0){
                //3.当前用了能力可能是之前没用，当前用了，可能是之前就用了
                yes = Math.max(yes,pcsRes[0]+matrix[row][col]);
            }
        }
        return new int[]{yes,no};
    }
    public static int dp(int[][] matrix){
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] dpUnUse = new int[rows][cols];
        int[][] dpUse = new int[rows][cols];
        int max = 0;
        //先填第一列
        for(int row = 0;row<rows;row++){
            dpUnUse[row][0] = matrix[row][0];
            dpUse[row][0] = -matrix[row][0];
            max = Math.max(max,Math.max(dpUnUse[row][0],dpUse[row][0]));
        }
        //同时向右边推进
        for(int col = 1;col<cols;col++){
            for(int row = 0;row<rows;row++){
                //遍历三个可能来的方向
                //1.从三个子流程那里拿数据凑出自己当前的数据
                int no = -1;//默认不可达
                int yes = -1;
                for (int[] way : ways) {
                    int newRow = row + way[0];
                    int newCol = col + way[1];
                    if(newRow<0||newRow>=matrix.length||newCol<0||newCol>=matrix[0].length) continue;
                    int[] pcsRes = new int[]{dpUse[newRow][newCol],dpUnUse[newRow][newCol]};
                    if(pcsRes[1]>=0){
                        //2.当前的不用能力只能是之前没用，现在依然没用
                        no = Math.max(no,pcsRes[1]+matrix[row][col]);
                        yes = Math.max(yes,pcsRes[1]-matrix[row][col]);
                    }
                    if(pcsRes[0]>=0){
                        //3.当前用了能力可能是之前没用，当前用了，可能是之前就用了
                        yes = Math.max(yes,pcsRes[0]+matrix[row][col]);
                    }
                }
                dpUnUse[row][col] = no;
                dpUse[row][col] = yes;
                max = Math.max(max,Math.max(no,yes));
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1,-4,10},
                {3,-2,-1},
                {2,-1,0},
                {0,5,-2}
        };
        System.out.println(walk(matrix));
    }
}
