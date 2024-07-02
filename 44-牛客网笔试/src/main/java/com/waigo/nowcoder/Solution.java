package com.waigo.nowcoder;

import com.google.common.util.concurrent.RateLimiter;
import javafx.concurrent.ScheduledService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.*;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class Solution{
    public static class TokenLimiter{
        private ArrayBlockingQueue<Integer> blockingQueue;
        private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        private TimeUnit timeUnit;
        private int limit;
        private int period;//间隔
        private int oneAdd;//每次添加
        public TokenLimiter(int limit,int period,TimeUnit timeunit,int oneAdd){
            this.limit = limit;
            this.timeUnit = timeunit;
            this.period = period;
            this.oneAdd = oneAdd;
            blockingQueue = new ArrayBlockingQueue<>(limit);
            addToken();
            start();
        }
        public void addToken(){
            int d = Math.min(limit-blockingQueue.size(),oneAdd);
            for(int i = 0;i<d;i++){
                blockingQueue.offer(1);
            }
        }
        public void start(){
            scheduledExecutorService.scheduleAtFixedRate(
                    this::addToken,period,period,timeUnit
            );
        }
        public boolean tryAcquire(){
            boolean b = blockingQueue.poll()!=null;
            if(!b){
                System.out.println("限流");
            }
            return b;
        }
    }
    private static TokenLimiter tokenLimiter = new TokenLimiter(100,6,TimeUnit.SECONDS,10);
    public void api(){
        if(!tokenLimiter.tryAcquire()) return;
        System.out.println("业务方法");
    }
    public static void main(String[] args){
        /*Solution solution = new Solution();
        while (true){
            Thread.sleep(100);
            solution.api();
        }*/
        System.out.println(Arrays.toString("3333".toCharArray()));
    }

}
