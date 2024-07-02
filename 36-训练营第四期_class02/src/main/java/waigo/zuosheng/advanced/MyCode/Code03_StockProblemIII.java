package waigo.zuosheng.advanced.MyCode;


import java.util.HashMap;
import java.util.Map;

/**
 * author waigo
 * create 2021-06-05 9:10
 */
public class Code03_StockProblemIII {
    /**
     * 其实问题问的就是选哪两个最高点进行结算，动态规划
     *
     * 给定一个数组arr，从左到右表示昨天从早到晚股票的价格
     * 作为一个事后诸葛亮，你想知 道如果交易次数不超过K次，
     * 且每次交易只买卖一股，返回能挣到的最大钱数
     */
    public static int maxProfit(int[] prices) {
        if(prices==null||prices.length<2) return 0;
        return process(prices,0,prices[0],2,new HashMap<String,Integer>());
    }
    //当前来到cur位置，之前位置的最小值为Min,还能选择rest个最高点
    private static int process(int[] prices, int cur, int min, int rest, Map<String,Integer> cache) {
        if(rest<=0||cur==prices.length) return 0;//没能抓了，返回0元
        String key = cur+"_"+min+"_"+rest;
        if(cache.containsKey(key)){
            System.out.println("cache.....");
            return cache.get(key);
        }

        //上坡切换
        while (cur+1<prices.length&&prices[cur+1]<=prices[cur])
            cur++;
        //抓上坡
        min = Math.min(min,prices[cur]);
        while(cur+1<prices.length&&prices[cur+1]>prices[cur]){
            cur++;
        }
        int res = prices[cur]-min;
        //到顶点了，做抉择，抓还是不抓
        //抓
        res += process(prices,cur+1,prices[cur],rest-1,cache);
        //不抓
        res = Math.max(res,process(prices,cur+1,min,rest,cache));
        cache.put(key,res);
        return res;
    }
    //如果只有两次交易机会可以优化
    //到某个点的时候有这么些可能性
    //1.已经交易了一次，但是还没买
    //1.1 已经交易了一次，现在手里拿着一股
    //2.已经交易了两次
    //3.还没交易过，但是还没买
    //3.1 还没交易过，手里拿着一股，只要每个位置结束的时候都把这些状态给准备好传给下个位置就行
    public static int maxProfit3(int[] prices) {
        if (prices == null || prices.length < 2) return 0;
        int buy1 = -prices[0];//只进行了一次买操作,存的就是买的啥
        int buy2 = -prices[0];//在一次交易后买了第二个
        int sell1 = 0;//一次交易完
        int sell2 = 0;//两次交易完
        for (int i = 1; i < prices.length; i++) {
            buy1 = Math.max(buy1,-prices[i]);
            //一次交易完可以是之前就交易完或者是之前买的，现在卖出
            sell1 = Math.max(sell1,prices[i]+buy1);
            buy2 = Math.max(buy2,sell1-prices[i]);
            sell2 = Math.max(sell2,prices[i]+buy2);
        }
        return sell2;
    }

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{3,3,5,0,0,3,1,4}));
    }
}
