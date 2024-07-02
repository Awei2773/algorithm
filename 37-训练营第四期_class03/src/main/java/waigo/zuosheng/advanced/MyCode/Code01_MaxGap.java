package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-08 12:06
 */

import java.util.Arrays;

/**
 * 164. 最大间距
 *
 * 给定一个无序的数组，找出数组在排序之后，相邻元素之间最大的差值。
 *
 * 如果数组元素个数小于 2，则返回 0。
 *
 * 示例 1:
 *
 * 输入: [3,6,9,1]
 * 输出: 3
 * 解释: 排序后的数组是 [1,3,6,9], 其中相邻元素 (3,6) 和 (6,9) 之间都存在最大差值 3。
 *
 * 示例 2:
 *
 * 输入: [10]
 * 输出: 0
 * 解释: 数组元素个数小于 2，因此返回 0。
 *
 * 说明:
 *
 *     你可以假设数组中所有元素都是非负整数，且数值在 32 位有符号整数范围内。
 *     请尝试在线性时间复杂度和空间复杂度的条件下解决此问题。
 */
public class Code01_MaxGap {
    //1.模型构造，将min~max这个范围等分为nums.len+1个桶，这样nums里的数进桶之后必然会有一个桶为空
    //2.答案可能出现的情况本来有两种，一个是桶内两数的差，另一个是相邻两个桶中后桶的max-前桶的min
    //利用这个模型可以构造出一个平凡解，那就是保底有一个隔着空桶的答案，这就排除掉了桶内可能出答案的情况,
    //（保有一个空桶也，而且这个空桶不可能是开头和结尾，而是中间的空桶，这样至少有一个大于桶长度的答案）
    //就省去了遍历桶内元素的复杂度，但是这个平凡解还不能确定是最终解，最终解就是平凡解和相邻桶PK的结果
    //这种可能性排除法太秀了，利用流程将可能性进行替换，实现复杂度的降低
    public static int maximumGap(int[] nums) {
        if(nums==null||nums.length==0) return 0;
        int min = nums[0];
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            min = Math.min(min,nums[i]);
            max = Math.max(max,nums[i]);
        }
        if(min==max) return 0;
        //造桶
        int N = nums.length+1;
        boolean[] pailIsEmpty = new boolean[N];
        int[] pailMin = new int[N];
        int[] pailMax = new int[N];

        int areaNum = max-min;
        for (int i = 0; i < nums.length; i++) {
            int pN = getSpePair(nums[i]-min,areaNum,N-1);
            pailMin[pN] = pailIsEmpty[pN]?Math.min(pailMin[pN],nums[i]):nums[i];
            pailMax[pN] = pailIsEmpty[pN]?Math.max(pailMax[pN],nums[i]):nums[i];
            pailIsEmpty[pN] = true;
        }
        //遍历桶
        int res = 0;
        for (int left = 0; left < N-1; left++) {
            int right = left+1;
            while (!pailIsEmpty[right]){
                right++;
            }
            //这两个桶搞个答案出来
            res = Math.max(pailMin[right]-pailMax[left],res);
            left = right-1;
        }
        return res;
    }

    private static int getSpePair(long num, long areaNum, long n) {
        return (int) (num*n/areaNum);
    }
    public static int maximumGap2(int[] nums) {
        if(nums==null||nums.length<2) return 0;
        int N = nums.length;
        int[] bucketsMin = new int[N+1];
        int[] bucketsMax = new int[N+1];
        boolean[] isBucketUnEmpty = new boolean[N+1];//false为空
        //桶初始化,按照值域划分
        int min = Arrays.stream(nums).min().getAsInt();
        int max = Arrays.stream(nums).max().getAsInt();
        //N个数均匀进N+1个桶，总有一个会空下来
        int area = Math.max((max - min)/N,1);
        for(int i = 0;i<N;i++){
            int pN = nums[i]==max?N:Math.min(N,(nums[i]-min)/area);
            bucketsMin[pN] = isBucketUnEmpty[pN]?Math.min(bucketsMin[pN],nums[i]):nums[i];
            bucketsMax[pN] = isBucketUnEmpty[pN]?Math.max(bucketsMax[pN],nums[i]):nums[i];
            isBucketUnEmpty[pN] = true;
        }
        //遍历桶抓答案
        int res = 0;
        int left = 0,right;
        while(left<N){//最后一个桶在N下标
            //找到右边一个有数的桶
            right = left+1;
            while(right<N&&!isBucketUnEmpty[right]){
                right++;
            }
            res = Math.max(res,bucketsMin[right]-bucketsMax[left]);
            left = right;
        }
        return res;
    }

    public static void main(String[] args) {
        int[] a = {1,1,1,1,1,5,5,5,5,5};
        System.out.println(maximumGap2(a));
    }
}
