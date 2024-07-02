package com.waigo.newcoder;

/**
 * author waigo
 * create 2021-08-27 20:51
 */
public class NC100_atoi {
    public int atoi(String str) {
        //-000001
        //-000000
        //-001000,前0省略，后0不能省
        if (str == null || str.length() == 0) return 0;
        str = str.replaceAll(" ", "");
        char[] cs = str.toCharArray();
        boolean isPositive = cs[0] != '-';
        int start = isNumber(cs[0]) ? 0 : 1;
        int n = str.length();
        //找到第一个数字所在
        while (start < n && !isNumber(cs[start])) {
            start++;
        }
        int end = start + 1;
        while (end < n && isNumber(cs[end])) {
            end++;
        }
        int res = 0;
        for (int i = start; i < end; i++) {
            if(Integer.MAX_VALUE/10<res){
                return isPositive?Integer.MAX_VALUE:Integer.MIN_VALUE;
            }//严谨一点需要处理等于的情况
            res = res*10 + (cs[i] - '0');
        }
        return isPositive ? res : -res;
    }

    private boolean isNumber(char c) {
        return c - '0' >= 0 && c - '0' <= 9;
    }


    public static void main(String[] args) {
//        System.out.println(new NC100_atoi().atoi("      -10441643317j"));
    }
}
