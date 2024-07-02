package com.waigo.nowcoder.MyCode.Code13_tuya;

import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author waigo
 * create 2021-09-14 20:10
 */
public class Main1 {
    //Manacher算法
    //思路：
    //1.到了i位置，假设i<R,则有利可图
    //初始长度为Math.min(pArr[i1],R-i);,然后暴力扩
    public String longestPalindrome (String str) {
        if(str==null||str.length()<2) return str;
        char[] resolveChars = resolve(str);//#d#a#b#a#d#,最左边一个位置为i - pArr[i] + 1
        int N = resolveChars.length;
        int[] pArr = new int[N];//每个位置为回文中心的回文半径，回文长度为pArr[i]-1
        int C = -1;
        int R = 0;//  R),右边不包括的
        int maxIdx = -1,maxLen = 0;
        for(int i = 0;i<N;i++){
            //i - C + 1 = C - i1 + 1
            //i1 = 2C - i;
            int i1 = (C<<1)-i;
            int minLen = i >= R?1:Math.min(pArr[i1],R-i);
            int left,right;
            while((left=i - minLen)>=0&&(right=i+minLen)<N&&resolveChars[left]==resolveChars[right]){
                minLen++;//暴力括
            }
            if(i+minLen>R){
                C = i;
                R = i + minLen;
            }
            pArr[i] = minLen;
            if(minLen>maxLen){
                maxIdx = i;
                maxLen = minLen;
            }
        }
        //从maxIdx - pArr[maxIdx] + 1 开始抓
        int resLen = maxLen - 1;
        char[] resChars = new char[resLen];
        int w = 0;
        for(int i = maxIdx - pArr[maxIdx] + 1;w<resLen;i++){
            if(resolveChars[i]!='#'){
                resChars[w++] = resolveChars[i];
            }
        }
        return String.valueOf(resChars);
    }
    //"dabad" -> "#d#a#b#a#d#"
    private char[] resolve(String str) {
        int N = (str.length()<<1)|1;
        char[] res = new char[N];
        int w = 0;
        for(int i = 0;i<N;i++){
            //偶数，0,2,4...
            res[i] = (i&1)==0?'#':str.charAt(w++);
        }
        return res;
    }

    public static void main(String[] args) {
        String dabad = new Main1().longestPalindrome("abbd");
        System.out.println(dabad);

    }
}
