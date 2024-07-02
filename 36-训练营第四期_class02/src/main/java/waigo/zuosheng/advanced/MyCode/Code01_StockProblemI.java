package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-05 7:51
 */

/**
 * 给定一个数组arr，从左到右表示昨天从早到晚股票的价格。
 * 作为一个事后诸葛亮，你想知道如果只做一次交易，且每次交易只买卖一股，返回能挣到的最大钱数
 */

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
 * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
 */
public class Code01_StockProblemI {
    /**
     * 思路：在上坡开始的时候存max，在下坡开始的时候如果min比之前的min要小更新min
     * 下坡开始的时候抓一遍答案，坡平的情况直接跳过去
     */
    public static int maxProfit(int[] prices) {
        if(prices==null||prices.length<2) return 0;
        int min = Integer.MAX_VALUE;
        int max;
        int res = 0;
        for (int i = 0; i < prices.length-1; i++) {
            if(prices[i+1]>prices[i]){
                min = Math.min(min,prices[i]);//拼出一个min
                //上坡，更新max
                while (i+1 < prices.length&&prices[i+1]>prices[i])
                    i++;
                //上坡全看一遍了，抓max
                max = prices[i];
                res = Math.max(res,max-min);
                i--;//这个点还没看过呢，局部最高点
            }
        }
        return res;
    }
    //每天卖出股票能够获得的最大利益PK出最大那个
    public static int maxProfit2(int[] prices) {
        if(prices==null||prices.length<2) return 0;
        int min = prices[0];
        int res = 0;
        for (int i = 1; i < prices.length; i++) {
            res = Math.max(res,prices[i]-min);
            min = Math.min(min,prices[i]);
        }
        return res;
    }
    public static int maxProfit3(int[] prices) {
        int today0 = 0,today1 = -prices[0];//结果就是today0
        for(int i = 1;i<prices.length;i++){
            today0 = Math.max(today0,today1+prices[i]);//为什么today0使用几天的没有影响？因为就算是当前卖出了，那我再买回来就抵消了
            today1 = Math.max(today1,today0-prices[i]);
        }
        return today0;
    }
    /**
     继续套路
     T[i][k][0] T[i][k][1],最多两次交易，所以k取值0,1,2，k表示的是买卖了几次
     T[i][2][0]是答案
     T[i][2][0] = Math.max(T[i-1][2][0],T[i-1][1][1]+prices[i]);//先看看答案，需要哪些位置，然后一层一层推下去
     T[i][1][1] = Math.max(T[i-1][1][1],T[i-1][1][0]-prices[i]);
     T[i][1][0] = Math.max(T[i-1][1][0],T[i-1][0][1]+prices[i]);
     T[i][0][1] = Math.max(T[i-1][0][1],-prices[i]);//OK，你看圆回来了，所有未知数都能下推了
     所以使用四个变量
     */
    public static int maxProfit4(int[] prices) {
        int twoZero = 0,oneOne = Integer.MIN_VALUE,oneZero = 0,zeroOne = Integer.MIN_VALUE;
        for(int i = 0;i<prices.length;i++){
            twoZero = Math.max(twoZero,oneOne+prices[i]);//先看看答案，需要哪些位置，然后一层一层推下去
            oneOne = Math.max(oneOne,oneZero-prices[i]);
            oneZero = Math.max(oneZero,zeroOne+prices[i]);
            zeroOne = Math.max(zeroOne,-prices[i]);//OK，你看圆回来了，所有未知数都能下推了
        }
        return twoZero;
    }
    public static int maxProfit5(int[] prices) {
        if(prices==null||prices.length<2) return 0;
        int n = prices.length;
        //需要三个变量
        int i0 = 0,i1 = -prices[0],i_2 = 0;
        int next0,next1;
        for(int i = 1;i<n;i++){
            next0 = Math.max(i0,i1+prices[i]);
            next1 = Math.max(i1,i_2-prices[i]);
            i_2 = i0;
            i0 = next0;
            i1 = next1;
        }//需要注意，i-2是相对于刚刚进循环来说的，刚进循环的时候i0是[i-1][0],而i_2每次总比i0慢一位，所以i_2就表示为[i-2][0]
        return i0;
    }
    public static void main(String[] args) {
//        System.out.println(maxProfit5(new int[]{1,2,3,0,2}));

    }
}
