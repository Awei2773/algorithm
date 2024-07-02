package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-29 8:38
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个正数数组 arr，其中所有的值都为整数，以下是最小不可组成和的概念:
 * 把 arr 每个子集内的所有元素加起来会出现很多值，其中最小的记为 min，最大的记为max 在区间[min,max]上，
 * 如果有数不可以被arr某一个子集相加得到，那么其中最小的那个数是arr 的最小不可组成和 在区间[min,max]上，
 * 如果所有的数都可以被arr的某一个子集相加得到，那么max+1是arr的最 小不可组成和
 * 请写函数返回正数数组 arr 的最小不可组成和。
 * 【举例】
 * arr=[3,2,5]。子集{2}相加产生 2 为 min，子集{3,2,5}相加产生 10 为 max。在区间[2,10] 上，
 * 4、 6 和 9 不能被任何子集相加得到，其中 4 是 arr 的最小不可组成和。 arr=[1,2,4]。
 * 子集{1}相加产生 1 为 min，子集{1,2,4}相加产生 7 为 max。在区间[1,7]上， 任何 数都可以被子集相加得到，
 * 所以 8 是 arr 的最小不可组成和。
 * 【进阶】
 * 如果已知正数数组 arr 中肯定有 1 这个数，是否能更快地得到最小不可组成和?
 */
public class Code02_SmallestUnFormedSum {
    //不确定有1
    //完全没有单调性，比如3,2,4,6，需要看的是2~10，你一开始看到有2，然后看到有3，还有4
    //接下来需要看有没有5，虽然没有5，但是3和2可以合成5，这是你无法知道的，所以只能将这个数组
    //所有的可能性给抓出来，然后从min~max一直看哪些没有
    public static  int smallestUnFormedSum(int[] arr) {
        if (arr == null || arr.length == 0) return 1;
        Set<Integer> formedSet = new HashSet<>();
        process(arr,0,0,formedSet);
        int min = arr[0];
        int max = arr[0];
        for (int i =  1; i < arr.length; i++) {
            min = Math.min(min,arr[i]);
            max += arr[i];
        }
        for (int i = min; i <= max; i++) {
            if(!formedSet.contains(i)){
                return i;
            }
        }
        return max+1;
    }
    //curSum之前看过的位置组出来的和
    private static void process(int[] arr, int cur, int curSum, Set<Integer> formedSet) {
        if(cur==arr.length){
            formedSet.add(curSum);
            return;
        }
        formedSet.add(arr[cur]);
        process(arr,cur+1,curSum,formedSet);
        process(arr,cur+1,curSum+arr[cur],formedSet);
    }
    //改上面为动态规划
    public static int dp(int[] arr){
        if (arr == null || arr.length == 0) return 1;
        int cols = arr.length;
        int min = arr[0];
        int max = arr[0];
        for (int i = 1; i < cols; i++) {
            min = Math.min(min,arr[i]);
            max += arr[i];
        }
        int rows = max - min + 1;
        boolean[][] dp = new boolean[rows][cols];
        //dp[i][j]意思:当前看过数组的0~j，是否能够得到组成和i
        //base case:
        //第一行：等于min那个格子以及之后的格子为true
        //第一列：arr[0]等于i那个格子为true
        //注意，行i，需要加上min偏移量，row=0的时候表示的是min
        for (int col = 0; col < cols; col++) {
            dp[0][col]= arr[col] == min || (col - 1 >= 0 && dp[0][col - 1]);
        }
        //找到arr[0]那个值对应的格子
        dp[arr[0]-min][0] = true;
        //普通位置
        //dp[i][j]想要为true，无外乎就是这个位置到底是用还是不用
        //三种情况：1.不用当前位置就能够组合出i,dp[i][j-1]
        //        2.用当前位置能够组合出i,之前i-arr[j]位置为true，dp[i-arr[j]][j-1]
        //        3.当前位置就等于i+min
        //动态规划问题一定要处理好所有的可能性，因为这种打表很难调试出问题的
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                        dp[row][col] = arr[col]-min==row||
                        dp[row][col-1]||(row-arr[col]>=0&&
                        dp[row-arr[col]][col-1]);
            }
        }
        for (int row = 0; row < rows; row++) {
            if(!dp[row][cols-1]) return row+min;
        }
        return rows+min;
    }
    //如果确定有1的话能够做什么样的加速呢？
    //比如现在有一个数组{1,4,3,2}
    //我先将它排序{1,2,3,4}，然后现在看到1，我可以肯定1~1都是可以组成的，所以range=1，想要range+1扩出去
    //然后看到了2，range=2*range+1,现在能够1~3都扩到了，最经济的是现在需要4，但是现在来的是3，没关系
    //range+=3,可以1~6都扩到，现在最经济的是来个7，但是来的是4，所以1~10都可以扩到，那么最小的就是11
    public static int smallestUnFormedSum2(int[] arr){
        if (arr == null || arr.length == 0) return 1;
        Arrays.sort(arr);
        //arr[0]必定是1
        int range = 1;
        for (int i = 1; i < arr.length; i++) {
            if(arr[i]>range+1)
                return range+1;
            range+=arr[i];
        }
        return range+1;
    }
}
