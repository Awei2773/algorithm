package com.waigo.nowcoder.MyCode.Code06_360;

import java.util.Scanner;

/**
 * author waigo
 * create 2021-08-29 15:21
 */
public class Main2 {
    /**
     * 时间限制： 3000MS
     * 内存限制： 1048576KB
     * 题目描述：
     *        长城上有连成一排的n个烽火台，每个烽火台都有士兵驻守。
     *        第i个烽火台驻守着ai个士兵，相邻峰火台的距离为1。另外，有m位将军，
     *        每位将军可以驻守一个峰火台，每个烽火台可以有多个将军驻守，
     *        将军可以影响所有距离他驻守的峰火台小于等于x的烽火台。每个烽火台的基础战斗力为士兵数，
     *        另外，每个能影响此烽火台的将军都能使这个烽火台的战斗力提升k。长城的战斗力为所有烽火台的战斗力的最小值。
     *        请问长城的最大战斗力可以是多少？
     * 输入描述
     * 第一行四个正整数n,m,x,k(1<=x<=n<=10^5,0<=m<=10^5,1<=k<=10^5)
     * 第二行n个整数ai(0<=ai<=10^5)
     * 输出描述
     * 仅一行，一个整数，表示长城的最大战斗力
     *
     *
     * 样例输入
     * 5 2 1 2
     * 4 4 2 4 4
     * 样例输出
     * 6
     *
     * 提示
     * 有5个烽火台，2名将军，将军的影响范围为1，提升战斗力的值为2。令将军驻守在第2和第4个烽火台，
     * 这样所有烽火台的战斗力都是6。
      */
    /**
     * 相邻峰火台的距离为1
     * 每个烽火台的基础战斗力为士兵数
     * 长城的战斗力为所有烽火台的战斗力的最小值：就是木桶原理中最短那个
     * @param n n个烽火台
     * @param m 有m位将军
     * @param x 将军可以影响所有距离他驻守的峰火台小于等于x的烽火台
     * @param k 每个能影响此烽火台的将军都能使这个烽火台的战斗力提升k
     * @param soldiers 第i个烽火台驻守着ai个士兵
     * @return 如何排将军能使得获得最大战斗力
     */
    //为了让将军罩住更多，所以从第0个开始,每x个距离放一个将军
    //每次都将将军放在最弱的烽火台附近，增强它
    //需要知道每个状态下整个区域中最小的烽火台在哪个位置
    public static int getMaxPower(int n,int m,int x,int k,int[] soldiers){
        if(m==0){
            int min = soldiers[0];
            for(int i = 1;i<n;i++){
                min = Math.min(min,soldiers[i]);
            }
            return min;
        }
        //尝试在每个位置放将军
        int max = 0;
        for(int i = 0;i<n;i++){
            int start = Math.max(i-x,0);
            int end = Math.min(i+x,n-1);
            takeIn(soldiers,start,end,k);
            max = Math.max(max,getMaxPower(n,m-1,x,k,soldiers));
            removeTrace(soldiers,start,end,k);
        }
        return max;
    }

    private static void removeTrace(int[] soldiers, int start, int end, int k) {
        for(int i = start;i<=end;i++){
            soldiers[i]-=k;
        }
    }

    private static void takeIn(int[] soldiers, int start, int end,int k) {
        for(int i = start;i<=end;i++){
            soldiers[i]+=k;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] firLine = sc.nextLine().split(" ");
        int n = Integer.valueOf(firLine[0]);
        int m = Integer.valueOf(firLine[1]);
        int x = Integer.valueOf(firLine[2]);
        int k = Integer.valueOf(firLine[3]);
        int[] soldiers = new int[n];
        String[] nextLine = sc.nextLine().split(" ");
        for(int i = 0;i<n;i++){
            soldiers[i] = Integer.valueOf(nextLine[i]);
        }
        System.out.println(getMaxPower(n,m,x,k,soldiers));
    }
}
/**
 * 思路：二分法，从当前最小的士兵数到，k*m+最小士兵中间二分
 */