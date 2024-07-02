package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-22 22:49
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 给定两个有序数组arr1和arr2，再给定一个正数K
 *
 * 求两个数累加和最大的前K个，两个数必须分别来自arr1和arr2
 *
 * 最大那K个肯定在右下角连在一块，所以设计算法从右下角开始抓TopK就好了，
 * 这样能够做到O(k)，而如果遍历整个二维表抓TopK就是O(N^2)了，很明显左上角那些玩意儿
 * 不可能是TopK中的。
 */
public class Code05_TopKSumCrossTwoAry {
    public static class TopKNode{
        int row;
        int col;
        int value;

        public TopKNode(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }
    }
    public static int[] getTopKSum(int[] arr1,int[] arr2,int K){
        if(arr1==null||arr1.length==0||arr2==null||arr2.length==0) return new int[]{};
        int cols = arr2.length;
        int rows = arr1.length;
        K = Math.min(K, rows * cols);
        int[] res = new int[K];
        PriorityQueue<TopKNode> mHeap = new PriorityQueue<>(K,(o1,o2)->o2.value-o1.value);
        int write = 0;
        mHeap.add(new TopKNode(rows-1,cols-1,arr1[rows-1]+arr2[cols-1]));
        boolean[][] isVisited = new boolean[rows][cols];
        while (!mHeap.isEmpty()&&write<K){
            TopKNode curMax = mHeap.poll();
            res[write++] = curMax.value;
            if(curMax.row-1>=0&&(!isVisited[curMax.row-1][curMax.col])){
                mHeap.add(new TopKNode(curMax.row-1,curMax.col,arr1[curMax.row-1]+arr2[curMax.col]));
                isVisited[curMax.row-1][curMax.col]=true;
            }
            if(curMax.col-1>=0&&(!isVisited[curMax.row][curMax.col-1])){
                mHeap.add(new TopKNode(curMax.row,curMax.col-1,arr1[curMax.row]+arr2[curMax.col-1]));
                isVisited[curMax.row][curMax.col-1]=true;
            }
        }
        return res;
    }
    public static int[] getTopKSum2(int[] arr1,int[] arr2,int K){
        if(arr1==null||arr1.length==0||arr2==null||arr2.length==0) return new int[]{};
        int cols = arr2.length;
        int rows = arr1.length;
        K = Math.min(K, rows * cols);
        PriorityQueue<Integer> sHeap = new PriorityQueue<>(K,Comparator.comparingInt(o->o));
        int[] res = new int[K];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int curSum = arr1[row] + arr2[col];
                if(sHeap.size()<K){
                    sHeap.add(curSum);
                }else if(!sHeap.isEmpty()&&curSum>sHeap.peek()){
                    sHeap.poll();
                    sHeap.add(curSum);
                }
            }
        }
        int write = 0;
        while (!sHeap.isEmpty()){
            res[write++] = sHeap.poll();
        }
        return res;
    }
    //for test
    public static int[] getRandomAry(int maxVal,int maxSize){
        int size = (int) (Math.random()*maxSize)+1;
        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            res[i] = (int) (Math.random()*maxVal)+1;
        }
        Arrays.sort(res);
        return res;
    }
    public static void main(String[] args) {
        int maxSize = 10;
        int maxVal = 10;
        int testTimes = 1000000;
        int i = 0;
        for (;i  < testTimes;i ++) {
            int[] a1 = getRandomAry(maxVal, maxSize);
            int[] a2 = getRandomAry(maxVal,maxSize);
            int K = (int) (Math.random()*(a1.length*a2.length))+1;
            int[] topKSum = getTopKSum(a1, a2, K);
            Arrays.sort(topKSum);
            int[] topKSum2 = getTopKSum2(a1, a2, K);
            Arrays.sort(topKSum2);
            if(!Arrays.equals(topKSum,topKSum2)){
                System.out.println(Arrays.toString(topKSum));
                System.out.println(Arrays.toString(topKSum2));
                break;
            }
        }
        System.out.println(i==testTimes?"finish!!!":"fucking!!!");
    }
}
