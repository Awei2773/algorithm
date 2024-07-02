package com.waigo.newcoder;

/**
 * author waigo
 * create 2021-11-09 17:14
 */
public class NC10_BigNumsMulti {
    public static String solve (String s, String t) {
        if(s==null||t==null||s.length()==0||t.length()==0||s.charAt(0)=='0'||t.charAt(0)==0)
            return "0";
        //将s*t每一位乘出来的中间值存入一个char数组中,数组长度为t.len+1
        int sLen = s.length();
        int tLen = t.length();
        //中间值的长度不会超过sLen+1,中间值的个数是tLen
        char[][] temps = new char[tLen][sLen+1];
        //tlen - 1对应0位置
        for(int i = tLen - 1;i>=0;i--){
            int in = 0;
            //i指定t的一位数字
            int curTNum = t.charAt(i) - '0';
            int temp;
            //sLen-1对应slen位置
            for(int j = sLen -1;j>=0;j--){
                temp = (curTNum * (s.charAt(j) - '0')) + in;
                temps[tLen-i-1][j+1] = (char)((temp % 10)+'0');
                in = temp/10;
            }
            temps[tLen-i-1][0] = (char)(in+'0');
        }
        //将中间结果加起来，temps[i]就需要乘以10^i
        char[] res = temps[0];
        int digit = 1;
        for(int i = 1;i<tLen;i++){
            //temps[i]*digit+res
            res = charArrAdd(res,temps[i],digit);
            digit++;
        }
        int start = 0;
        while(start<res.length-1&&res[start]=='0')
            start++;
        return String.valueOf(res,start,res.length-start);
    }
    //return b*digit+a
    public static char[] charArrAdd(char[] a,char[] b,int digit){
        //加法的答案不会超过长的那个+1
        int aLen = a.length;
        int bLen = b.length;
        int longLen = aLen>(bLen+digit)?aLen:bLen+digit;
        char[] res = new char[longLen+1];
        int aIdx = aLen-1;
        int bIdx = bLen-1;
        int w = longLen;
        int in = 0;
        int bitSum;
        while(aIdx>=0&&bIdx>=0){
            if(digit >0){//
                res[w--] = a[aIdx--];
                digit--;
            }else{
                bitSum = (a[aIdx--] - '0')+(b[bIdx--]-'0')+in;
                in = bitSum / 10;
                res[w--] = (char)((bitSum % 10)+'0');
            }
        }
        while(aIdx>=0){
            bitSum = (a[aIdx--] - '0') + in;
            in = bitSum / 10;
            res[w--] = (char)((bitSum % 10)+'0');
        }
        while(bIdx>=0){
            bitSum = (b[bIdx--]-'0')+in;
            in = bitSum / 10;
            res[w--] = (char)((bitSum % 10)+'0');
        }
        while(w>=0){
            res[w--] = (char)(in + '0');
            in = 0;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(solve("10088","9923432"));
    }
}
