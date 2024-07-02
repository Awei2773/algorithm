package waigo.zuoshen.advanced.MyCode;

import java.util.*;

/**
 * author waigo
 * create 2021-10-23 11:34
 */
public class Code04_LIS {
    //dp[i]:长度达到i的最小值，有效右边界right,[1~right]是有效的
    //[1...right]必定是递增的
    //遍历num去[1...right]中二分找到大于等于num的最左位置
    public static int lengthOfLIS(int[] nums) {
        if(nums==null||nums.length==0) return 0;
        int N = nums.length;
        int[] ends = new int[N+1];
        int right = 1;//1...right有效
        ends[1] = nums[0];
        /** 如果需要输出具体子序列，则添加一个lens数组，
         * lens[i]表示必须以i结尾的最长子序列长度*/
        int[] lens = new int[N];
        /** 长度数组lens2也可以这样定义，lens2[i]表示0~i为止最长的子序列长度,注意这是错误的
         * 比如长度为2,              1,10  1,6都组成长度为2的，此时是把握不了应该选10还是选6的，还是得判断，还不如上面那种呢
         * */
        lens[0] = 1;
        for(int i = 1;i<N;i++){
            int l = 1,r = right;
            //1.找到l...r中小于num的最右位置
            int target = 0;
            while(l<=r){
                int mid = ((r-l)>>>1)+l;
                if(ends[mid]<nums[i]){
                    target = mid;
                    l = mid+1;
                }else{
                    r = mid-1;
                }
            }
            //2.调整right
            right = Math.max(target+1,right);
            //3.三个数组处理好
            //3.1ends数组
            ends[target+1] = nums[i];
            //3.2lens数组，当前以i结尾的最长递增子序列的长度为target+1
            lens[i] = target+1;
        }

        /** 输出最长递增子序列，如果有多个，输出字典序最大那个*/
        int[] lis = new int[right];
        //1.遍历一遍lens找到最长那个子序列结尾的下标,找到最后一个
        int end = 0;
        for(int i = 0;i<lens.length;i++){
            if(lens[i]==right){
                end = i;
            }
        }
        //2.求最大或者最小的字典序需要从前往后遍历，前面的都小，那肯定就是最小，前面都大，那肯定是最大
        int w = right-1;//w就是当前要写入lis的下标，也就是长度为w+1的结尾
        for(int i = end;i>=0;i--){
            if(lens[i]==w+1&&(w+1>=right||nums[i]<lis[w+1])){
                lis[w--] = nums[i];
            }
        }
        System.out.println(Arrays.toString(lis));
        return right;
    }

    public static void main(String[] args) {
        int[] nums = {2, 1, 5, 3, 6, 4, 8,3, 9, 7};
        System.out.println(lengthOfLIS(nums));
    }
}
