package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-13 12:46
 */

import java.util.Arrays;
import java.util.Comparator;

/**
 * 给你一个二维整数数组 envelopes ，其中 envelopes[i] = [wi, hi] ，表示第 i 个信封的宽度和高度。
 * 当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样。
 * 请计算 最多能有多少个 信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）。
 * 注意：不允许旋转信封。
 *  
 * 示例 1：
 *
 * 输入：envelopes = [[5,4],[6,4],[6,7],[2,3]]
 * 输出：3
 * 解释：最多信封的个数为 3, 组合为: [2,3] => [5,4] => [6,7]。
 * 示例 2：
 *
 * 输入：envelopes = [[1,1],[1,1],[1,1]]
 * 输出：1
 *  
 *
 * 提示：
 *
 * 1 <= envelopes.length <= 5000
 * envelopes[i].length == 2
 * 1 <= wi, hi <= 10^4
 */
/*
思路分析：
将二维问题化一维解决，固定一边来讨论问题，所以先对所有信封根据宽度进行排序，
这样能装进我的信封都在我的左边，我只能装进行我右边的信封中。

数据状况，信封最多5000个，所以N^2的方法能过

枚举每个信封作为最顶上最多能套入几个信封的情况，答案必在其中。
一：暴力法
尝试模型：从左到右进行尝试，求解以每个位置i作为封面时候，[0...i]能嵌套几层
1.base case：0
只有自己，肯定嵌套一层
2.普通位置x
此时在求i位置信封，遍历[0...x-1]，如果能够装入i位置则抓一下在它们装入
i时候，i的最大嵌套数量。
3.在求每个位置的最大值的同时抓一下答案

二:优化动归
这就直接过了，但是还是想思考一下能不能优化，因为N^2比较重了

三:转换成求最长递增子序列问题
想要优化N^2,那就是简化查找的过程，或者找到一种不用查找的方法，直接O(1)拿到每个位置上的答案。

我们之前那种方法，求某个位置x的时候往前找是找的啥？
我们想要知道  前面哪个位置  是我们能够包进去的同时   它的答案是最大的


如果只是对宽度进行排序，那么你只能够知道当前在求的x位置前面,[0...x]上宽度都是<=w[x]的，但是长度肯定是乱七八糟随便排的
对于一个乱糟糟的序列咱们不可能能够简化查找，所以思考能不能靠单调性来加速。

在宽度一定的情况下，肯定是长度越长，我的包容性越好能够装的也就越多啦，所以考虑对宽度相同的情况下，考虑长度按降序来排。
这样子还是不能够保证从左到右有单调性，虽然分成了很多小的单调区间。

不过神奇的事情发生了，众所周知，在宽度合适的情况下，左边想要给右边的包进去肯定是要是一个递增的序列。
通过我们对长度进行了降序的处理，就规避掉了宽度相同的捣蛋情况了，因为宽度相同你必然长度是降序的，不可能同时进一个递增序列。
所以只要找到二维长度上的一个递增序列长度其实问题就解了。

*/
public class Code04_RussianDollEnvelopes {
    public static int maxEnvelopes(int[][] envelopes) {
        if(envelopes==null||envelopes.length==0) return 0;
        Arrays.sort(envelopes, Comparator.comparingInt(a->a[0]));
        int max = 0;
        for(int i = 0;i<envelopes.length;i++){
            max = Math.max(max,process(envelopes,i));
        }
        return max;
    }

    //[0...x]上以x作为封面的时候最多嵌套多少层
    private static int process(int[][] envelopes, int x) {
        if(x==0) return 1;
        //遍历[0...x-1]
        int max = 1;
        for(int i = 0;i<x;i++){
            if(envelopes[i][0]<envelopes[x][0]&&envelopes[i][1]<envelopes[x][1]){
                max = Math.max(process(envelopes,i)+1,max);//别忘记了，封面这一层
            }
        }
        return max;
    }
    //改动归
    //[0...x]上以x作为封面的时候最多嵌套多少层
    private static int maxEnvelopes1(int[][] envelopes) {
        if(envelopes==null||envelopes.length==0) return 0;
        Arrays.sort(envelopes, Comparator.comparingInt(a->a[0]));
        int N = envelopes.length;
        int[] dp = new int[N];
        dp[0] = 1;
        int max;
        int res = 1;
        for (int x = 1; x < N; x++) {
            max = 1;//即使是前面都套不进去，最小也是1，别丢了自己
            for(int i = 0;i<x;i++){
                if(envelopes[i][0]<envelopes[x][0]&&envelopes[i][1]<envelopes[x][1]){
                    max = Math.max(dp[i]+1,max);//别忘记了，封面这一层
                }
            }
            dp[x] = max;
            res = Math.max(res,dp[x]);
        }
        return res;
    }

    private static int maxEnvelopes2(int[][] envelopes) {
        if(envelopes==null||envelopes.length==0) return 0;
        Arrays.sort(envelopes, (a,b)->{
            if(a[0]!=b[0])
                return a[0]-b[0];//不相同按宽度升序
            else
                return b[1]-a[1];//相同按长度降序
        });
        //找到envelopes长度组成的一维数组中的最长递增子序列,具体解法看  #300.最小递增子序列
        int N = envelopes.length;
        int[] ends = new int[N];//ends[i]表示i+1长度的子序列最小结尾的值
        int right = 0;
        ends[0] = envelopes[0][1];
        int l,r,m;
        int target;
        for(int i = 1;i<N;i++){
            l = 0;
            r = right;//[l...r]上=>nums[i]的最左位置
            target = -1;//赋初值
            int curNum = envelopes[i][1];//nums[i]
            while (l<=r){
                m = ((r-l)>>>1)+l;
                if(ends[m]>=curNum){
                    target = m;
                    r = m-1;
                }else{
                    l = m+1;
                }
            }
            //target如果没有找到说明要扩right了
            target = target==-1?(++right):target;
            ends[target] = curNum;
        }
        return right+1;
    }
    //for test
    public static int[][] getRandomAry(int maxLen,int maxVal){
        int len = (int) (Math.random()*maxLen)+1;
        int[][] res = new int[len][2];
        for (int i = 0; i < len; i++) {
            res[i][0] = (int) (Math.random()*maxVal)+1;
            res[i][1] = (int) (Math.random()*maxVal)+1;
        }
        return res;
    }
    public static void main(String[] args) {
        int testTimes= 1000000;
        int i = 0;
        for(;i<testTimes;i++){
            int[][] randomAry = getRandomAry(10, 10);
            int res1 = maxEnvelopes1(randomAry);
            int res2 = maxEnvelopes2(randomAry);
            if(res1!=res2){
                System.out.println("res1："+res1);
                System.out.println("res2："+res2);
                for (int j = 0; j < randomAry.length; j++) {
                    System.out.println(Arrays.toString(randomAry[i]));
                }
                break;
            }
        }
        System.out.println(i==testTimes?"finish!!!":"fucking!!!!");
    }
}
