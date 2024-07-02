package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-25 10:27
 */
//经典数位DP
public class Code01_WriteHowManyOne {
    public static int howManyOne(int N){
        int count = 0;
        for (int i = 0; i <= N; i++) {
            int curNum = i;
            while (curNum>0){
                if(curNum%10==1) count++;
                curNum/=10;
            }
        }
        return count;
    }
    //数位DP，就是每次干一个高位，将问题从多位往低位进行分治
    //考量每个位置上的1如何直接使用公式求快点
    //假设N为K位
    //最高位为1的时候，例如，131532，那么最高位共有100000~131532个1，就是31533个
    //最高位不为1的时候，例如，423423，那么最高位共有100000~199999个1，10^(k-1)个
    //不是最高位咋算？
    //131532的第二位确定为1的时候共有几种情况？
    //你会发现，千位、百位、个位不管是选什么总会能够通过调整高位使得它落入31533~131532这一段中
    //同理，第三位，第四位...第K位都是如此，所以这一段中1的个数为高位的31533+10^(k-2)*(k-1)
    //10^(k-2)指的是每个位置都可能选0~9，共10种可能性，而高位作为调整，自然只有一种选择

    //如果高位不为1的时候，第二位咋求？
    //423423的第二位确定为1的时候共有几种情况？现在考虑的是23424~423423这一段
    //可以将这一段分为
    //23424~123423
    //123424~223423
    //223424~323423
    //323424~423423
    //通过修改最高位总是能够使得第二位为1的时候都唯一的落入这四个块中
    //所以23424~423423这一段的1的个数为高位的10^(k-1)+4*10^(k-2)*(k-1)
    //这一段算完之后，可以踢掉一个高位，下面去递归
    public static int howManyOne2(int N){
        if(N<=0) return 0;
        //现在算N有几位
        int[] K = getNumsAndFirOfN(N);
        int K_1 = (int) Math.pow(10,K[0]-1);

        int first = K[1];
        int firRes = first==1?N-K_1+1: (int) Math.pow(10, (K[0] - 1));
        int nextRes = first*(int)(Math.pow(10,K[0]-2))*(K[0]-1);
        return firRes+nextRes+howManyOne2(N-(K_1*first));
    }

    private static int[] getNumsAndFirOfN(int N) {
        int count = 0;
        int first = 0;
        while (N!=0){
            first = N;
            N/=10;
            count++;
        }
        return new int[]{count,first};
    }

    public static void main(String[] args) {
        int i = 0,testTimes = 10000;
        for(;i<testTimes;i++){
            if(howManyOne2(i)!=howManyOne(i)) break;
        }
        System.out.println(i==testTimes?"finish!!!":"fucking!!!");
    }
}
