package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-07-11 0:13
 */
public class Code05_MinimumCostToMergeStones {
    public int mergeStones(int[] stones, int k) {
        if(stones==null||stones.length==0||k<=1||((stones.length-1)%(k-1)!=0)) return -1;
        int N = stones.length;
        int[] preSum = new int[N];
        preSum[0] = stones[0];
        for(int i = 1;i<N;i++){
            preSum[i] = stones[i] + preSum[i-1];
        }
        int[][][] dp = new int[N][N][k+1];
        //L:0~N-1,R:0~N-1,part:0~k
        return process(0,N-1,1,k,stones,preSum,dp);
    }
    //将L...R合成part个数，返回最小代价
    public int process(int L,int R,int part,int k,int[] stones,int[] preSum,int[][][] dp){
        if(dp[L][R][part]!=0){
            return dp[L][R][part];
        }
        if(L==R) return part==1?0:-1;
        if(part==1){
            int first = process(L,R,k,k,stones,preSum,dp);
            if(first==-1) {
                dp[L][R][part] = -1;
                return -1;
            }
            int res =  first + preSum[R] - (L-1<0?0:preSum[L-1]);
            dp[L][R][part] = res;
            return res;
        }
        //枚举前缀，枚举可能出现的第一块，然后让剩下的走递归
        //step = 1 ....
        int minimum = Integer.MAX_VALUE;
        for(int firBend = L;firBend<R;firBend+=(k-1)){
            int first = process(L,firBend,1,k,stones,preSum,dp);
            int next = process(firBend+1,R,part-1,k,stones,preSum,dp);
            if(next==-1) continue;
            minimum = Math.min(minimum,first+next);
        }
        int res = minimum==Integer.MAX_VALUE?-1:minimum;
        dp[L][R][part] = res;
        return res;
    }
}
