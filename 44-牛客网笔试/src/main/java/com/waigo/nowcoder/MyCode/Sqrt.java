package com.waigo.nowcoder.MyCode;

import java.text.DecimalFormat;

/**
 * author waigo
 * create 2021-08-15 8:50
 */
public class Sqrt {
    //1.袖珍计算机
    public static int sqrt2(int x){
        //根号x = x的二分之一次方
        if(x<0) return 0;
        int a = (int) Math.pow(x,0.5);
        return (a+1)*(a+1)>=x?a:a+1;
    }
    //方法三、牛顿迭代法
    public static double sqrt3(double x) {
        if(x<0) {
            return -1;
        }
        //格式化，保证输出位数
        DecimalFormat df = new DecimalFormat("#.00");

        double k = x;
        double precision = 0.000001;
        while((k*k-x)>precision) {
            k=0.5*(k+x/k);
        }
        return Double.valueOf(df.format(k));
    }
    public static int sqrt1(int x){
        if(x<=0) return 0;
        double C=x,x0 = x;
        while(true){
            double x1 = (x0+(C/x0))*0.5;
            if(Math.abs(x0-x1)<1e-7){
                break;
            }
            x0 = x1;
        }
        return (int) x0;
    }
    public static void main(String[] args) {
        int a = 9;
        System.out.println(sqrt1(a));
    }
}
