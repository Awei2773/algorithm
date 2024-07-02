package com.waigo.newcoder;

import java.util.ArrayList;

/**
 * author waigo
 * create 2021-09-02 22:54
 */
public class NC38_spiralOrder {
    public ArrayList<Integer> spiralOrder(int[][] matrix) {
        ArrayList<Integer> res = new ArrayList<>();
        if(matrix==null||matrix.length==0) return res;
        //左上角和右下角定下一个矩阵，每个矩阵的遍历规定好
        int[] leftTop = {0,0};
        int[] rightBottom = {matrix.length-1,matrix[0].length-1};
        while(leftTop[0]<=rightBottom[0]&&leftTop[1]<=rightBottom[1]){
            collectMatrix(res,leftTop,rightBottom,matrix);
            leftTop[0]+=1;
            leftTop[1]+=1;//右下角移动
            rightBottom[0]-=1;
            rightBottom[1]-=1;//左上角移动
        }
        return res;
    }
    public void collectMatrix(ArrayList<Integer> res, int[] leftTop, int[] rightBottom, int[][] matrix){
        int[] cur = {leftTop[0],leftTop[1]};
        //一直向右走
        while(cur[1]<rightBottom[1]){
            res.add(matrix[cur[0]][cur[1]]);
            cur[1] += 1;
        }
        //处理单条的情况
        if(leftTop[0]==rightBottom[0]){
            res.add(matrix[cur[0]][cur[1]]);
            return;
        }
        //走不动了就向下
        while(cur[0]<rightBottom[0]){
            res.add(matrix[cur[0]][cur[1]]);
            cur[0] += 1;
        }
        //走不动就像左
        while(cur[1]>leftTop[1]){
            res.add(matrix[cur[0]][cur[1]]);
            cur[1] -= 1;
        }
        //走不动就向上
        while(cur[0]>leftTop[0]){
            res.add(matrix[cur[0]][cur[1]]);
            cur[0] -= 1;
        }
    }
}
