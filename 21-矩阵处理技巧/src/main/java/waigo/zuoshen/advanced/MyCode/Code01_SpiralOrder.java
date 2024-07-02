package waigo.zuoshen.advanced.MyCode;

import java.util.Arrays;

/**
 * author waigo
 * create 2021-04-02 8:22
 */
//https://leetcode-cn.com/problems/shun-shi-zhen-da-yin-ju-zhen-lcof/
/*
输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字。
示例 1：
输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
输出：[1,2,3,6,9,8,7,4,5]
示例 2：
输入：matrix = [[],[5,6,7,8],[9,10,11,12]]
输出：[1,2,3,4,8,12,11,10,9,5,6,7]
*/
public class Code01_SpiralOrder {
    //关键点：找到coding上的宏观调度，而不是着眼于点到点的移动
    public static int[] spiralOrder(int[][] matrix) {
        if(matrix==null||matrix.length==0) return new int[]{};
        //将第一圈顺时针打印出来然后其他圈相同处理完成顺时针打印
        int[] orders = new int[matrix[0].length*matrix.length];
        
        //根据矩形的不同来进行分别处理，横线，竖线，点和普通矩形
        int idx = 0;
        int left = 0,top = 0,right = matrix[0].length-1,bottom = matrix.length-1;
        while (left<=right&&top<=bottom){//只要是还能拼出一个矩形
            if(bottom==top){//横线或点
                for(int i = left;i<=right;i++)
                    orders[idx++] = matrix[bottom][i];
            }else if(left==right){//竖线
                for(int i = top;i<=bottom;i++)
                    orders[idx++] = matrix[i][left];
            }else{//普通矩形
                for(int i = left;i<right;i++){//上面第一排
                    orders[idx++] = matrix[top][i];
                }
                for(int i = top;i<bottom;i++){//右边一排
                    orders[idx++] = matrix[i][right];
                }
                for(int i = right;i>left;i--){//下面一排
                    orders[idx++] = matrix[bottom][i];
                }
                for(int i = bottom;i>left;i--){
                    orders[idx++] = matrix[i][left];
                }
            }
            left++;
            top++;
            right--;
            bottom--;
        }
        return orders;
    }


    public static void main(String[] args) {
        System.out.println(Arrays.toString(spiralOrder(new int[][]{ { 1 } })));
    }
}
