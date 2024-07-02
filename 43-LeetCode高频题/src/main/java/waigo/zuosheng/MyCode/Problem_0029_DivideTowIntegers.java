package waigo.zuosheng.MyCode;


/**
 * author waigo
 * create 2021-07-15 9:58
 */
@SuppressWarnings("Duplicates")
public class Problem_0029_DivideTowIntegers {
    //加法,b表示进位信息,a表示无进位信息
    public static int add(int a,int b){
        int sum = 0;
        while(b!=0){
            sum = a^b;
            b = (a&b)<<1;
            a = sum;
        }
        return a;
    }
    //减法，a-b = a+(-b)
    public static int sub(int a,int b){
        return add(a,opposite(b));
    }

    private static int opposite(int b) {
        return add(~b,1);
    }
    //乘法：a*b 比如a = 10101,b = 11010
    //a*b =             10101
    //                 *11010
    //          --------------
    //                  00000
    //                 10101
    //                00000
    //               10101
    //      +       10101
    //  就是a*b = a*2^(4)+a*2^(3)+a*2^(1)
    public static int mul(int a,int b){
        boolean isPos = isPositive(a,b);
        a = a<0?opposite(a):a;
        b = b<0?opposite(b):b;
        int step = 0;
        int res = 0;
        while(b!=0){
            if((b&1)!=0){
                res = add(res,a<<step);
            }
            b>>>=1;
            step++;
        }
        return isPos?res:opposite(res);
    }
    //除法    a/b,整数除法，如果a<b那么直接是0，必须提前进行筛选
    //注意：注意：//有边界问题，不能这样做
    public static int divideFaliure(int a,int b){
        boolean isPos = isPositive(a,b);
        a = a<0?opposite(a):a;
        b = b<0?opposite(b):b;
        int step = 0;
        int res = 0;
        //除法和乘法类似，a肯定是等于b*res,也就是a = b*2^(甲)+b*2^(乙)+c*2^(丙)
        //所以做法就是让b去够a，不断左移直到小于等于a,然后a = a-此时的b,这时就能得到一个甲，相当于是得到了结果的一部分
        while(a!=0){
            if(b>a){
                //超了，往右边走走
                b>>>=1;
                if(--step<0) break;//此时说明已经除不尽了，a<b
            }else if((b<<1)>a){
                //到位了，减一下
                a = sub(a,b);
                res|=(1<<step);//抓一个除数
            }else{
                //还没到，继续往左边走走
                b<<=1;
                step++;
            }
        }
        return isPos?res:opposite(res);
    }

    //要这样
    public static int divide(int a,int b){
        boolean isPositive = isPositive(a,b);
        a = a<0?-a:a;
        b = b<0?-b:b;
        int res = 0;
        for(int i = 31;i>=0&&a!=0;i--){
            if((a>>>i)>=b){
                res|=1<<i;
                a = a - (b<<i);
            }
        }
        return res;
    }
    //符号组合出来是什么，tru是正数
    private static boolean isPositive(int a, int b) {
        return ((a^b)>>>31)==0;
    }

    public static void main(String[] args) {
        /*int testTimes = 1000000;
        for(int i = 0;i<testTimes;i++){
            int a = (int) (Math.random()*100)-(int) (Math.random()*500);
            int b = (int) (Math.random()*100)-(int) (Math.random()*600);
            if(b==0) continue;
            if(a/b!=divide(a,b)){
                System.out.println("FUNKing....");
                break;
            }
        }*/
//        System.out.println(isPositive(-3,-4));
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE);
//        System.out.println(isPositive(-12354235,-35));

        System.out.println(divide(255,2));
    }
}
