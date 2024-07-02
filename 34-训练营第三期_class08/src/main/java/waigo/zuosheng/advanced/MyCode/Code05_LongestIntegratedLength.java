package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-01 8:34
 */

import java.util.HashSet;
import java.util.Set;

/**
 * 先给出可整合数组的定义:如果一个数组在排序之后，每相邻两个数差的绝对值 都为 1，
 * 则该数组为可整合数组。例如，[5,3,4,6,2]排序之后为[2,3,4,5,6]， 符合每相邻两个数差的绝对值
 * 都为 1，所以这个数组为可整合数组。 给定一个整型数组 arr，请返回其中最大可整合子数组的长度。
 * 例如， [5,5,3,2,6,4,3]的最大 可整合子数组为[5,3,2,6,4]，所以返回 5。
 */
public class Code05_LongestIntegratedLength {
    //这题无法找到单调性，可能一个短的数组无法整合，多一点就可以了，关键点在于简化整合的定义好走过程
    public static int getLGIntegratedLength(int[] arr){
        if(arr==null||arr.length==0) return 0;
        int[] prefixSum = new int[arr.length];
        prefixSum[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            prefixSum[i]+=prefixSum[i-1]+arr[i];
        }
        //将可整合数组的定义解读成无重复值，最大值-最小值加一等于数组长度
        //问题就变成了从某个位置开始在维护这个新定义不变的情况下能够囊括多长的子数组

        //枚举从数组的每个位置开始的子数组，符合条件的，从中取最大的那个必然是答案
        Set<Integer> set = new HashSet<>();//记录出现过的数，防止重复值
        int min=Integer.MAX_VALUE;
        int max=Integer.MIN_VALUE;//子数组中的最小值和最大值
        int maxLen = 0;
        for (int L = 0; L < arr.length; L++) {
            //
            set.clear();
            //从L往外去扩
            for (int R = L; R < arr.length; R++) {
                if(set.contains(arr[R])) break;
                min = Math.min(min,arr[R]);
                max = Math.max(max,arr[R]);
                //无重复情况下看看是否可以整合
                if(max-min==R-L){
                    maxLen=Math.max(maxLen,R-L+1);
                }
                set.add(arr[R]);
            }
        }
        return maxLen;
    }
}
