package com.waigo.nowcoder.MyCode.Code02_meituan;


import java.text.DecimalFormat;

/**
 * author waigo
 * create 2021-08-15 8:29
 */
public class Title1 {
    public static void main(String[] args){
//        System.out.println(getSum(81,4));
//        System.out.println(getSum(2,2));
//        System.out.println(Math.sqrt(9.0));
        /*DecimalFormat format = new DecimalFormat("#.00");
        for(int j = 0;j<1;j++){
            double i = Math.random()*1000;
            double a = setScale(776.4936661456593);
            System.out.println(a);
            double b = Double.valueOf(format.format(776.4936661456593));
            System.out.println(b);
            System.out.printf("%.2f",776.4936661456593);
//            if(a!=b){
//                System.out.println(i);
//            }
        }*/
        System.out.println(Math.round(2.5));
        System.out.printf("%.2f",3.145f);


    }
    public static double getSum(int n,int m){
        double res = 0;
        double num = n;
        for(int i = 0;i<m;i++){
            res += num;
            num = Math.sqrt(num);
        }
        DecimalFormat format = new DecimalFormat("#.00");
        return setScale(res);
    }
    public static double setScale(double n){
        return (double)Math.round(n*100)/100;
    }
}
/**
 * 精度设置方法
 * 1.DecimalFormat java.text.DecimalFormat
 *
 * 2.(double)(Math.round(num*100)/100)
 *
 * 3.printf(".2f",num)
 *
 * System.out.println((double)Math.round(3.145f*100)/100);
 * System.out.printf("%.2f",3.145f);这里加不加f结果有影响，加了就不四舍五入了
 *
 * TODO:://这三种都试一下，第二种好像和1,3结果会不同，在测试用例中过不去
 *
 */