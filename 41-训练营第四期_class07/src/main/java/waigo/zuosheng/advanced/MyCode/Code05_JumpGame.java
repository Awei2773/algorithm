package waigo.zuosheng.advanced.MyCode;

import java.util.HashMap;

/**
 * author waigo
 * create 2021-07-06 20:48
 */
public class Code05_JumpGame {
    public static boolean canJump(int[] nums) {
        //递归定义 从i位置能否跳到最后一个位置
        if(nums==null||nums.length==0) return false;
        return process(nums,0,new HashMap<>());
    }
    public static boolean process(int[] nums,int cur,HashMap<Integer,Boolean> cache){
        if(cache.containsKey(cur)) return cache.get(cur);
        if(cur==nums.length-1){
            cache.put(cur,true);
            return true;
        }
        boolean jump = false;
        for(int i = 1;i<=nums[cur];i++){
            if(cur+i<nums.length&&process(nums,cur+i,cache)){
                jump = true;
                break;
            }
        }
        cache.put(cur,jump);
        return jump;
    }
    public static boolean dpWays(int[] nums){
        //递归定义 从i位置能否跳到最后一个位置
        if(nums==null|| nums.length ==0) return false;
        int N = nums.length;
        boolean[] dp = new boolean[N];
        dp[N -1] = true;
        for(int i = N -2; i>=0; i--){
            for(int step = 1;step<=Math.min(N-i-1,nums[i]);step++){
                dp[i] = dp[i]|dp[i+step];
            }
        }
        return dp[0];
    }
    //每到一个位置都维护一下当前能够到达的最远位置，等到能够超过最后一个位置就说明可以到达了
    public static boolean canJump3(int[] nums){
        if(nums==null||nums.length==0) return false;
        int maxCanGo = nums[0];
        for(int cur = 1;cur<=maxCanGo;cur++){
            maxCanGo = Math.max(maxCanGo,cur+nums[cur]);
            if(maxCanGo>=nums.length-1) return true;
        }
        return false;
    }
    public static void main(String[] args) {
        System.out.println(canJump3(new int[]{3,2,1,0,4}));
    }
}
