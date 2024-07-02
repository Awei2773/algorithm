package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-14 19:26
 */

import java.util.Arrays;

/**
 * 面试题 17.24. 最大子矩阵
 * 给定一个正整数、负整数和 0 组成的 N × M 矩阵，编写代码找出元素总和最大的子矩阵。
 * <p>
 * 返回一个数组 [r1, c1, r2, c2]，其中 r1, c1 分别代表子矩阵左上角的行号和列号，r2, c2 分别代表右下角的行号和列号。
 * 若有多个满足条件的子矩阵，返回任意一个均可。
 * <p>
 * 注意：本题相对书上原题稍作改动
 * <p>
 * 示例：
 * <p>
 * 输入：
 * [
 * [-1,0],
 * [0,-1]
 * ]
 * 输出：[0,1,0,1]
 * 解释：输入中标粗的元素即为输出所表示的矩阵
 * <p>
 * <p>
 * 说明：
 * <p>
 * 1 <= matrix.length, matrix[0].length <= 200
 * <p>
 * 一个矩阵肯定是头和尾占据两行，所以只要能够枚举所有两行的配对(这两行中一个为矩阵开始行，一个为结束行)，
 * 每个两行都抓一遍答案，最终答案必在其中。
 * 两行配对都看一遍需要O(N^2)，那么两行配出来的最大矩阵咋求？
 * 一个矩阵的和如果都累加到一行上面，那这一行的最大子数组的和必然就是最大矩阵的和。
 * 所以问题又变成了，如何找一个一维数组的最大子数组的和，并且还要抓住这个子数组的开始和结尾。
 * 假如此时在算开始行i，结束行j构成的最大子矩阵，然后最大子数组为x1~x2这一段，那么就能够加工出这个矩阵的具体四个位置
 * 左上(i,x1),右上(i,x2),左下(j,x1),右下(j,x2)
 * 由#53. 最大子序和   我们可以用O(N)来抓到最长最大子数组，所以加上枚举的O(N^2)，这个算法时间复杂度O(N^3)
 * 这个题的数据量N为200,所以，O(N^3)~O(N^4)都能过
 * 关键点：
 */
public class Code07_SubMatrixMaxSum {
    //return {max,x1,x2}
    public static int[] getSubArrayMaxSum(int[] nums) {//找出nums数组的最大子序和和{x1,x2}
        int max = Integer.MIN_VALUE;
        int cur = 0;
        int x1 = 0, x2 = 0, begin = 0;
        for (int i = 0; i < nums.length; i++) {
            if (cur == 0) begin = i;//开始准备捉答案
            cur += nums[i];
            if (cur > max) {//抓到一个
                x2 = i;
                x1 = begin;
                max = cur;
            }
            cur = cur < 0 ? 0 : cur;
        }
        return new int[]{max, x1, x2};
    }

    public static int[] getMaxMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] curMergeSum = new int[cols];
        int max = Integer.MIN_VALUE;
        int iMax = 0;
        int jMax = 0;
        int x1 = 0, x2 = 0;
        for (int i = 0; i < rows; i++) {
            //初始化这一行初始的行和
            for (int col = 0; col < cols; col++) {
                curMergeSum[col] = 0;
            }
            for (int j = i; j < rows; j++) {
                for (int col = 0; col < cols; col++) {
                    curMergeSum[col] += matrix[j][col];
                }
                int[] flags = getSubArrayMaxSum(curMergeSum);
                if(max<flags[0]){
                    iMax = i;
                    jMax = j;
                    x1 = flags[1];
                    x2 = flags[2];
                    max = flags[0];
                }
            }
        }
        return new int[]{iMax,x1,jMax,x2};
    }

    public static void main(String[] args) {
//        System.out.println(Arrays.toString(getSubArrayMaxSum(new int[]{1, 2, -6, 3, 4, -5, -7, -9, 5, 5, 3})));
        int[][] a={{1,-3},{-4,9}};
        System.out.println(Arrays.toString(getMaxMatrix(a)));
    }
}
