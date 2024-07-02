package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-07 12:47
 */
/*
https://leetcode-cn.com/problems/super-washing-machines/

假设有 n 台超级洗衣机放在同一排上。开始的时候，每台洗衣机内可能有一定量的衣服，也可能是空的。
在每一步操作中，你可以选择任意 m （1 ≤ m ≤ n） 台洗衣机，与此同时将每台洗衣机的一件衣服送到相邻的一台洗衣机。
给定一个非负整数数组代表从左至右每台洗衣机中的衣物数量，请给出能让所有洗衣机中剩下的衣物的数量相等的最少的操作步数。
如果不能使每台洗衣机中衣物的数量相等，则返回 -1。

示例 1：

输入: [1,0,5]

输出: 3

解释:
第一步:    1     0 <-- 5    =>    1     1     4
第二步:    1 <-- 1 <-- 4    =>    2     1     3
第三步:    2     1 <-- 3    =>    2     2     2
示例 2：

输入: [0,3,0]

输出: 2

解释:
第一步:    0 <-- 3     0    =>    1     2     0
第二步:    1     2 --> 0    =>    1     1     1
示例 3:

输入: [0,2,0]

输出: -1

解释:
不可能让所有三个洗衣机同时剩下相同数量的衣物。
 

提示：

n 的范围是 [1, 10000]。
在每台超级洗衣机中，衣物数量的范围是 [0, 1e5]。

思路分析：
1.如果总数N%洗衣机数量M!=0必不可能平分，返回-1
2.如果N%M!=0总是可以平分的，最不济我全都挤到一起去，然后再一个一个分出去也能平分，关键是怎么找到最小操作步数。
一个下标i，它之前有i个位置，所以之前的衣服在平分之后应该为i*(N/M)件，假设目前已经有P件了，i之后的所有位置的衣服和在平分之后应该为(N-(i+1))*(N/M)件
假设目前已经有A件了。

1.P-i*(N/M)=左边需要丢给i或者需要从i接收的衣服数量，记为LN
  A-(arr.length-(i+1))*(N/M)=右边需要丢给i或者需要从i接收的衣服数量，记为RN
分情况讨论
  1.1
    LN<0,RN<0,此时说明i位置很多，需要往左右丢|LN|+|RN|件衣服，丢完后i位置合法，因为每次只能从一台机器上往左或者往右丢一件，所以
    需要|LN|+|RN|次操作
  1.2
    LN<0,RN>0,此时说明i需要从右边接收|RN|件，然后还要向左边丢|LN|件，接收和丢是可以同时进行的，所以这种情况下最少需要max(|LN|,|RN|)次操作
    LN>0,RN<0，从左边收，丢到右边，和上面一样
    LN>0,RN>0,i特别少，需要从左右收，这个也是可以同时左右一起进行的

    LN=0,RN>0
    LN=0,RN<0
    LN<0,RN=0,
    LN>0,RN=0
    这7种情况都是最少需要max(|LN|,|RN|)次操作
  1.3
    LN=0,RN=0
    这种情况说明i位置已经合法，不需要操作，也可以看做是1.2里的情况，为了代码简洁

  每个位置的最少操作算出来我们就可以分析一下在这些最少操作数中最大的那个了，如果说在最小操作数最大的那个位置完成自己的操作之后其他的位置也能完成
  自己的操作，那么最小操作数就是这个最大值了。

  如果每个位置一开始都能够正常工作，那么这个结论肯定是正确的，因为从一开始就在算操作数，每个操作都让所有位置的mostLessOpr--,那么最多
  操作数那个完成的时候其他位置肯定都完成了

  那么关键在于就算一开始不能够正常工作，就是收不到，也丢不出去，也就是自己是0，自己左右也都是0的情况
  在这种情况下是否能够保证操作数不超过那个最大位置的操作数下完成？
  这就是贪心问题，严格证明就不考虑了。反正想了一下没有找到反例就直接实现试试看。


  实现关键点
  1.P，这可以用一个变量一直累加，不用前缀和数组
  2.N，数组和，这必须得一开始就求了
  3.A可以用N和LN以及arr[i]求出来，A=N-P-arr[i]

*/
public class Code02_PackingMachine {
    public static int findMinMoves(int[] machines) {
        if(machines==null||machines.length<2) return 0;
        int N = 0;
        for(int i = 0;i<machines.length;i++)
            N+=machines[i];
        int mostLessOpr = -1;
        int P = 0;//0~i-1的累加和
        int aSum = N/machines.length;//一个位置最后放几个
        if(N%machines.length==0){//分不了就直接返回
            for(int i = 0;i<machines.length;i++){
                int LN = P-i*aSum;//左边还需要操作的
                int A = N - P - machines[i];//右边目前有的
                int RN = A-(machines.length-(i+1))*aSum;//右边还需要操作的
                if(LN<0&&RN<0){
                    mostLessOpr = Math.max(mostLessOpr,Math.abs(LN)+Math.abs(RN));//|LN|+|RN|次操作
                }else{
                    mostLessOpr = Math.max(mostLessOpr,Math.max(Math.abs(LN),Math.abs(RN)));
                }
                P+=machines[i];
            }
        }
        return mostLessOpr;
    }

    public static void main(String[] args) {
        int[] a = {1,0,5};
        findMinMoves(a);
    }
}
