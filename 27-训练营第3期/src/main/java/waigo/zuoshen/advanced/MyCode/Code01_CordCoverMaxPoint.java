package waigo.zuoshen.advanced.MyCode;

import java.util.Arrays;
import java.util.Vector;

/**
 * author waigo
 * create 2021-05-05 8:05
 */
/*
给定一个有序数组arr（有正有负），从左到右依次表示X轴上从左往右点的位置
给定一个正整数K，返回如果有一根长度为K的绳子，最多能盖住几个点
绳子的边缘点碰到X轴上的点，也算盖住，所以一条长度为2的绳子能够cover住[1,2,3]
*/
@SuppressWarnings("Duplicates")
public class Code01_CordCoverMaxPoint {
    //思路一：枚举以每个位置结尾的区间能够盖住的最多点是多少，答案必在其中
    //时间复杂度，枚举数组中所有的值，O(N)，每次到一个arr[i]位置，要结算出这个位置能够cover的最多节点数目就看>=arr[i]-k的最左一个位置在哪了
    //找这个最左位置可以使用二分法，正好这里就是一个有序的数组，时间复杂度为O(logN)
    //然后这个位置cover住的点的数量为：i-mostLeft(>=arr[i]-k)+1
    public static int getCoverMaxPoint(int[] arr, int K) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            int mostLeft = getMostLeft(arr, arr[i] - K);
            ans = Math.max(ans, i - mostLeft + 1);
        }
        return ans;
    }

    //找到不小于num的最左位置返回,经典二分法，时间复杂度O(logN)
    public static int getMostLeft(int[] arr, int num) {
        int target = -1;//这个是碰到arr中全部的数都比num要小的时候就返回-1
        int L = 0, R = arr.length - 1;
        int mid;
        while (L <= R) {
            mid = ((R - L) >> 1) + L;
            if (arr[mid] < num) {
                L = mid + 1;
            } else {
                target = mid;//打个标记，然后继续向左走，走到不能再往左为止
                R = mid - 1;
            }
        }
        return target;
    }


    //思路二：
    //使用滑动窗口，找单调性，很明显有单调性，K越大，我cover住的点就越多，或者说我cover住的点的范围扩大，就离那条绳子长度越近
    //1.从每个位置开始维护窗口，窗口右边界往右边扩，扩到不能扩的位置也就是窗口内cover住的长度<=绳子长度了，再扩就超了这样
    //2.右边界停止扩，收集一次答案，然后左边界往右移动，继续下一个位置答案的收集，答案必在其中
    //左右边界不会回退，收集一次答案就是R-L+1，所以时间复杂度O(N)
    public static int getCoverMaxPoint1(int[] arr, int K) {
        int L = 0, R = 0;//[L,R]
        int ans = 0;
        while (L < arr.length && R < arr.length - 1) {
            if (arr[R + 1] <= arr[L] + K) {
                R++;
            } else {
                ans = Math.max(ans, R - L + 1);
                L++;
            }
        }
        ans = Math.max(ans, R - L + 1);//抓一下最后出来的那次的值
        return ans;
    }

    public static int awesomeMore(int[] arr, int K) {
        int L = 0;
        int R = 0;//[L,R)
        int N = arr.length;
        int ans = 0;
        while (L < N) {
            while (R < N && arr[R] - arr[L] <= K) {//注意，[1,2]这是cover住长度为1的绳子
                R++;
            }
            ans = Math.max(ans, R - L++);
        }
        return ans;
    }


    public static int[] generalRandomAry(int maxLen, int maxVal) {
        int size = (int) (Math.random() * maxLen) + 1;
        int[] ary = new int[size];
        for (int i = 0; i < size; i++)
            ary[i] = (int) (Math.random() * maxVal) - (int) (Math.random() * maxVal);
        Arrays.sort(ary);
        return ary;
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxVal = 100;
        int testTimes = 1000000;
        int i = 0;
        for (; i < testTimes; i++) {
            int[] arr = generalRandomAry(maxLen, maxVal);
            int k = (int) (Math.random() * maxLen) + 1;
            if (getCoverMaxPoint(arr, k) != maxCover2(arr, k)) {
                System.out.println(Arrays.toString(arr));
                System.out.println("k:"+k);
                System.out.println(getCoverMaxPoint(arr, k));
                System.out.println(maxCover2(arr, k));
                break;
            }
        }

        System.out.println(i == testTimes ? "finish!!!" : "fucking!!!");

    }

    /******************************************************************************/
    //1.枚举法，绳子以某个位置结尾的情况下最多盖住多少个点，那就是找mostLeft(>=(i-k))的最左侧位置，然后i-mostLeft+1就是盖住的点数
    public static int maxCover(int[] arr, int K) {
        if (arr == null || arr.length == 0) return 0;
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            int mostLIdx = mostLeft(arr, arr[i] - K);
            max = Math.max(max, i - mostLIdx + 1);
        }
        return max;
    }

    //找不到返回0
    private static int mostLeft(int[] arr, int num) {
        int res = 0;
        int L = 0, R = arr.length - 1;
        while (L <= R) {
            int mid = (L + R) >> 1;
            if (arr[mid] >= num) {
                R = mid - 1;
                res = mid;
            } else {
                L = mid + 1;
            }
        }
        return res;
    }

    //2.滑动窗口，很明显这里都是下标，所以是有序的，能够找到单调性，滑动窗口能够保持好,窗口不会退，可以O(N)
    public static int maxCover2(int[] arr, int K) {
        if (arr == null || arr.length == 0) return 0;
        int L = 0, R = 0;//[L,R),窗口长度为R-L
        int res = 0;
        while (R < arr.length) {
            if (arr[R] - arr[L] <= K) {
                R++;
            } else {
                res = Math.max(R-L, res);
                L++;
            }
        }
        return Math.max(R-L, res);
    }
}
