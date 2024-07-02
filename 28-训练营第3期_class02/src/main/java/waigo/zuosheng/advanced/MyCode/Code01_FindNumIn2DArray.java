package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-07 10:59
 */
/*
在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个高效的函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。

 

示例:

现有矩阵 matrix 如下：

[
  [1,   4,  7, 11, 15],
  [2,   5,  8, 12, 19],
  [3,   6,  9, 16, 22],
  [10, 13, 14, 17, 24],
  [18, 21, 23, 26, 30]
]
给定 target = 5，返回 true。
给定 target = 20，返回 false。

限制：
0 <= n <= 1000
0 <= m <= 1000
从这个限制可以看出，这是一个数据量为10^6的题目，所以算法时间复杂度不能超过N,N^logN

思路：
从左下角出发，每次在target<右边的数的时候尽力往右走，走不动了就往上走，
因为每次都这样做，所以每一排走到没法走之后答案存在与否都会在当前列后面，也就是说当前列之前的所有列不可能有答案了，淘汰掉了
然后在往上走的时候，每上去一步就会淘汰掉下面的一行

综上所述，这样走，每一步会淘汰一行或者一列，最多也就只会走rows+cols步
*/
public class Code01_FindNumIn2DArray {
    public static boolean findNumberIn2DArray(int[][] matrix, int target) {
        int curRow = matrix.length-1;
        int curCol = 0;//定位左下角
        while (curRow>=0&&curCol< matrix[0].length){
            //尽力往右走
            while (curCol+1<matrix[0].length&&matrix[curRow][curCol+1]<=target){
                curCol++;
            }
            //要是此时不是target说明来到这一行的最右了，再右就比target大了，得往上走了
            if(matrix[curRow--][curCol]==target) return true;
            //向上走一步 curRow--
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1,   4,  7, 11, 15},
                {2,   5,  8, 12, 19},
                {3,   6,  9, 16, 22}
        };
        System.out.println(findNumberIn2DArray(matrix,8));
    }
}
