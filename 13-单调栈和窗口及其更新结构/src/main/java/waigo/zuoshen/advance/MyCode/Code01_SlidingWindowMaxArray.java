package waigo.zuoshen.advance.MyCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * author waigo
 * create 2021-03-07 17:28
 */
/*
假设一个固定大小为W的窗口，依次划过arr，
返回每一次滑出状况的最大值
例如，arr = [4,3,5,4,3,3,6,7], W = 3
返回：[5,5,5,4,6,7]
*/
public class Code01_SlidingWindowMaxArray {
    //for test
    public static int[] getSWMaxAry(int[] arr, int W) {
        if (arr == null || arr.length < W) return new int[]{};
        int[] ary = new int[arr.length - W + 1];
        for (int i = 0; i < ary.length; i++) {
            int max = arr[i];
            for (int j = i; j < i + W; j++) {
                if (max < arr[j]) {
                    max = arr[j];
                }
            }
            ary[i] = max;
        }
        return ary;
    }

    //for test
    public static int[] getRandomAry(int maxLen, int maxVal) {
        int size = (int) (Math.random() * maxLen) + 1;
        int[] ary = new int[size];
        for (int i = 0; i < ary.length; i++)
            ary[i] = (int) (Math.random() * maxVal);

        return ary;
    }

    public static int[] getSWMaxAry1(int[] arr, int W) {
        if (arr == null || arr.length < W) return new int[]{};
        int[] ary = new int[arr.length - W + 1];
        //维护一个从尾至头单调变大的单调双端队列 存一个值，肯定存下标
        LinkedList<Integer> queue = new LinkedList<>();
        //定义滑动窗口范围[L...R)
        int L = 0, R = 0;
        while (R<arr.length){
            //滑动窗口成形
            while (R - L < W) {
                while (!queue.isEmpty() && arr[queue.peekLast()] <= arr[R]) {
                    queue.pollLast();
                }
                queue.addLast(R);
                R++;
            }
            //开始记录
            ary[L] = arr[queue.peekFirst()];
            if(L==queue.peekFirst()){
                queue.pollFirst();
            }
            L++;//窗口右移
        }
        return ary;
    }
    public static boolean isAryEql(int[] arrA,int[] arrB){
        if(arrA==arrB) return true;
        if(arrA==null||arrA.length!=arrB.length) return false;
        for(int i = 0;i < arrA.length;i++){
            if(arrA[i]!=arrB[i]) return false;
        }
        return true;
    }
    public static void main(String[] args) {
        int testTimes = 10000,maxLen = 10,maxVal = 100;
        int i = 0;
        for(;i<testTimes;i++){
            int[] ary = getRandomAry(maxLen, maxVal);
            int W = (int) (Math.random()*ary.length)+1;
            int[] res = getSWMaxAry(ary, W);
            int[] res1 = getSWMaxAry1(ary, W);
            if(!isAryEql(res,res1)) {
                System.out.println(Arrays.toString(ary));
                System.out.println("res:"+Arrays.toString(res));
                System.out.println("res1:"+ Arrays.toString(res1));
                break;
            }
        }
        System.out.println(i==testTimes?"finish!!!":"fucking");
    }
}
