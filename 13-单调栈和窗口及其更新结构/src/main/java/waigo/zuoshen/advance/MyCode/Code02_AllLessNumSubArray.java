package waigo.zuoshen.advance.MyCode;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * author waigo
 * create 2021-03-07 22:03
 */
/*
给定一个整型数组arr，和一个整数num
某个arr中的子数组sub，如果想达标，必须满足：
sub中最大值 – sub中最小值 <= num，
返回arr中达标子数组的数量
*/
public class Code02_AllLessNumSubArray {

    /*
    思考是否有单调性
    1.一个符合条件的数组A它的每个子数组都符合条件
        因为A.max - A.min <= num,A的任意子数组E都有
        E.max<=A.max,E.min>=A.min
        推出 E.max-E.min <= A.max - A.min <= num，符合条件

    2.找到每个位置出发符合条件的最大子数组，然后就能够得到这个位置开始的所有子数组
    所有位置的子数组的最终的和就是达标子数组的总数量
    */
    public static int getValidAryNum1(int[] arr, int num) {
        if (arr == null || arr.length == 0) return 0;
        int L = 0, R = 0;//窗口范围[L...R)
        LinkedList<Integer> lowestQueue = new LinkedList<>();//最小双端队列
        LinkedList<Integer> biggestQueue = new LinkedList<>();//最大值双端队列

        int res = 0;
        while (L < arr.length) {//每个位置都找到所有的子数组
            if(R<L) R=L;//防止负数的时候出现滑动窗口为空都没有符合条件的，然后R循环break了，R没动，L动了，L>R，窗口破坏了
            while (R < arr.length) {
                //维护最小值队列和最大值队列
                while (!lowestQueue.isEmpty() && arr[lowestQueue.peekLast()] >= arr[R]) {
                    lowestQueue.pollLast();
                }
                while (!biggestQueue.isEmpty() && arr[biggestQueue.peekLast()] <= arr[R]) {
                    biggestQueue.pollLast();
                }
                lowestQueue.addLast(R);
                biggestQueue.addLast(R);
                if(arr[biggestQueue.peekFirst()] - arr[lowestQueue.peekFirst()] > num) break;
                R++;
            }
            res += R-L;//结算一个位置
            if (L == lowestQueue.peekFirst()) {
                lowestQueue.pollFirst();
            }
            if (L == biggestQueue.peekFirst()) {
                biggestQueue.pollFirst();
            }
            L++;//到下一个位置,需要维护一下两个队列，因为窗口动了
        }
        return res;
    }

    //for test
    public static int getValidAryNum(int[] arr, int num) {
        int res = 0;
        //遍历所有子数组
        for (int i = 0; i < arr.length; i++) {
            for (int j = arr.length - 1; j >= i; j--) {
                //一种子数组
                int max = arr[i];
                int min = arr[i];
                for (int z = i + 1; z <= j; z++) {
                    max = Math.max(max, arr[z]);
                    min = Math.min(min, arr[z]);
                }
                if (max - min <= num) res++;
            }
        }
        return res;
    }

    //for test
    public static int[] generalRandomAry(int maxLen, int maxVal) {
        int size = (int) (Math.random() * maxLen) + 1;
        int[] ary = new int[size];
        for (int i = 0; i < size; i++) {
            ary[i] = (int) (Math.random() * maxVal) + 1;
        }
        return ary;
    }

    public static void main(String[] args) {
        int testTimes = 10000, maxLen = 4, maxVal = 100;
        int i = 0;
        for (; i < testTimes; i++) {
            int[] arr = generalRandomAry(maxLen, maxVal);
            int num = (int) (Math.random() * maxVal)-(int) (Math.random() * maxVal);
            int res1 = getValidAryNum1(arr, num);
            int res = getValidAryNum(arr, num);
            if (res1 != res) {
                System.out.println("res:" + res);
                System.out.println("res1:" + res1);
                System.out.println(Arrays.toString(arr));
                System.out.println(num);
                break;
            }
        }
        System.out.println(i == testTimes ? "finish!!!" : "fucking");
    }
}
