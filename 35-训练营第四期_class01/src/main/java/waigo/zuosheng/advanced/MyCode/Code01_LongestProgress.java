package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-01 10:40
 */

import java.util.HashMap;

/**
 * 给定一个二维数组matrix，可以从任何位置出发，每一步可以走向上、下、左、右，四个方向。返回最大递增链的长度。
 * 例子：
 * matrix =
 * 5  4  3
 * 3  1  2
 * 2  1  3
 * 从最中心的1出发，是可以走出1 2 3 4 5的链的，而且这是最长的递增链。所以返回长度5
 *
 * 迷惑人的点：路径需要记录嘛？会走从头路嘛？
 * 是不会的，因为每次走之前都考虑好了，假设每一步都是往上走的，咋可能走着走着走到之前的老路上呢？
 * 就像是攀登，只要不断向上跑去，去到的地方不会比停在原地更低。
 */
public class Code01_LongestProgress {
    public static int getLongestProgressLen(int[][] matrix){
        if(matrix==null||matrix.length==0||matrix[0].length==0) return 0;
        return process(matrix,2,1);
    }
    //只要能进来说明这个位置合法，而且就是在一条递增链上的，不用管最后链条多长，管好你自己这个片区就好
    private static int process(int[][] matrix, int row, int col) {
        //枚举四个方向走出去的答案
        int res = 1;//自己这个点，父链条还没加
        int sonMax = 0;
        //up
        if(row-1>=0&&matrix[row-1][col]>matrix[row][col]){
            sonMax = Math.max(process(matrix,row-1,col),sonMax);
        }
        //left
        if(col-1>=0&&matrix[row][col-1]>matrix[row][col]){
            sonMax = Math.max(process(matrix,row,col-1),sonMax);
        }
        //right
        if(col+1<matrix[0].length&&matrix[row][col+1]>matrix[row][col]){
            sonMax = Math.max(process(matrix,row,col+1),sonMax);
        }
        //down
        if(row+1<matrix.length&&matrix[row+1][col]>matrix[row][col]){
            sonMax = Math.max(process(matrix,row+1,col),sonMax);
        }
        return res+sonMax;
    }
    //dp
    public static int longestIncreasingPath(int[][] matrix) {
        if(matrix==null||matrix.length==0||matrix[0].length==0) return 0;
        int rows = matrix.length;
        int cols = matrix[0].length;
        int res = 0;
        HashMap<String,Integer> cache = new HashMap<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                res = Math.max(res,process2(matrix,row,col,cache));
            }
        }
        return res;
    }
    //记忆化搜索版本
    private static int process2(int[][] matrix, int row, int col, HashMap<String, Integer> cache) {
        String key = row + "_" + col;
        if(cache.containsKey(key))
            return cache.get(key);
        //枚举四个方向走出去的答案
        int res = 1;//自己这个点，父链条还没加
        int sonMax = 0;
        //up
        if(row-1>=0&&matrix[row-1][col]>matrix[row][col]){
            sonMax = Math.max(process2(matrix,row-1,col,cache),sonMax);
        }
        //left
        if(col-1>=0&&matrix[row][col-1]>matrix[row][col]){
            sonMax = Math.max(process2(matrix,row,col-1,cache),sonMax);
        }
        //right
        if(col+1<matrix[0].length&&matrix[row][col+1]>matrix[row][col]){
            sonMax = Math.max(process2(matrix,row,col+1,cache),sonMax);
        }
        //down
        if(row+1<matrix.length&&matrix[row+1][col]>matrix[row][col]){
            sonMax = Math.max(process2(matrix,row+1,col,cache),sonMax);
        }
        cache.put(key,res+sonMax);
        return res+sonMax;
    }
    //dp2
    public static int longestIncreasingPath2(int[][] matrix) {
        if(matrix==null||matrix.length==0||matrix[0].length==0) return 0;
        int rows = matrix.length;
        int cols = matrix[0].length;
        int res = 0;
        int[][] cache = new int[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                res = Math.max(res,process3(matrix,row,col,cache));
            }
        }
        return res;
    }
    //记忆化搜索版本
    private static int process3(int[][] matrix, int row, int col,int[][] cache) {
        if(cache[row][col]!=0)
            return cache[row][col];
        //枚举四个方向走出去的答案
        int res = 1;//自己这个点，父链条还没加
        int sonMax = 0;
        //up
        if(row-1>=0&&matrix[row-1][col]>matrix[row][col]){
            sonMax = Math.max(process3(matrix,row-1,col,cache),sonMax);
        }
        //left
        if(col-1>=0&&matrix[row][col-1]>matrix[row][col]){
            sonMax = Math.max(process3(matrix,row,col-1,cache),sonMax);
        }
        //right
        if(col+1<matrix[0].length&&matrix[row][col+1]>matrix[row][col]){
            sonMax = Math.max(process3(matrix,row,col+1,cache),sonMax);
        }
        //down
        if(row+1<matrix.length&&matrix[row+1][col]>matrix[row][col]){
            sonMax = Math.max(process3(matrix,row+1,col,cache),sonMax);
        }
        cache[row][col] = res+sonMax;
        return res+sonMax;
    }
    public static void main(String[] args) {
        int[][] matrix ={{9,9,4},{6,6,8},{2,1,1}};
//        int[][] matrix ={{1}};
        System.out.println(longestIncreasingPath2(matrix));
    }
}
