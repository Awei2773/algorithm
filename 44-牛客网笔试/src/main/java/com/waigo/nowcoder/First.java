package com.waigo.nowcoder;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * author waigo
 * create 2021-08-13 21:03
 */
public class First {
    public long[] FarmerNN (int n, long m) {
        //先算最少回到最下面多少趟
        long count = 0;//1：n,2:3n-2,3:5n-4,4:7n-6   是一个等差数列，d = 2n-2 --> 第x趟是 n+(x-1)(2n-2)
        long L = 0,R = m;
        //找到n+(x-1)(2n-2) <=m 的最右边位置
        long last = 0;
        long mid;
        while(L<=R){
            mid = (R+L)>>1;
            long num = getNum(mid,n);
            if(num>m||num<=0){
                R = mid -1;
            }else{
                L = mid+1;
                count = mid;
            }
            last = num;
        }
        //如果count!=0,那么当前就在最底下，否则在最顶上
        long[] res = new long[n];
        if(count!=0){
            //当前在最底下，而且1和n都是count，中间都是（2*count-1）
            for(int i = 0;i<n;i++){
                if(i==0||i==n-1){
                    res[i] = count;
                }else{
                    res[i] = (count<<1)-1;
                }
                m -= res[i];
            }
        }
        //如果在最顶上那么向下走，如果在最底下那么向上走，只会走一次
        if(count!=0){
            for(int i = n-2;i>0&&m>0;i--){
                res[i]+=1;
                m--;
            }
        }
        for(int i = 0;m>0;i++){
            res[i]+=1;
            m--;
        }
        return res;
    }
    public long getNum(long x,int n){
        return n+(x-1)*((n-1)*2);
    }

    public static void main(String[] args) {
        First first = new First();
//        first.FarmerNN(185,200673322476847425L);
        while(true){
            int n = (int)(Math.random()*999)+2;
            long m = (long)(Math.random()*1000000000000000000F)+1;
            try{
                first.FarmerNN(n,m);
            }catch(Exception e){
                System.out.println("n:"+n);
                System.out.println("m:"+m);
            }
        }
    }
}
