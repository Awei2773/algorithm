package com.waigo.nowcoder.MyCode.Code03_alibaba;

import java.util.HashMap;
import java.util.Scanner;

/**
 * author waigo
 * create 2021-08-16 18:48
 */
public class Main1 {
    //空格切分两端，左边是类型，右边是变量
    static HashMap<String,Integer> pScore = new HashMap<>();
    static{
        pScore.put("long",8);
        pScore.put("int",4);
        pScore.put("char",1);
    }
    public static int getCap(String str){
        String[] s = str.split(" ");
        String type = s[0];
        String[] p  = s[1].split(",");
        int base = 0;
        for(String a:p){
            //a是一个变量声明
            int i = a.indexOf("[");
            if(i!=-1){
                //高阶数组
                int cur = 1;
                int num = 0;
                while(i<a.length()){
                    char c = a.charAt(i);
                    if(c =='['){
                        num = 0;
                    }else if(c == ']'){
                        cur *= num;
                    }else{
                        num = num*10+(c-'0');
                    }
                    i++;
                }
                base+=cur;
            }else{
                //一个变量
                base += 1;
            }
        }
        return base * pScore.get(type);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        System.out.println(getCap(s));
    }
}
