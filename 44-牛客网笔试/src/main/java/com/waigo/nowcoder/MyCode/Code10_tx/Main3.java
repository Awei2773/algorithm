package com.waigo.nowcoder.MyCode.Code10_tx;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * author waigo
 * create 2021-09-05 21:01
 */
public class Main3 {
    //s只有0，1，下标从1开始,第i位价值位vali
    //
    public static int getMaxValue(int n,String s){
        StringBuilder builder = new StringBuilder();
        int[] vals = new int[n];
        return process(0,builder,vals,n,s,0,0);

    }
    //当前看i位置，之前处理过的字符串在builder中，它们的价值为vals,当前builder取了w位
    private static int process(int i, StringBuilder builder, int[] vals,int n,String s,int w,int preVal) {
        if(i==n){
            return preVal;
        }
        //当前位置要不要，两种情况
        //不要
        int res1 = process(i+1,builder,vals,n,s,w,preVal);
        builder.append(s.charAt(i));
        vals[w] = w==0?1:(builder.charAt(w)==builder.charAt(w-1)?vals[w-1]+1:1);
        int res2 = process(i+1,builder,vals,n,s,w+1,preVal+vals[w]);
        builder.deleteCharAt(builder.length()-1);//轨迹擦除
        return Math.max(res1,res2);
    }

    public static void main(String[] args) {
        new Thread(()->{
            try {
                a();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            b();
        }).start();
    }
    static Object lockA = new Object();
    static Object lockB = new Object();
    public static void a() throws InterruptedException {
        synchronized (lockA){
            TimeUnit.SECONDS.sleep(1);
            synchronized (lockB){
                System.out.println("A.....");
            }
        }
    }
    public static void b(){
        synchronized (lockB){
            synchronized (lockA){
                System.out.println("B.....");
            }
        }
    }
}
