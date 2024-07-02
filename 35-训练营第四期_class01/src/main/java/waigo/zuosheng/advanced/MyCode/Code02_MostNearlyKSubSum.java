package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-02 9:17
 */

import java.util.TreeSet;

/**
 * 给定一个数组arr，再给定一个k值
 * 返回累加和小于等于k，但是离k最近的子数组累加和
 * <p>
 * 思路：枚举以每个位置结尾的情况下离K最近的累加和，其中最大的就是答案
 * 如何计算每个位置的答案？每个位置可以计算前缀和prefix，然后如果能够知道前面有没有>=prefix-K的前缀和就好了，
 * 假设前面j位置的前缀和>=prefix-K,那么j到当前i位置的前缀和就是<=K满足要求。
 * 所以求“但是离k最近的子数组累加和”就是等于求">=prefix(i)-K的最小的值"，nice,有序表就有这个API floor
 */
public class Code02_MostNearlyKSubSum {
    public static int getMostNearly(int[] arr, int K) {
        if (arr == null || arr.length == 0) return 0;
        int prefix = 0;
        TreeSet<Integer> sortedSet = new TreeSet<>();
        sortedSet.add(0);//太关键了，没这个下面代码多很多判断，一开始来了是符合条件的，但是找不到ceil
        //那肯定是找到0，不符合条件就不管了，所以判断就只用看jPrefix是否为null了
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            prefix += arr[i];
            Integer jPrefix = sortedSet.ceiling(prefix - K);//>=prefix - K最小的值
            ans = jPrefix == null?ans:Math.max(ans, prefix-jPrefix);
            sortedSet.add(prefix);
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] a = {2, -2, -1};
        int k = 0;
        System.out.println(getMostNearly(a,k));
    }
}
