package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-22 21:17
 */

/**
 * 给出一组正整数arr，你从第0个数向最后一个数，
 * 每个数的值表示你从这个位置可以向右跳跃的最大长度
 * 计算如何以最少的跳跃次数跳到最后一个数。
 */
public class Code04_JumpGame {
    //O(N^2)太慢了
    public static int minimumStepToEnd(int[] arr){
        int N = arr.length;
        int[] dp = new int[N];//dp[i]表示从i跳到最后一个位置的最小步数
        dp[N-1] = 0;
        for(int i=N-2;i>=0;i--){
            dp[i] = Integer.MAX_VALUE;
            int maxStep = Math.min(N-1-i,arr[i]);
            for(int step = 1;step<=maxStep;step++){
                dp[i] = Math.min(dp[i],1+dp[i+step]);
            }
        }
        return dp[0];
    }
    //想着怎么优化，两步能够走到的地方一定是一步能够走到的地方拱出来的
    //所以整一个边界，从一步开始，一步能够拱出的边界往外扩
    //需要两个边界，一个是当前步数囊括的边界，一个是现在步数往外扩出去的边界
    //这里有个小贪心，那就是我1步就能到的地方我最后的步数肯定是比你两步才到的少，相当于对上面方法的剪枝
    public static int minimumStepToEnd2(int[] arr){
        int step = 0;
        int curEdge = 0;
        int nextEdge = 0;
        for (int i = 0; i < arr.length; i++) {
            if(i>curEdge) {
                step++;
                curEdge=nextEdge;
            }
            nextEdge = Math.max(i+arr[i],nextEdge);
        }
       return step;
    }
    //for test
    public static int[] getRandomAry(int maxVal,int maxSize){
        int size = (int) (Math.random()*maxSize)+1;
        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            res[i] = (int) (Math.random()*maxVal)+1;
        }
        return res;
    }
    public static void main(String[] args) {
        int maxSize = 10;
        int maxVal = 10;
        int[] ary = getRandomAry(maxVal, maxSize);
        System.out.println(minimumStepToEnd2(ary));
        System.out.println(minimumStepToEnd(ary));
    }
}
