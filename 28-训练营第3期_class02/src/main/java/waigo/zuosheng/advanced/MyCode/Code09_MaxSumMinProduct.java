package waigo.zuosheng.advanced.MyCode;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * author waigo
 * create 2021-05-09 11:19
 */
/*
一个数组的 最小乘积 定义为这个数组中 最小值 乘以 数组的 和 。
比方说，数组 [3,2,5] （最小值是 2）的最小乘积为 2 * (3+2+5) = 2 * 10 = 20 。
给你一个正整数数组 nums ，请你返回 nums 任意 非空子数组 的最小乘积 的 最大值 。
由于答案可能很大，请你返回答案对  109 + 7 取余 的结果。
请注意，最小乘积的最大值考虑的是取余操作 之前 的结果。题目保证最小乘积的最大值在
不取余 的情况下可以用 64 位有符号整数 保存。
子数组 定义为一个数组的 连续 部分。

示例 1：

输入：nums = [1,2,3,2]
输出：14
解释：最小乘积的最大值由子数组 [2,3,2] （最小值是 2）得到。
2 * (2+3+2) = 2 * 7 = 14 。
示例 2：

输入：nums = [2,3,3,1,2]
输出：18
解释：最小乘积的最大值由子数组 [3,3] （最小值是 3）得到。
3 * (3+3) = 3 * 6 = 18 。
示例 3：

输入：nums = [3,1,5,6,4,2]
输出：60
解释：最小乘积的最大值由子数组 [5,6,4] （最小值是 4）得到。
4 * (5+6+4) = 4 * 15 = 60 。


提示：

1 <= nums.length <= 10^5
1 <= nums[i] <= 10^7

思路：
枚举所有位置为最小值的情况，答案必在其中，所以关键点在于如何简化随意i位置以它为最小值的区间的求解
单调栈解决的就是这种问题，单调栈能够求解出某个位置左右比它大或者比它小的最近位置，我们这里要的是
某个位置左边比它小的最近位置和右边比它小的最近位置，单调栈设置为从小到大

找到区间了，下一步就是要求这个区间的和了，可以用前缀和数组来快速求解
*/
public class Code09_MaxSumMinProduct {
    public static int maxSumMinProduct(int[] nums) {
        long ans = Integer.MIN_VALUE;
        long MOD = 1000000007;
        for (int i = 0; i < nums.length; i++) {
            int L = i, R = i;//[L,R]
            long sum = nums[i];
            while (L - 1 >= 0 && nums[L - 1] >= nums[i]) {
                L--;
                sum += nums[L];
            }
            while (R + 1 < nums.length && nums[R + 1] >= nums[i]) {
                R++;
                sum += nums[R];
            }
            ans = Math.max(ans, nums[i] * sum);
        }
        return (int)(ans%MOD);
    }

    //左右找到小于i位置的区间之后，(l,r),这个区间的和为prefixSum[r-1]-prefixSum[l]
    public static int maxSumMinProduct2(int[] nums) {
        long ans = Integer.MIN_VALUE;
        long MOD = 1000000007;
        //前缀和求解
        long[] prefixSum = new long[nums.length];
        prefixSum[0] = nums[0];
        for (int i = 1; i < nums.length; i++)
            prefixSum[i] = nums[i] + prefixSum[i - 1];
        //最近小位置
        int[][] idxs = getNearestLessIdx2(nums);
        //枚举所有位置拿答案
        for (int i = 0; i < nums.length; i++) {
            int r = idxs[i][1] == -1 ? nums.length - 1 : (idxs[i][1] - 1);
            long l = idxs[i][0] == -1 ? 0 : prefixSum[idxs[i][0]];
            ans = Math.max(ans, (prefixSum[r] - l) * nums[i]);
        }
        return (int)(ans%MOD);
    }

    //没有重复值的单调栈,idx[i][0]==-1||idx[i][0]<num.length,idx[i][1]==-1||idx[i][1]>0
    public static int[][] getNearestLessIdx(int[] nums) {
        if (nums == null || nums.length == 0) return new int[][]{};
        int[][] res = new int[nums.length][2];
        Stack<Integer> stack = new Stack<>();
        //从小到大栈,存的都是下标
        for (int i = 0; i < nums.length; i++) {
            //1.为空直接进
            //2.有则判断是否小于栈顶，小于要一直弹到能够进为止
            while (!stack.isEmpty() && nums[i] < nums[stack.peek()]) {
                Integer cur = stack.pop();
                res[cur][0] = stack.isEmpty() ? -1 : stack.peek();
                res[cur][1] = i;
            }
            stack.push(i);
        }
        //将栈内剩余的给结算
        while (!stack.isEmpty()) {
            Integer pop = stack.pop();
            res[pop][0] = stack.isEmpty() ? -1 : stack.peek();
            res[pop][1] = -1;
        }
        return res;
    }

    //可能有重复值的单调栈
    public static int[][] getNearestLessIdx2(int[] nums) {
        if (nums == null || nums.length == 0) return new int[][]{};
        int[][] res = new int[nums.length][2];
        Stack<List<Integer>> stack = new Stack<>();
        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[i] < nums[stack.peek().get(0)]) {
                //弹出一批并且结算
                List<Integer> pop = stack.pop();
                int leftNearLessIdx = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                for (int j = 0; j < pop.size(); j++) {
                    Integer cur = pop.get(j);
                    res[cur][0] = leftNearLessIdx;//应该拿最后一个，因为是最近的
                    res[cur][1] = i;
                }
            }
            if (!stack.isEmpty() && nums[i] == nums[stack.peek().get(0)]) {
                stack.peek().add(i);
            } else {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(i);
                stack.push(list);
            }
        }
        //弹完进行结算
        while (!stack.isEmpty()) {
            List<Integer> pop = stack.pop();
            int leftNearLessIdx = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
            for (int j = 0; j < pop.size(); j++) {
                Integer cur = pop.get(j);
                res[cur][0] = leftNearLessIdx;//应该拿最后一个，因为是最近的
                res[cur][1] = -1;
            }
        }
        return res;
    }
    public static int[] generalRandomArr(int maxLen,int maxVal){
        int size = (int) (Math.random()*maxLen)+1;
        int[] res = new int[size];
        for(int i = 0;i<size;i++){
            res[i] = (int) (Math.random()*maxVal);
        }
        return res;
    }
    public static void main(String[] args) {
//        int[] a = {63429, 60800, -16042, -23872, 40464, 58854, -64424};
//        System.out.println(maxSumMinProduct2(a));
//        System.out.println(maxSumMinProduct(a));

        int testTime = 100;
        int i = 0;
        int maxLen = 100000;
        int maxVal = 10000000;
        for(;i<testTime;i++){
            int[] nums = generalRandomArr(maxLen, maxVal);
            long res1 = maxSumMinProduct2(nums);
            long res2 = maxSumMinProduct(nums);
            if(res1 != res2){
                System.out.println(Arrays.toString(nums));
                System.out.println("maxSumMinProduct2:"+res1);
                System.out.println("maxSumMinProduct:"+res2);
                break;
            }
        }
        System.out.println(i==testTime?"finish!!!":"fucking!!!");

//        System.out.println(Math.pow(10,9)+7==1e+7);
    }
}
