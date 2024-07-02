package waigo.zuoshen.advanced.MyCode;

import java.util.Arrays;

/**
 * author waigo
 * create 2021-05-12 11:41
 */

/*
Given an integer array nums, return the length of the longest strictly increasing subSequence.
A subsequence is a sequence that can be derived from an array by deleting some or no elements
without changing the order of the remaining elements. For example, [3,6,2,7] is a subSequence of the array [0,3,1,6,2,2,7].

Example 1:

Input: nums = [10,9,2,5,3,7,101,18]
Output: 4
Explanation: The longest increasing subSequence is [2,3,7,101], therefore the length is 4.
Example 2:

Input: nums = [0,1,0,3,2,3]
Output: 4
Example 3:

Input: nums = [7,7,7,7,7,7,7]
Output: 1
 
Constraints:
1 <= nums.length <= 2500
-104 <= nums[i] <= 104
 
Follow up:
Could you come up with the O(n2) solution?
Could you improve it to O(n log(n)) time complexity?

思路分析：
分析数据量，这题数组长度为2500以下，所以O(N^2)的算法能过
一、暴力法：
枚举所有位置，求出必须以某个位置结尾时候的最长子序列长度，这些答案求个Max必是答案。

递归函数定义：
int process(int[] arr,int x):返回arr数组中，[0...x]这一段子数组中必须以x位置结尾的最长递增子序列

注意：这题和那个求最长公共子序列不太一样，那个是只要有序列就好我后面新加进序列的和之前的是没有关系的，所以可以将
递归设置为[0...x]上的最长子序列，但是不要求以x位置结尾。但是这里你新加一个进序列的时候要求这个新的值比序列中所有
位置的值都要大，所以需要记录你每个序列的最大值。因此，递归设置成这样。

一：base case
x=0,必然是1，因为就一个数
二:寻常x，0<x<arr.length
遍历process[0...x-1]，只要<arr[x]就抓一遍答案。
最终拿出最大的那个

时间复杂度O(N^2)，因为每个位置的求解都要看一遍它之前的所有位置
空间复杂度O(N):最后一个位置再求的时候最大递归压栈O(N)

暴力解可以优化成动态规划，不要相同位置老是去求大过程，不过O(N^2)是不可避免的,能快，但是复杂度不能快

复杂度这么高的关键点在于每次都要去遍历寻找前面一个值小于当前位置的位置i，且它的子序列最多，能否优化？
遍历在所难免，是否能够找到啥单调性加速遍历的过程。


我往前找，我想知道前面的随意一个位置i，想知道i结尾的序列长度，然后所有的i位置pk出一个最长的子序列作为我当前位置的子序列值。
那么我遍历其实就是想知道一件事，在我当前位置的值能够cover住的基础上我希望你子序列尽可能长。

这里其实可以贪心一下，我只需要记录一下之前出现过的位置的最大子序列中
1.相同长度时候尽可能小的那个值
2.所有的长度对应的值

这是一种专门解决递增子序列问题的算法模型，ends数组
性质
1.ends数组中，ends[i]表示子序列长度为i+1时，最小的结尾为ends[i]
ends数组只有填过的位置才具有以上性质，所有用一个right来做有效区的界限，[0...right]才是具有性质的

在定义一个东西的时候最好将它的意思标注一下，不然代码里有歧义了就麻烦了。
 */
