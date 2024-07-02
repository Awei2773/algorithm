package waigo.zuoshen.advance.MyCode;

import java.util.Arrays;

/**
 * author waigo
 * create 2021-04-10 13:23
 */
//给定一个正整数组成的无序数组arr，给定一个正整数值K
//找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的
//返回其长度
public class Code01_LongestSumEqlKSubArrayLengthInPositiveArray {
    public static int getMaxLength(int[] arr, int K) {
        if (arr == null || arr.length == 0 || K < 0) return 0;
        int len = 0;
        int L = 0, wSum = arr[0], R = 0;//窗口[L...R]
        while (R < arr.length) {
            if (wSum <= K) {//窗口右边界扩一个
                if (wSum == K && R >= L) {//可能窗口下一个是0，所以继续右扩看看还有没有更大的窗口使得wSum==K
                    len = Math.max(len, R - L + 1);
                }
                if (++R == arr.length) break;
                wSum += arr[R];
            } else {//窗口大了，此时L压住的点的最大答案以及拿到了，换下一个
                wSum -= arr[L++];
            }
        }
        return len;
    }

    //test
    public static int right(int[] arr, int K) {
        if (arr == null || arr.length == 0 || K < 0) return 0;
        int len = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (valid(i, j, arr, K)) {
                    len = Math.max(len, j - i + 1);
                }
            }
        }
        return len;
    }

    private static boolean valid(int l, int r, int[] arr, int k) {
        int sum = 0;
        for (int i = l; i <= r; i++) {
            sum += arr[i];
        }
        return sum == k;
    }

    public static int[] generalRandomAry(int maxLen, int maxVal) {
        int size = (int) (Math.random() * maxLen) + 1;
        int[] ans = new int[size];
        for (int i = 0; i < size; i++) {
            ans[i] = (int) (Math.random() * maxVal);
        }
        return ans;
    }

    public static void main(String[] args) {
        int maxLen = 10;
        int maxVal = 10, testTime = 1000000, i = 0;
        for (; i < testTime; i++) {
            int[] positiveAry = generalRandomAry(maxLen,maxVal);
            int K =(int) (Math.random() * maxVal);
            int res1 = getMaxLength(positiveAry, K);
            int res2 = right(positiveAry, K);
            if (res1 != res2) {
                System.out.println(Arrays.toString(positiveAry));
                System.out.println("K:" + K);
                System.out.println("res1:" + res1);
                System.out.println("res2:" + res2);
                break;
            }
        }
        System.out.println(i == testTime ? "finish!!!" : "fucking!!!");

    }
}
