package com.waigo.nowcoder.MyCode.Code02_meituan;

import java.util.Scanner;

/**
 * author waigo
 * create 2021-08-15 10:18
 */
public class Main2 {
    //只能在尾巴加新字符,字符串必定不为空
    public static int toPalindrom(char[] strChars){
        int t1 = 0;
        int t2 = strChars.length-1;
        int need = 0;
        while(t1<t2){
           if(strChars[t1]==strChars[t2]){
               t1++;
               t2--;
           }else{
               //不等就得补，只能补后面，所以t1动，t2不动
               t1++;
               need++;
           }
        }
        return need;
    }

    public static void main(String[] args) {
//        System.out.println(toPalindrom("aba".toCharArray()));
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()){
            System.out.println(toPalindrom(sc.nextLine().toCharArray()));
        }
    }
}
