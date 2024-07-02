package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-06 9:17
 */

/*
Given a 2D grid of 0s and 1s, return the number of elements in the largest square subgrid that has all 1s on its border,
or 0 if such a subgrid doesn't exist in the grid.

Example 1:

Input: grid = [[1,1,1],[1,0,1],[1,1,1]]
Output: 9
Example 2:

Input: grid = [[1,1,0,0]]
Output: 1
 

Constraints:

1 <= grid.length <= 100
1 <= grid[0].length <= 100
grid[i][j] is 0 or 1
 */
public class Code06_LargestBorderedSquare {
    /*
    [1,1,1]
    [1,0,1] --> output:9，最大的矩形就是外围那个
    [1,1,1]
    */
    /*
        思路分析:两点唯一确定一个正方形，左上角有n^2种可能，左上角定下来之后右下角可能性为n
        所以枚举所有正方形的时间复杂度为O(N^3).
        这个大流程是不能优化的，总得看一下所有的正方形的结果然后从中找出最大的来。那就看看能不能
        优化一下判断一个正方形是否合法的逻辑，想要判断一个正方形的边框是否都是1
        如果能够知道某个点右边有几个1，下边有几个1，那么就能快速的判断这个正方形是否合法了
        左上。。。。右上
        。         。
        。         。
        左下。。。。右下
        判断一个正方形边框是否全部为1，就看左上那个点右边和下面是否有足够边长长度的1，右上点下面是否有
        足够长度的1，左下右边是否有足够长度的1

        为了解决这个问题，可以使用预处理数组，一个存某个点往右的1的个数，一个存某个点往下的1的个数
    */
    public static int largest1BorderedSquare(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] rightOne = new int[rows][cols];
        //预处理数组生成
        for(int col = cols-1;col>=0;col--){
            for(int row = 0;row<rows;row++){
                rightOne[row][col]=(grid[row][col]==1?
                        1+(col+1<cols?rightOne[row][col+1]:0)
                        :
                        0);
            }
        }
        int[][] bottomOne = new int[rows][cols];
        for(int row = rows-1;row>=0;row--){
            for(int col=0;col<cols;col++){
                bottomOne[row][col]=(grid[row][col]==1?1+
                        (row+1<rows?bottomOne[row+1][col]:0)
                        :0);
            }
        }
        //枚举所有的正方形，抓答案
        int ans = 0;
        for(int row = 0;row<rows;row++){
            for(int col = 0;col<cols;col++){
                //左上角确定,Math.min(cols-col,rows-row)最大能够达到的边长
                for(int edgeLen = 1;edgeLen<=Math.min(cols-col,rows-row);edgeLen++){
                    //左上角坐标[row,col],左下角坐标，[row+edgeLen-1,col],右上角坐标，[row,col+edgeLen-1]
                    if(rightOne[row][col]>=edgeLen&&rightOne[row+edgeLen-1][col]>=edgeLen
                            &&bottomOne[row][col]>=edgeLen&&bottomOne[row][col+edgeLen-1]>=edgeLen){
                        //找到一个合法的正方形，抓一下答案
                        ans = Math.max(ans,edgeLen*edgeLen);
                    }
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[][] square = {{1,1,0,0}};
        System.out.println(largest1BorderedSquare(square));
    }
}
