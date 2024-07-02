package com.waigo.nowcoder.MyCode.Code14_wangyi;

import java.util.Scanner;

/**
 * author waigo
 * create 2021-09-17 19:35
 */
//易数求解
//X:21370
//第一步:每位用最少位数的二进制表示A，即10,1,11,111,0
//第二步:对A进行翻转，去掉前置的0，得到B，即111,11,1,01
public class Main1 {
    public static String getEasyNum(int num){
        StringBuilder builder = new StringBuilder();
        //从最低位开始，每次将一位的二进制字符串拿到，然后翻转后再拼接
        while(num!=0){
            builder.append(reverseStr(Integer.toBinaryString(num%10)));
            num/=10;
        }
        //去掉前置0
        int start = 0;
        while(builder.charAt(start)=='0'){
            start++;
        }
        StringBuilder res = new StringBuilder();
        int N = builder.length();
        for(int idx = start; idx< N; idx++){
            res.append(builder.charAt(idx));
            while(idx+1==N||builder.charAt(idx+1)==builder.charAt(idx)){
                if(++idx==N){
                    break;
                }
            }
        }
        return res.toString();
    }

    private static char[] reverseStr(String s) {
        int length = s.length();
        char[] res = s.toCharArray();
        int left = 0;
        int right = length -1;
        while(left<right){
            swap(res,left,right);
            left++;
            right--;
        }
        return res;
    }

    private static void swap(char[] res, int left, int right) {
        if(left!=right){
            res[left] ^= res[right];
            res[right] ^= res[left];
            res[left] ^= res[right];
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int N = Integer.valueOf(sc.nextLine());
        for(int i = 0;i<N;i++){
            System.out.println(getEasyNum(Integer.valueOf(sc.nextLine())));
        }
    }
}
