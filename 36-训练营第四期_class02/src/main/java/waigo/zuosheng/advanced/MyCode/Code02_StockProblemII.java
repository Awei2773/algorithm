package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-05 8:45
 */

/**
 * 给定一个数组arr，从左到右表示昨天从早到晚股票的价格
 * 作为一个事后诸葛亮，你想知道如果随便交易，
 * 且每次交易只买卖一股，返回能挣到的最大钱数
 *
 * 给定一个数组 prices ，其中 prices[i] 是一支给定股票第 i 天的价格。
 *
 * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
 *
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 *
 */
public class Code02_StockProblemII {
    public static int maxProfit(int[] prices) {
        if(prices==null||prices.length<2) return 0;
        int res = 0;
        for (int i = 0; i < prices.length-1; i++) {
            if(prices[i+1]>prices[i]){
                //一条上坡出现了，榨出它所有的答案
                res+=prices[i+1]-prices[i];
            }
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{1,2,3,4,5}));
    }
}
