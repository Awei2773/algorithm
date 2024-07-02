package waigo.zuoshen.advance.MyCode;

/**
 * author waigo
 * create 2021-04-02 11:39
 */
/*
给定一个正整数N，表示有N份青草统一堆放在仓库里
有一只牛和一只羊，牛先吃，羊后吃，它俩轮流吃草
不管是牛还是羊，每一轮能吃的草量必须是：
1，4，16，64…(4的某次方)
谁最先把草吃完，谁获胜
假设牛和羊都绝顶聪明，都想赢，都会做出理性的决定
根据唯一的参数N，返回谁会赢
*/
public class Code02_CowAndSheep {
    public static String whoWin(int N){
        return process(N)==0?"先手":"后手";
    }

    //返回N份草的情况下谁会赢，0表示先手赢，1表示后手赢
    private static int process(int N) {
        if(N%4==0) return 0;
        int base = 1;
        while (base<=N){
            if(N-base==0||process(N-base)==1){
                return 0;//只要有赢的机会就选那个，递归就是管好自己就好了，别去想子过程，就是你做好榜样就可以了，别人就懂怎么做了
            }

            if(base>(N>>2)) break;//防止乘四后溢出
            base<<=2;
        }
        return 1;//没有赢就对手赢了
    }
    public static String whoWin2(int N){
        if(N<=12) return (N==2||N==5||N==7||N==10)?"后手":"先手";
        int idxInGroup = (N-13)%20;
        return (idxInGroup==0||idxInGroup==2||idxInGroup==9||idxInGroup==12||idxInGroup==14||idxInGroup==17)?"后手":"先手";
    }
    public static void main(String[] args) {
        int i = 0,testTimes=100;
        for(;i<testTimes;i++)
           if(!whoWin2(i).equals(whoWin(i))) {
               System.out.println(i);
               System.out.println(whoWin(i));
               System.out.println(whoWin2(i));
               break;
           }
        System.out.println(i==testTimes?"finish!!!":"fucking!!!");
    }
}
