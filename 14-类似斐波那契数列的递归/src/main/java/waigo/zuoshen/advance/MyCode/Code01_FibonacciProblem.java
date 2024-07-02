package waigo.zuoshen.advance.MyCode;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-03-10 10:15
 */
public class Code01_FibonacciProblem {
    //斐波那契数列 1,1,2,3,5这样，每一项都等于前两项的和 F(n) = F(n-1)+F(n-2)
    //1.递推实现
    public static int recurGetFibonaccN(int N) {
        if (N == 1 || N == 2) return 1;
        if (N == 3) return 2;
        return recurGetFibonaccN(N - 1) + recurGetFibonaccN(N - 2);
    }

    //2.线性方式实现
    public static int getFibonaccN(int N) {
        if (N == 1 || N == 2) return 1;
        if (N == 3) return 2;
        int resN_2 = 1;
        int resN_1 = 2;
        for (int i = 4; i <= N; i++) {
            int temp = resN_1;
            resN_1 += resN_2;
            resN_2 = temp;
        }
        return resN_1;
    }
    //3.按照上面两种实现方式最多只能将时间复杂度优化到O(N)，而通过线性代数的一种定理可以将斐波那契问题优化到log(N)
    //定理：如果F(N) = aF(N-1)+bF(N-2)+...+cF(n-k)这样的递推式是不以条件转移的(就是不是在某种条件下才成立，而是强成立的)
    //那么就有这么一种结论：|F(N),F(N-1)...,F(N-k+1)| = |F(k),F(k-1)...,F(1)|*(k阶矩阵行列式)^(n-k)


    //利用这个定理解决斐波那契问题F(N) = F(N-1)+F(N-2)这是定理的二阶问题，我们需要求解一个2x2的矩阵行列式
    //{
    //  {a,b},
    //  {c,d},列出两个式子求解出这四个变量就解出了行列式,几阶问题就需要找几个例子来计算行列式
    //}
    //解得
    //{
    //  {1,1},
    //  {1,0}
    //}
    //所以|F(N),F(N-1)|=|F(2),F(1)|* |1,1|^(N-2)
    //                              |1,0|

    //现在问题就转变成了如何快速求解一个矩阵的N次幂，有一种快速幂算法
    //比如求6的75次方，最快的方式是使用快速幂算法
    //75-->toBinary==> 1001011
    //6^75 => 6^64*6^8*6^2*6^1
    //也就是说遍历这个二进制串，整个中间变量t，每次都乘以6，整出6^1,6^2这样的中间值
    //如果此时遍历到的位的值是1就将这个中间变量的值加到res中，最后返回res不就是幂值嘛，实现一把

    //return a^b b是大于大于0的整数
    // 6^75 => 6^64*6^8*6^2*6^1
    public static int quickPow(int a, int b) {
        int t = a;
        int res = 1;
        do {
            if ((b & 1) != 0) {//看一下最后一位是否为1，表示此时需不需要将中间值乘到结果中
                res *= t;
            }
            t *= t;//拿2的幂来说，一开始是2^1,然后2^2,2^4,2^8这样，所以每次都是乘以自己
        } while ((b >>= 1) != 0);
        return res;
    }

