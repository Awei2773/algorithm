package waigo.zuoshen.basic.MyCode;


/**
 * author waigo
 * create 2021-02-27 9:24
 */
public class Code01_Hanoi {
    //打印N层汉诺塔问题的步骤
    public static void printNHanoiSetps1(int N){
        if(N<=0) return;
        leftToRight(N);
    }

    private static void leftToRight(int n) {
        //n==1，直接移动
        if(n==1){
            printSetps("左","右");
            return;
        }
        //将n-1层移动到中间，将n层移动到右边
        leftToMid(n-1);
        leftToRight(1);
        //将n-1层从中间移动到右边
        midToRight(n-1);
    }

    private static void midToRight(int n) {
        if(n==1){
            printSetps("中","右");
            return;
        }
        //将n-1层移动到左边，n层移动到右边
        midToLeft(n-1);
        midToRight(1);
        //n-1从左边到右边
        leftToRight(n-1);
    }

    private static void midToLeft(int n) {
        if(n==1){
            printSetps("中","左");
            return;
        }
        //n-1到右边，n到左边
        midToRight(n-1);
        midToLeft(1);
        //n-1到左边
        rightToLeft(n-1);
    }

    private static void leftToMid(int n) {
        if(n==1){
            printSetps("左","中");
            return;
        }
        //n-1去右边，n到中
        leftToRight(n-1);
        leftToMid(1);
        //n-1从右边到中间
        rightToMid(n-1);
    }

    private static void rightToMid(int n) {
        if(n==1){
            printSetps("右","中");
            return;
        }
        //n-1从右到左,n从右到中
        rightToLeft(n-1);
        rightToMid(1);
        //n-1从左到中
        leftToMid(n-1);
    }
    private static void rightToLeft(int n) {
        if(n==1){
            printSetps("右","左");
            return;
        }
        //n-1到中，n到左
        rightToMid(n-1);
        rightToLeft(1);
        //n-1中到左
        midToLeft(n-1);
    }

    private static void printSetps(String from,String to){
        System.out.println("let 1 from "+from+" to "+to);
    }
    public static void printNHanoiSetps2(int N){
        if(N<1) return;
        process(N,"左","右","中");
    }

    private static void process(int n, String from , String to, String other) {
        if(n==1){
            printSetps(from,to);
            return;
        }
        //n-1从from到other,1到to
        process(n-1,from,other,to);
        process(1,from,to,other);
        //n-1从other到to
        process(n-1,other,to,from);
    }

    public static void main(String[] args) {
        printNHanoiSetps1(5);
    }
}

