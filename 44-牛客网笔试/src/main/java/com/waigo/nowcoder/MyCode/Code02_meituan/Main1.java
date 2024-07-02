package com.waigo.nowcoder.MyCode.Code02_meituan;

import java.util.Arrays;
import java.util.Scanner;

/**
 * author waigo
 * create 2021-08-15 9:55
 */
public class Main1 {
    //是否能够有序后符合1,2,3,4这样排列
    public static boolean isYes(int[] num){
        Arrays.sort(num);
        int pre = num[0];
        for(int i = 1;i<num.length;i++){
            if(num[i]!=pre+1) return false;
            pre = num[i];
        }
        return true;
    }

    public static void main(String[] args) {
//        System.out.println(isYes(new int[]{4,4,1,3}));
        Scanner sc = new Scanner(System.in);
        int group = Integer.valueOf(sc.nextLine());
        for(int i = 0;i<group;i++){
            int n = Integer.valueOf(sc.nextLine());
            String[] nums = sc.nextLine().split(" ");
            if(n<0){
                System.out.println("No");
                continue;
            }else if(n==0){
                System.out.println("Yes");
                continue;
            }
            int[] num = new int[n];
            for(int j = 0;j<n;j++){
                num[j] = Integer.valueOf(nums[j]);
            }
            if(isYes(num)){
                System.out.println("Yes");
            }else{
                System.out.println("No");
            }
        }
    }
}
