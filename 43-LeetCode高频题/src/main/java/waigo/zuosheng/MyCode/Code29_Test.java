package waigo.zuosheng.MyCode;

import java.util.LinkedList;
import java.util.SortedSet;
import java.util.Stack;

/**
 * author waigo
 * create 2021-07-15 13:10
 */
public class Code29_Test {
    public static int divide(int dividend, int divisor) {
        if(dividend==0) return 0;
        if(divisor==Integer.MIN_VALUE) return dividend==Integer.MIN_VALUE?1:0;
        if(dividend == Integer.MIN_VALUE){
            if(divisor==-1){
                return Integer.MAX_VALUE;
            }
            int first = div(dividend+1,divisor);
            int lost = dividend - mul(first,divisor);
            return first+div(lost,divisor);
        }
        return div(dividend,divisor);
    }
    public static boolean isPos(int a,int b){
        return ((a^b)>>>31)==0;
    }
    //a*b
    public static int mul(int a,int b){
        boolean isPositive = isPos(a,b);
        a = a<0?-a:a;
        b = b<0?-b:b;
        int res = 0;
        int step = 0;
        while(b!=0){
            if((b&1)!=0){
                res+=(a<<step);
            }
            step++;
            b>>>=1;
        }
        return isPositive?res:-res;
    }
    public static int div(int a,int b){
        boolean isPositive = isPos(a,b);
        a = a<0?-a:a;
        b = b<0?-b:b;
        int res = 0;
        for(int i = 31;i>=0;i--){
            if((a>>>i)>=b){
                res|=1<<i;
                a = a - (b<<i);
            }
        }
        return isPositive?res:-res;
    }

    public static void main(String[] args) {
//        System.out.println(divide(-2147483648,-35));
        /*int testTimes = 1000000;
        for(int i = 0;i<testTimes;i++){
            int a = (int) (Math.random()*100)-(int) (Math.random()*500);
            int b = (int) (Math.random()*100)-(int) (Math.random()*600);
            if(b==0) continue;
            if(a/b!=div(a,b)){
                System.out.println("FUNKing....");
                System.out.println("a="+a+",b="+b);
                System.out.println(a/b);
                System.out.println(div(a,b));
                break;
            }
        }*/

    }
}