    //同理，我们现在想要算矩阵的快速幂，那么就只需要知道两个矩阵怎么乘能够拿到中间值不就可以了？
    //if a,b is valid，return a*b
    public static int[][] matrixMul(int[][] a, int[][] b) {
        if (a == null || b == null) return null;
        if (a[0].length != b.length) return null;
        int[][] res = new int[a.length][b[0].length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                //res[i][j]需要a的i行乘以b的[j]列的和
                for(int ik = 0;ik<a[0].length;ik++){//ik表示i行k列
                    res[i][j]+=a[i][ik]*b[ik][j];//每个i行k列都要乘以b中k行j列
                }
            }
        }
        return res;
    }
    //矩阵的快速幂算法 return a^b
    public static int[][] matrixPow(int[][] a,int b){
        int[][] res = new int[a.length][a[0].length];
        //res初始化为一个单位矩阵，单位矩阵就表示矩阵中的1
        for(int i = 0;i<res.length;i++){
            res[i][i] = 1;
        }
        int[][] t = a;
        do{
            if((b&1)!=0){
                res = matrixMul(res,t);
            }
            t = matrixMul(t,t);
        }while ((b>>=1)!=0);

        return res;
    }
    public static void printAry(int[][] ary){
        if(ary==null) return;
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for(int i = 0;i < ary.length;i++)
            builder.append(Arrays.toString(ary[i])).append(",");
        if(builder.length()>1) builder.deleteCharAt(builder.length()-1);
        builder.append("]");
        Logger.getGlobal().info(builder.toString());
    }
    public static int quickGetFibonacciN(int N){
        if(N<1) return 0;
        if(N==1||N==2) return 1;
        int[][] base = {
                {1,1},
                {1,0}
        };
        //|F(N),F(N-1)|=|1,1|*base^(N-2)
        //base^(N-2) = {{x,y},{z,d}} ==> F(N) = x+z
        int[][] basePow = matrixPow(base, N - 2);
        return basePow[0][0]+basePow[1][0];
    }



    //例题1：
    /*
    假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
    每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
    */
    //思路：这里先进行推理：
    //1.  一个台阶只有一种爬法
    //2.  两个台阶有两种爬法
    //3.  三个台阶等于先爬到第一个台阶然后直接爬两步到第三级台阶加上先爬到第二个台阶然后直接爬异步到第三级台阶的和
    //    那就是1+2=3
    //         ....
    //n. F(N) = F(N-1)+F(N-2),这就是和斐波那契数列一样的二阶问题
    public static int getUpStairNum(int N){
        if(N<1) return 0;
        if(N<=2) return N==1?1:2;
        int[][] base = {
                {1,1},
                {1,0}
        };
        int[][] matrixPow = matrixPow(base, N - 2);
        return 2*matrixPow[0][0] + matrixPow[1][0];
    }
    //例题2：生奶牛问题
    /*
    第一年农场有1只成熟的母牛A，往后的每年：
    1）每一只成熟的母牛都会生一只母牛
    2）每一只新出生的母牛都在出生的第三年成熟
    3）每一只母牛永远不会死
    返回N年后牛的数量
    */
    /*
    思路：先进行推理
    第一年：A(成熟)-->1个
    第二年：A(成熟)，B-->2个
    第三年：A(成熟)，B，C-->3个
    第四年：A(成熟)，B(成熟)，C，D-->4个
    第五年：A(成熟)，B(成熟)，C(成熟)，D，E，F-->6个
    第六年：A(成熟)，B(成熟)，C(成熟)，D(成熟)，E，F，G，H，I-->9个
    经过推演，可以发现第N年的牛的个数F(N)=F(N-1)+0*F(N-2)+F(N-3)
    就是去年出生的今年还会活着，而三年前就已经出生的，现在肯定是成熟的，可以生新的小牛
    |F(N),F(N-1),F(N-2)| = |F(3),F(2),F(1)|*|3*3|^(N-3)
    这是一个三阶问题
    我发现在计算base矩阵的时候，那些个矩阵中的值不是0就是1，有时候可以合理猜测节省解方程时间
    */
    public static int getCowNum(int N){
        if(N<1) return 0;
        if(N<4) return N==1?1:(N==2?2:3);
        int[][] base = {
                {1,1,0},
                {0,0,1},
                {1,0,0}
        };
        int[][] matrixPow = matrixPow(base, N - 3);
        return 3*matrixPow[0][0]+2*matrixPow[1][0]+matrixPow[2][0];
    }
    //例题3：合法01串问题
    /*
    给定一个数N，想象只由0和1两种字符，组成的所有长度为N的字符串
    如果某个字符串,任何0字符的左边都有1紧挨着,认为这个字符串达标
    返回有多少达标的字符串

    思路：字符串的开头肯定1，这是确定的，然后第二位可以选择是0或者1，第三位可以在第二位选0的时候选1
    也就是说我们可以设置递归思路这样，当前位选了1，然后返回有多少达标的字符串
    */
    //返回第一位是1时，后面N-1个字符串能够排列出合法字符串的个数
    //可以多列几项，看看这一项可不可以由前几项加工出来
    public static int recurGetValidStrNum(int N){
        if(N<1) return 0;
        if(N<=2) return N==1?1:2;
        return recurGetValidStrNum(N-1)+recurGetValidStrNum(N-2);
    }
    public static int quickGetValidStrNum(int N){
        if(N<1) return 0;
        if(N<=2) return N==1?1:2;
        int[][] base = {
                {1,1},
                {1,0}
        };
        int[][] matrixPow = matrixPow(base, N - 2);
        return 2*matrixPow[0][0] + matrixPow[1][0];
    }
    public static void main(String[] args) {
        /*int testTimes = 10;
        int i = 0;
        for (; i < testTimes; i++) {
            int N = (int) (Math.random() * 10) + 1;
            int resRecur = recurGetFibonaccN(N);
            int fibonaccN = getFibonaccN(N);
            int quickRes = quickGetFibonacciN(N);
            if (resRecur != fibonaccN||resRecur!=quickRes) {
                System.out.println("resRecur:"+resRecur);
                System.out.println("fibonaccN:"+fibonaccN);
                System.out.println("quickRes:"+quickRes);
                System.out.println(N);
                break;
            }
        }
        System.out.println(i == testTimes ? "finish!!!" : "fucking!!!");*/
//        System.out.println(getUpStairNum(5));
//        System.out.println(getCowNum(6));
        System.out.println(recurGetValidStrNum(24));
        System.out.println(quickGetValidStrNum(24));
    }

}