public class Code01_LongestIncreasingSubSeq {
    public static int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int max = 1;
        for (int i = 0; i < nums.length; i++) {
            max = Math.max(max, process(nums, i));
        }
        return max;
    }

    //返回arr数组中，必须以x结尾的[0...x]这一段子数组的最长递增子序列

    private static int process(int[] arr, int x) {
        if (x == 0) return 1;
        int res = 1;
        for (int i = 0; i < x; i++) {
            res = arr[i] < arr[x] ? Math.max(res, process(arr, i) + 1) : res;
        }
        return res;
    }

    //动态规划
    public static int lengthOfLIS2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int N = nums.length;
        int[] dp = new int[N];
        dp[0] = 1;
        int max = 1;
        for (int x = 1; x < N; x++) {
            //填x位置
            dp[x] = 1;
            for (int i = 0; i < x; i++) {
                dp[x] = nums[i] < nums[x] ? Math.max(dp[x], dp[i] + 1) : dp[x];
            }
            max = Math.max(dp[x], max);//抓一个点的答案
        }
        return max;
    }
    public static int lengthOfLIS3(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int N = nums.length;
        int[] ends = new int[N];
        ends[0] = nums[0];
        int endIdx = 0;//endIdx+1表示当前最长的子序列
        for(int x = 1;x<N;x++){
            //二分找到nums[x]能cover住的最长子序列
            //大于等于的最左的下标，其实我们要减一变成小于nums[x]的最右，然后加一变成长度
            int mostCoverLen = findMostCoverLen(ends, endIdx, nums[x]);
            mostCoverLen = mostCoverLen==-1?endIdx+1:mostCoverLen;
            //当前x位置的最长子序列
            int xRes = mostCoverLen+1;//必是1<=xRes<=endIdx+2,因为每个位置的子序列都是往前看的，最多也就是之前出现过的最长序列加一
            if(xRes-1>endIdx){
                ends[++endIdx]=nums[x];
            }else{
                ends[xRes-1] = nums[x]<ends[xRes-1]?nums[x]:ends[xRes-1];
            }
        }
        return endIdx+1;
    }

    //找到0...endIdx中最左一个大于等于target的下标
    private static int findMostCoverLen(int[] nums, int endIdx, int target) {
        int begin = 0;
        int res = -1;
        while (begin<=endIdx){
            int mid = ((endIdx-begin)>>>1)+begin;
            if(nums[mid]>=target){
                res = mid;
                endIdx = mid-1;
            }else{
                begin = mid+1;
            }
        }
        return res;
    }

    //ends数组的awesome版本
    public static int lengthOfLIS4(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int N = nums.length;
        int[] ends = new int[N];
        ends[0] = nums[0];//初始化，在长度为1的时候最小结尾肯定是第一个数
        int right = 0;//有效区间[0...right]
        int l,r,m;//做二分,加速找到大于等于nums[i]的最左位置
        int target;//想找到的大于等于的最左位置
        for(int i = 1;i<nums.length;i++){
            l = 0;
            r = right;
            target = -1;
            while (l<=r){
                m = ((r-l)>>>1)+l;
                if(ends[m]>=nums[i]){
                    r = m-1;
                    target = m;
                }else{
                    l = m+1;
                }
            }
            target = target==-1?(++right):target;//如果大于等于的最左位置不存在，那就要扩充了，表示之前
            //的值背后那些子序列的结尾都比我当前位置的要小，子序列最长长度肯定得更新了

            //要么更新，要么改掉之前某个长度的子序列的结尾
            ends[target] = nums[i];
            //当前位置结尾产生的最长子序列为i+1
        }
        return right+1;
    }
    public static int[] generalRandomAry(int maxLen, int maxVal) {
        int size = (int) (Math.random() * maxLen) + 1;
        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            res[i] = (int) (Math.random() * maxVal) - (int) (Math.random() * maxVal);
        }
        return res;
    }

    public static void main(String[] args) {
        int i = 0;
        int testTimes = 1000000;
        for (; i < testTimes; i++) {
            int[] nums = generalRandomAry(10, 10);
            if (lengthOfLIS4(nums) != lengthOfLIS(nums)) {
                System.out.println(Arrays.toString(nums));
                System.out.println(lengthOfLIS4(nums));
                System.out.println(lengthOfLIS(nums));
                break;
            }
        }
        System.out.println(i==testTimes?"finish!!!":"fucking!!!");
    }
    public static int[] LIS (int[] arr) {
        // write code here
        if(arr==null||arr.length==0) return new int[]{};
        int N = arr.length;
        int[] lis = new int[N];//lis[i]表示长度为i+1的时候最小的结尾
        lis[0] = arr[0];
        int right = 1;//right~N-1表示无效区域,有效区域为[0~right)
        //每次一个arr[j]都去找大于等于arr[j]的最左一个位置
        int[] dp = new int[N];
        dp[0] = 1;
        for(int i = 1;i<N;i++){
            int L = 0,R = right-1;
            int target = right;
            while(L<=R){
                int mid = (R+L)>>1;
                if(lis[mid]>=arr[i]){
                    target = mid;
                    R = mid-1;
                }else{
                    L = mid+1;
                }
            }
            if(target==right) {
                right++;
            }
            lis[target] = arr[i];
            dp[i] = target+1;
        }
        int[] res = new int[right];
        int w = right-1;
        for(int i = N-1;i>=0;i--){
            if(dp[i]==right){
                res[w--] = arr[i];
                right--;
            }
        }
        return res;
    }
}
