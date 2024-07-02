package waigo.zuoshen.basic.MyCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * author waigo
 * create 2021-03-22 12:08
 */
/*IPO (Initial public offerings)首次公开募股
假设 力扣（LeetCode）即将开始其 IPO。为了以更高的价格将股票卖给风险投资公司，力扣 希望在 IPO 之前开展一些项目以增加其资本。
由于资源有限，它只能在 IPO 之前完成最多 k 个不同的项目。帮助 力扣 设计完成最多 k 个不同项目后得到最大总资本的方式。
给定若干个项目。对于每个项目 i，它都有一个纯利润 Pi，并且需要最小的资本 Ci 来启动相应的项目。最初，你有 W 资本。当你完成一个项目时
，你将获得纯利润，且利润将被添加到你的总资本中。总而言之，从给定项目中选择最多 k 个不同项目的列表，以最大化最终资本，并输出最终可获得的最多资本。
示例：
输入：k=2, W=0, Profits=[1,2,3], Capital=[0,1,1].
输出：4
解释：
由于你的初始资本为 0，你仅可以从 0 号项目开始。
在完成后，你将获得 1 的利润，你的总资本将变为 1。
此时你可以选择开始 1 号或 2 号项目。
由于你最多可以选择两个项目，所以你需要完成 2 号项目以获得最大的资本。
因此，输出最后最大化的资本，为 0 + 1 + 3 = 4。
提示：
假设所有输入数字都是非负整数。
表示利润和资本的数组的长度不超过 50000。
答案保证在 32 位有符号整数范围内。
*/
//注意点1：一个项目只能做一遍
public class Code05_IPO {
    //暴力递归做对数器
    public static int findMaximizedCapital1(int k, int W, int[] Profits, int[] Capital) {
        return process(k,W,Profits,Capital);
    }
    //返回当前可以做k个项目，W个本金的基础上能够得到最大的总资本
    private static int process(int k, int w, int[] profits, int[] capital) {
        if(k==0) return w;//多少钱进就回多少钱，没做项目，没有利润
        //遍历所有方案，拿到能够得到最大总资本的方案
        int maxCapital = w;
        for(int i = 0;i < capital.length;i++){
            if(w>= capital[i]){
                //表示这个项目能做
                int[] newProfit = copyExcept(profits,i);
                int[] newCapital = copyExcept(capital,i);
                maxCapital = Math.max(maxCapital,process(k-1,w+profits[i],newProfit,newCapital));
            }
        }
        return maxCapital;
    }
    public static int[] copyExcept(int[] arr,int i){
        int[] res = new int[arr.length-1];
        int z = 0;
        for(int j=0;j<res.length;z++,j++){
            res[j] = j==i?arr[++z]:arr[z];
        }
        return res;
    }
    //贪心策略，将所有项目按照成本入小根堆，这样先弹出的都是能够做的，将所有能做的项目弹出压入根据利润排序的大根堆，这样上面的就是利润最大的
    public static int findMaximizedCapital(int k, int W, int[] Profits, int[] Capital) {
        PriorityQueue<Integer> cHeap = new PriorityQueue<>(Profits.length,Comparator.comparingInt(o -> Capital[o]));//堆中存的都是下标，下标对应项目成本和利润
        PriorityQueue<Integer> pHeap = new PriorityQueue<>(Capital.length,(o1,o2)->Profits[o2]-Profits[o1]);
        for(int i = 0;i<Capital.length;i++){
            cHeap.add(i);
        }
        while (k>0){
            while (!cHeap.isEmpty()&&W>=Capital[cHeap.peek()]){
                pHeap.add(cHeap.poll());
            }
            if(!pHeap.isEmpty()){
                W+=Profits[pHeap.poll()];
                k--;
            }else break;//一个项目都没有说明都做不了了
        }
        return W;
    }
    //对数器
    public static int[][] generalRandomCP(int maxLen,int maxVal){
        int len = (int) (Math.random()*maxLen)+1;
        int[] profits = new int[len];
        int[] capital = new int[len];
        for(int i = 0;i< len;i++){
            profits[i] = (int) (Math.random()*maxVal)+1;
            capital[i] = (int) (Math.random()*maxVal)+1;
        }
        return new int[][]{profits,capital};
    }
    public static void main(String[] args) {
//        int k=10, W=0;
//        int[] Profits=new int[]{1,2,3}, Capital=new int[]{0,1,2};
//        System.out.println(findMaximizedCapital(k,W,Profits,Capital));
        int maxLen = 5,maxVal = 10,testTimes=1000000;
        int i = 0;
        for(;i<testTimes;i++){
            int[][] randomCP = generalRandomCP(maxLen, maxVal);
            int k = (int)(Math.random()*maxLen)+1;
            int W = (int)(Math.random()*maxVal)+1;
            int res = findMaximizedCapital(k, W, randomCP[0], randomCP[1]);
            int res1 = findMaximizedCapital1(k, W, randomCP[0], randomCP[1]);
            if(res != res1){
                System.out.println("k:"+k);
                System.out.println("W:"+W);
                System.out.println("p:"+Arrays.toString(randomCP[0]));
                System.out.println("c:"+Arrays.toString(randomCP[1]));
                System.out.println("res:"+res);
                System.out.println("res1:"+res1);
                break;
            }
        }
        System.out.println(i==testTimes?"finish!!!":"fucking!!!");
    }
}
