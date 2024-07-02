package waigo.zuoshen.advance.MyCode;

import java.util.Arrays;

/**
 * author waigo
 * create 2021-04-14 9:05
 */
/*
给定一个整数组成的无序数组arr，值可能正、可能负、可能0
给定一个整数值K
找到arr的所有子数组里，哪个子数组的累加和<=K，并且是长度最大的
返回其长度
*/
public class Code03_LongestLessSumSubArrayLength {
    //暴力解test
    public static int maxLengthForce(int[] arr, int k) {
        int len = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (isValid(i, j, arr, k)) {
                    len = Math.max(len, j - i + 1);
                }

            }
        }
        return len;
    }

    private static boolean isValid(int l, int r, int[] arr, int k) {
        int sum = 0;
        for (int i = l; i <= r; i++) {
            sum += arr[i];
        }
        return sum <= k;
    }

    //分析暴力解问题，重复求解子数组的和，可以使用前缀和数组进行优化，不需要求解那么多子数组，有些答案可以直接淘汰，
    //比如从1开始找到的<=K的最长子数组长度为6，那么从2开始到7都不会更新答案的，直接看看2到8会不会有答案
    //本质上还是O(N^2)的算法，比如k=0的时候，然后arr里一个0都没有，你得把全部子数组都遍历一遍
    public static int maxLengthOptimism(int[] arr, int k) {
        int[] prefixSumAry = generalPrefixSum(arr);
        int len = 0;
        for (int begin = 0; begin < arr.length; begin++) {
            for (int end = len + begin; end < arr.length; end++) {
                if (isValid2(begin, end, prefixSumAry, k, arr)) {
                    len = Math.max(len, end - begin + 1);
                }
            }
            //看看此时begin+len是否已经超出数组长度了，超过了说明已经找到最左边符合答案的位置了，break
            if (begin + len > arr.length) break;
        }
        return len;
    }

    private static boolean isValid2(int begin, int end, int[] prefixSumAry, int k, int[] arr) {
        return prefixSumAry[end] - prefixSumAry[begin] + arr[begin] <= k;
    }

    //分析优化版本的问题，虽然利用之前求过的最长答案淘汰了一些子数组，但是某个i开始其实是得不到答案的，它的最小数组和都大于K
    //所以如果能够知道某个位置i开始往后的子数组中最小的数组和的话，这个位置可不可能出现答案马上能知道end就能不断往右了，
    // 时间复杂度就能优化到O(1).需要一个minSums和与之对应的minSumEnds
    //两层循环：外层控制变量为begin，内层控制变量为end
    //end受len影响
    //内层循环没进去   end不变，begin++
    //内层循环进去了   end变大，begin++
    //end最大值为arr.length,begin也是，所以这个时间复杂度O(N)
    public static int maxLengthAwesome(int[] arr, int k) {
        int[] minSums = new int[arr.length];
        int[] minSumEnds = new int[arr.length];
        minSums[arr.length - 1] = arr[arr.length - 1];
        minSumEnds[arr.length - 1] = arr.length - 1;
        for (int i = arr.length - 2; i >= 0; i--) {
            minSums[i] = arr[i] + (minSums[i + 1] <= 0 ? minSums[i + 1] : 0);
            minSumEnds[i] = minSums[i + 1] <= 0 ? minSumEnds[i + 1] : i;
        }
        int len = 0;//[begin...end]
        int end;
        int sum;//sum-->[begin,end),自己维护
        int[] prefixSum = generalPrefixSum(arr);//前缀和数组
        for (int begin = 0; begin < arr.length; begin++) {
            if (begin + len >= arr.length) break;
            end = begin + len;
            sum = end - 1 < begin ? 0 : prefixSum[end - 1] - prefixSum[begin] + arr[begin];
            while (end < arr.length && sum + minSums[end] <= k) {//解决了之前某个位置都不可能有答案了还遍历它所有可能的浪费
                sum = sum + minSums[end];//维护sum
                end = minSumEnds[end] + 1;//minSums[i]-->[end...k]的和，minSumEnds[end]-->k
            }
            len = Math.max(len, (end > arr.length ? arr.length : end) - begin);
        }
        return len;
    }
    /*
       其实这种方法还是利用了单调性，就是需要确定某个位置我扩出去，最大扩的范围能够满足要求，只要找到这个位置后面的就不用看了，后面的就不符合要求了
       [begin,end),从begin扩到end-1是最大满足要求的，end就不满足要求了，这就满足了单调性
       然后begin之后的位置，如果不能更新end的位置那么就舍弃，不必要计算的答案
       如果直到begin==end了都还不能更新end的位置，说明minSum[end]的值太大了，end直接后移就好了
       书写代码的时候需要做好考虑再动手，尽量一次循环中少移动多个边界，不然需要进行一些相关的边界处理，就像下面的同时移动了begin和end的话，
       其实是没有起到任何优化的，时间复杂度还是O(N)，但是代码变得可读性差多了，有很多边界处理
    */
    public static int awesomeMore(int[] arr, int K) {
        if (arr == null || arr.length == 0) return 0;
        int[] minSums = new int[arr.length];
        minSums[arr.length - 1] = arr[arr.length - 1];
        int[] minSumEnds = new int[arr.length];
        minSumEnds[arr.length - 1] = arr.length - 1;
        for (int i = arr.length - 2; i >= 0; i--) {
            minSums[i] = arr[i] + (minSums[i + 1] <= 0 ? minSums[i + 1] : 0);
            minSumEnds[i] = minSums[i + 1] <= 0 ? minSumEnds[i + 1] : i;
        }
        int len = 0;
        int end = 0, sum = 0;
        for (int begin = 0; begin < arr.length; begin++) {
            while (end < arr.length && sum + minSums[end] <= K) {//[begin,end)符合条件
                sum += minSums[end];
                end = minSumEnds[end] + 1;
            }
            len = Math.max(len,end-begin);
            if (begin < end) {
                sum -= arr[begin];//收左边界
            } else {//窗口没数了，表示从begin开始到end已经没有能够使end扩张的位置了，这个end位置结束了
                end = begin + 1;
            }
        }
        return len;
    }

    private static int[] generalPrefixSum(int[] arr) {
        int[] res = new int[arr.length];
        res[0] = arr[0];
        for (int i = 1; i < res.length; i++) {
            res[i] = res[i - 1] + arr[i];
        }
        return res;
    }

    public static int[] generalRandomAry(int maxLen, int maxVal) {
        int size = (int) (Math.random() * maxLen) + 1;
        int[] ans = new int[size];
        for (int i = 0; i < size; i++) {
            ans[i] = (int) (Math.random() * maxVal) - (int) (Math.random() * maxVal);
        }
        return ans;
    }

    public static void main(String[] args) {
        int maxLen = 50;
        int maxVal = 50, testTime = 1000000, i = 0;
        for (; i < testTime; i++) {
            int[] randomAry = generalRandomAry(maxLen, maxVal);
            int K = (int) (Math.random() * maxVal);
            int res1 = maxLengthForce(randomAry, K);
            int res2 = awesomeMore(randomAry, K);
            if (res1 != res2) {
                System.out.println(Arrays.toString(randomAry));
                System.out.println("K:" + K);
                System.out.println("res1:" + res1);
                System.out.println("res2:" + res2);
                break;
            }
        }
        System.out.println(i == testTime ? "finish!!!" : "fucking!!!");

    }

}
