package com.waigo.nowcoder.MyCode.Code10_tx;

import java.util.Arrays;
import java.util.Scanner;

/**
 * author waigo
 * create 2021-09-05 20:23
 */
public class Main2 {
    /**
     *
     * @param n 精灵数
     * @param A A的精灵攻击力
     * @param B B的精灵攻击力
     * @return 小A最优出战顺序下最多赢多少回合
     * 已知小B出战顺序为1，2，。。n
     *
     * 策略，采用贪心法，每次都使用刚好能cover住的那只精灵,>=bPower+1的最左那只
     */
    public static int game(int n,int[] A,int[] B){
        if(n==0) return 0;
        //1.先将A,B的精灵攻击力都算出来
        int[] aPower = getPower(A,n);
        int[] bPower = getPower(B,n);
        Arrays.sort(aPower);
        int res = 0;
        boolean[] isJoin = new boolean[n];
        for(int bP:bPower){
            int aJoin = getMinLeftEQ(aPower,bP+1,isJoin);
            if(aJoin!=-1){
                res++;
                isJoin[aJoin] = true;
            }
        }
        return res;
    }
    //>=num的最左一个位置
    private static int getMinLeftEQ(int[] bPower, int num, boolean[] isJoin) {
        int l = 0,r = bPower.length-1;
        int target = -1;
        while(l<=r){
            int mid = ((r-l)>>1)+l;
            if(bPower[mid]>=num){
                if(!isJoin[mid]){
                    target = mid;
                }
                r = mid-1;
            }else{
                l = mid + 1;
            }
        }
        return target;
    }

    //返回res[i]:i下标的数的因子个数
    private static int[] getPower(int[] a,int n) {
        int[] res = new int[n];
        for(int i = 0;i<n;i++){
            res[i] = getNum(a[i]);
        }
        return res;
    }
    //返回n这个数的因子个数
    private static int getNum(int n) {
        int res = 0;
        double sqrt = Math.sqrt(n);
        for(int i = 1; i<= sqrt; i++){
            if(n%i==0){
                res+=(i== sqrt ?1:2);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.valueOf(sc.nextLine());
        int[] aPower = new int[n];
        int[] bPower = new int[n];
        String[] aPowers = sc.nextLine().split(" ");
        String[] bPowers = sc.nextLine().split(" ");
        for(int i = 0;i<n;i++){
            aPower[i] = Integer.valueOf(aPowers[i]);
            bPower[i] = Integer.valueOf(bPowers[i]);
        }
        System.out.println(game(n,aPower,bPower));
    }
}
