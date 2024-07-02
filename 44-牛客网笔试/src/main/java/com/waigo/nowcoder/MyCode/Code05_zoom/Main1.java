package com.waigo.nowcoder.MyCode.Code05_zoom;

/**
 * author waigo
 * create 2021-08-28 17:31
 */
public class Main1 {
    public int find132Pattern (int[] nums) {
        //暴力枚举所有的三个整数的序列，由于是序列，所以i,j,k大小满足，只需要保证后一个条件
        return collect(nums,0,new int[3],0);
    }
    public int collect(int[] nums,int idx,int[] pre,int w){
        if(w==3){
            //0<2<1
            return pre[0]<pre[2]&&pre[0]<pre[1]&&pre[2]<pre[1]?1:0;
        }
        if(idx==nums.length){
            return 0;
        }
        //当前位置收集或者不收集
        pre[w] = nums[idx];
        int n = 0;
        n += collect(nums,idx+1,pre,w+1);
        n += collect(nums,idx+1,pre,w);
        return n;
    }

    public static void main(String[] args) {
        System.out.println(new Main1().find132Pattern(new int[]{-1,3,2,0}));
    }
}
