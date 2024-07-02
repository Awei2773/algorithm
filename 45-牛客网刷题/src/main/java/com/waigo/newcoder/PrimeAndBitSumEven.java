package com.waigo.newcoder;

import java.util.ArrayList;
import java.util.Collections;

/**
 * author waigo
 * create 2021-08-23 23:28
 */
public class PrimeAndBitSumEven {
    //找质数 10以内2,3,5,7是质数,10以外如果是2,3,5,7的倍数则不是质数
    public static void main(String[] args) {
        PrimeAndBitSumEven primeAndBitSumEven = new PrimeAndBitSumEven();
        System.out.println(primeAndBitSumEven.process(100));
        int[] num = new int[1000];
        for(int i = 0 ;i < 1000;i++) {
            num[i] = i;
        }
        for(int i = 0 ;i < 1000;i++) {
            if(i==989){
                System.out.println();
            }
            if(primeAndBitSumEven.isPrime(num[i])&&primeAndBitSumEven.isBitSumEven(num[i])) {
                System.out.println(num[i]);
            }
        }
    }
    //返回小于num的质数中位数和为偶数的个数
    public int process(int num){
        if(num<10) return num>=2?1:0;
        int count = 1;
        for(int i = 11;i<=num;i++){
            if(isPrime(i)&&isBitSumEven(i)){
                count++;
            }
        }
        return count;
    }

    private boolean isBitSumEven(int num) {
        int bitSum = 0;
        while(num!=0){
            bitSum+=num%10;
            num/=10;
        }
        return (bitSum&1)==0;
    }

    public boolean isPrime(int num){
        int sqrt = (int) Math.sqrt(num);
        for(int i = 2;i<=sqrt;i++){
            if(num%i==0) return false;
        }
        return true;
    }
}
