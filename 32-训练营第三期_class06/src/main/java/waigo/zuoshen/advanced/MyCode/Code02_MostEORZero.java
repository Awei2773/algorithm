package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-20 12:06
 */

import java.util.HashMap;

/**
 * 一个数组，如何对它进行拆分能够使得子数组异或和为0的部分最多
 *
 *  dp[i]表示[0...i]这个区间中能够划分出最多的异或和为0的子数组的个数
 *
 * 在异或和为0最多的划分下，最后一块有两种可能
 * 分析i位置
 * 1.i位置所在的区间是异或和为0
 * dp[i]=1+dp[j-1]
 * 2.i位置所在区间是异或和不为0
 * dp[i]=dp[i-1]
 *
 * 碰到问题的时候先考虑一下，能否问题切小上抛，小样本量是否好解决，然后将大切小，过程中碰到问题具体优化
 */
public class Code02_MostEORZero {
    public static int getMostEOR(int[] arr){
        if(arr==null||arr.length==0) return 0;
        int prefixEOR = 0;
        int N = arr.length;
        int[] dp = new int[N];//dp[i]表示[0...i]这一段区间中最多能分多少块
        HashMap<Integer,Integer> map = new HashMap<>();
        map.put(0,-1);
        for (int i = 0; i < arr.length; i++) {
            //第一种情况
            prefixEOR^=arr[i];
            if(map.containsKey(prefixEOR)){
                int j = map.get(prefixEOR);
                dp[i]=1+(j==-1?0:dp[j]);
            }
            if(i>0){
                dp[i] = Math.max(dp[i],dp[i-1]);
            }
            map.put(prefixEOR,i);
        }
        return dp[N-1];
    }

    public static void main(String[] args) {
        System.out.println(getMostEOR(new int[]{3,2,1,1,0,4,3,2,1,0,0,3,1,2,1,0,2,1,3}));
    }
}
