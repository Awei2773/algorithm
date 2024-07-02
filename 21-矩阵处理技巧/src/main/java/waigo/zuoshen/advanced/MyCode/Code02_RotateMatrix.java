package waigo.zuoshen.advanced.MyCode;

import java.util.Arrays;

/**
 * author waigo
 * create 2021-04-02 10:20
 */
/*
给你一幅由 N × N 矩阵表示的图像，其中每个像素的大小为 4 字节。请你设计一种算法，将图像旋转 90 度。
不占用额外内存空间能否做到？
示例 1:
给定 matrix =
[
  [1,2,3],
  [4,5,6],
  [7,8,9]
],
原地旋转输入矩阵，使其变为:
[
  [7,4,1],
  [8,5,2],
  [9,6,3]
]
*/
public class Code02_RotateMatrix {
    public static void rotate(int[][] matrix) {
        if(matrix==null||matrix.length==0) return;
        int left = 0,top = 0,right = matrix[0].length-1,bottom = matrix.length-1;
        int temp;
        while (left<right){//因为是单位矩阵，所以不会出现线旋转的情况
            //注意i，我们想要它有两种意义，一个是表示组号一个是表示加上left的坐标
            for(int i = left;i<right;i++){
                //上matrix[top][i],坐标
                //右matrix[i][right],坐标
                //下matrix[bottom][right-i]，组号
                //左matrix[bottom-i][left]，组号
                //上->右->下->左->上
                temp = matrix[i][right];//记住右
                matrix[i][right] = matrix[top][i];//上->右
                matrix[top][i] = matrix[bottom-(i-left)][left];//左->上
                matrix[bottom-(i-left)][left] = matrix[bottom][right-(i-left)];//下->左
                matrix[bottom][right-(i-left)] = temp;//右->下
            }
            left++;
            right--;
            top++;
            bottom--;
        }
    }


    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 },
                { 13, 14, 15, 16 } };
        rotate(matrix);
        for(int[] a:matrix)
            System.out.println(Arrays.toString(a));
    }
}
