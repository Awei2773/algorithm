package waigo.zuoshen.basic.MyCode;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-18 9:06
 */
public class Code05_SortArrayDistanceLessK {
    public static void sortedArrDistanceLessK(int[] arr, int k) {
        PriorityQueue<Integer> smallHeap = new PriorityQueue<>(k + 1);
        //0~k-1入堆，这里面一定有个最小值
        int initHeapSize = Math.min(k, arr.length);
        for (int i = 0; i < initHeapSize; i++)
            smallHeap.add(arr[i]);
        //从现在开始将arr遍历完，入堆一个就弹出一个写回arr
        int i = initHeapSize;
        for (; i < arr.length; i++) {
            smallHeap.add(arr[i]);
            if (!smallHeap.isEmpty()) arr[i - initHeapSize] = smallHeap.poll();
        }
        //将堆中的全部写回
        int beginIdx = i == initHeapSize ? 0 : i - initHeapSize;
        while (!smallHeap.isEmpty()) {
            arr[beginIdx++] = smallHeap.poll();
        }
    }

    public static void swap(int[] ary, int aIdx, int bIdx, boolean[] isSwap) {
        int temp = ary[aIdx];
        ary[aIdx] = ary[bIdx];
        ary[bIdx] = temp;
        isSwap[aIdx] = true;
        isSwap[bIdx] = true;
    }

    public static int[] generalRandomAryNoMoveThanK(int maxSize, int maxValue, int k) {
        int size = (int) (Math.random() * maxSize);//0~maxSize-1
        int[] ary = new int[size];
        for (int i = 0; i < size; i++)
            ary[i] = (int) (Math.random() * maxValue) - (int) (Math.random() * maxValue);
        Arrays.sort(ary);//排序
        //和一个k步之内位置进行交换，每个位置只能交换一次
        boolean[] isSwap = new boolean[size];
        int relativeIdx, randomIdx;
        for (int i = 0; i < size; i++) {
            if (!isSwap[i]) {
                while (true) {//generalValidRandomIndex
                    relativeIdx = (int) (Math.random() * k) - (int) (Math.random() * k);//[-k+1,k-1]
                    randomIdx = i + relativeIdx;//维护一个工作窗口
                    if (randomIdx >= 0 && randomIdx < size) break;
                }
                if (!isSwap[randomIdx]) {
                    swap(ary, i, randomIdx, isSwap);
                }
            }
        }
        return ary;
    }

    public static boolean isAryEql(int[] aAry, int[] bAry) {
        if (aAry == bAry) return true;//都是null或者是同个数组
        if (aAry.length != bAry.length) return false;
        for (int i = 0; i < aAry.length; i++) {
            if (aAry[i] != bAry[i]) return false;
        }
        return true;
    }

    public static int[] copyAry(int[] origin) {
        if (origin == null) return null;
        int[] newAry = new int[origin.length];
        for (int i = 0; i < newAry.length; i++) {
            newAry[i] = origin[i];
        }
        return newAry;
    }

    public static void main(String[] args) {
        int testTimes = 10000, maxSize = 10, maxValue = 10, k = 2;
        int i = 0;
        for (; i < testTimes; i++) {
            int[] ary = generalRandomAryNoMoveThanK(maxSize, maxValue, k);
            int[] cpyAry = copyAry(ary);
            Arrays.sort(cpyAry);
            sortedArrDistanceLessK(ary, k);
            if (!isAryEql(ary, cpyAry)) {
                Logger.getGlobal().info("OOPS!!!");
                break;
            }

        }
        Logger.getGlobal().info(i == testTimes ? "finish!!!" : "fucking!!!");
    }
}
