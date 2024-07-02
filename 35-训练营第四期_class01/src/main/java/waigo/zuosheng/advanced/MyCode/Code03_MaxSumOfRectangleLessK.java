package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-02 10:33
 */

import java.util.TreeSet;

/**
 * 给定一个二维数组matrix，再给定一个k值
 * 返回累加和小于等于k，但是离k最近的子矩阵累加和
 *
 * 思路：借鉴之前求子矩阵最大累加和问题的解法，将一维问题拓展到二维，具体看class04->Code06
 * 枚举所有子矩阵就已经N^4了，所以不可能直接按点来枚举的。
 * 解决方法：
 * 定顶，动底，枚举必须以某个行做顶，某个行做底的矩阵答案，所有的行列组合N^2
 * 行列形成的矩阵可以将列方向上进行求和压缩，这样就变成一个一维数组，求这个一维数组的最接近K的
 * 子数组的和就是对应这个行列的答案。
 */
public class Code03_MaxSumOfRectangleLessK {
    public static int maxSumSubmatrix(int[][] matrix, int k) {
        if (matrix == null ||matrix.length==0|| matrix[0] == null||matrix[0].length==0)
            return 0;
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] lineSum = new int[matrix[0].length];
        int res = Integer.MIN_VALUE;
        for (int rowHead = 0; rowHead < rows; rowHead++) {
            for (int rowBottom = rowHead; rowBottom < rows; rowBottom++) {
                //定住了一个框,搞定矩阵压缩
                for (int col = 0; col < cols; col++) {
                    if(rowBottom==rowHead){
                        lineSum[col] = matrix[rowHead][col];
                    }else{
                        lineSum[col] += matrix[rowBottom][col];
                    }
                }
                //从这个压缩矩阵中找出最接近K的累加和
                res = Math.max(getMostNearLessK(lineSum,k),res);
            }
        }
        return res;
    }

    private static int getMostNearLessK(int[] arr, int k) {
        if(arr==null||arr.length==0) return 0;
        int res = Integer.MIN_VALUE;
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(0);
        int prefix = 0;
        for (int i = 0; i < arr.length; i++) {
            prefix+=arr[i];
            Integer ceilingK = treeSet.ceiling(prefix - k);
            res = ceilingK ==null?res:Math.max(prefix-ceilingK,res);
            treeSet.add(prefix);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(getMostNearLessK(new int[]{-2,1,0,-1,2,-3},5));
    }
}
