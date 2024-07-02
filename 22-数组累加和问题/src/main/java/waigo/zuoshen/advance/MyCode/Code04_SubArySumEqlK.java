package waigo.zuoshen.advance.MyCode;

import java.util.HashMap;

/**
 * author waigo
 * create 2021-04-08 9:58
 */

/*
给定一个整数数组和一个整数 k，你需要找到该数组中和为 k 的连续的子数组的个数。
示例 1 :
输入:nums = [1,1,1], k = 2
输出: 2 , [1,1] 与 [1,1] 为两种不同的情况。
说明 :
数组的长度为 [1, 20,000]。
数组中元素的范围是 [-1000, 1000] ，且整数 k 的范围是 [-1e7, 1e7]。
 */
public class Code04_SubArySumEqlK {
    public static int subarraySum(int[] nums, int k) {
        //使用预处理结构，记录前缀和，这样只要看sum-k是否存在就可以确定有没有子数组，不使用窗口是因为数组值有正有负，无法确定单调性
        HashMap<Integer, Integer> sumMapNum = new HashMap<>();//<数组和，有几个>
        sumMapNum.put(0, 1);//默认情况下就有一个sum=0的情况，就是没有加任何位置进去的时候
        int sum = 0;
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sumMapNum.containsKey(sum - k)) {//[0....sum-k......sum]
                ans += sumMapNum.get(sum - k);//一次结算以i结尾和为K的所有情况
            }
            sumMapNum.put(sum, (sumMapNum.containsKey(sum) ? sumMapNum.get(sum) : 0) + 1);//如果出现过sum-k就个数加一，没有就设置为1
        }
        return ans;
    }

    //test:暴力解为什么慢？
    // 1.它每次子数组的和都要重新求一遍，但是可以利用预处理结构前缀和和当前数组和相减从而加速这个过程
    // 2.它每次从当前位置i到尾进行查看，有几个以当前位置出发和能等于k的，这样从自己到数组尾的所有情况都得看一下,得看N-i下
    //按照动态规划和很多算法加速的思想，就是依靠之前求过的东西来对现在要求的东西加速一下。
    //结合1,2可以得到这么一种解决方法。
    //1.用一张表记录[0,i]位置的sum，有就个数加一
    //2.每到一个新位置就看看sum-nums[j]看看有没有在表中存在，一次结算完以这个位置结尾的所有结果
    public static int subarraySum2(int[] nums, int k) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum == k) ans++;
            }
        }
        return ans;
    }

    public static int[] generalRandomAry(int maxLen, int maxVal) {
        int len = (int) (Math.random() * maxLen) + 1;
        int[] ary = new int[len];
        for (int i = 0; i < len; i++)
            ary[i] = (int) (Math.random() * maxVal) - (int) (Math.random() * maxVal);
        return ary;
    }

    public static void main(String[] args) {
        int maxLen = 100, maxVal = 50, testTimes = 1000000;
        int i = 0;
        for (; i < testTimes; i++) {
            int[] nums = generalRandomAry(maxLen, maxVal);
            int k = (int) (Math.random() * maxVal);
            if (subarraySum(nums, k) != subarraySum2(nums, k)) break;
        }
        System.out.println(i == testTimes ? "finish!!!" : "fucking!!!");
    }
}
