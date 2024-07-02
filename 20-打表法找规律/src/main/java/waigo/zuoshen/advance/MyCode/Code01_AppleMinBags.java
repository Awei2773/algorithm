package waigo.zuoshen.advance.MyCode;

/**
 * author waigo
 * create 2021-03-28 18:19
 */
/*
小虎去买苹果，商店只提供两种类型的塑料袋，每种类型都有任意数量。
1）能装下6个苹果的袋子
2）能装下8个苹果的袋子
小虎可以自由使用两种袋子来装苹果，但是小虎有强迫症，他要求自己使用的袋子数量必须最少，且使用的每个袋子必须装满。
给定一个正整数N，返回至少使用多少袋子。如果N无法让使用的每个袋子必须装满，返回-1
*/
public class Code01_AppleMinBags {

    public static int getMinBags(int N){
        for(int curBag = N>>3;curBag>=0;curBag--){//测试最多8袋子到0个8袋子，能否装满，至少使用多少袋子意思是至多使用几个8袋子
            if((N-curBag*8)%6==0) return curBag + (N-curBag*8)/6;
        }
        return -1;
    }
    public static int getMinBags1(int N){
        if(N<17){
            return N==0?0:N==6||N==8?1:N==12||N==14||N==16?2:-1;
        }
        int begin = 3;
        int group = (N-17)/8;
        return (((N-17)%8)&1)==0?-1:begin+group;
    }

    public static void main(String[] args) {
        int testTimes = 1000000;
        int i = 0;
        for(;i<testTimes;i++) {
//            System.out.println(i+":"+getMinBags(i));
            int res1 = getMinBags1(i);
            int res2 = getMinBags(i);
            if(res1 != res2) {
                System.out.println(i);
                System.out.println("res1:"+res1);
                System.out.println("res2:"+res2);
                break;
            }
        }
        System.out.println(i==testTimes?"finish!!!":"fucking!!!");

    }
}
