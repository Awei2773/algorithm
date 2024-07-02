package com.waigo.nowcoder.MyCode.Code05_zoom;

import java.util.*;

/**
 * author waigo
 * create 2021-08-28 17:38
 */
public class Main2 {
    public int[] slideWindow (int[] nums, int windowSize, int step) {
        if(nums==null||nums.length==0) return new int[]{};
        //使用队列维系窗口最值，队列中存的是下标
        //每个数从后入：一次将队列中小于自己的都从右边出队，然后压入自己
        //每次右移都看一下当前淘汰的是否是队头，是就将队头出队
        LinkedList<Integer> queue = new LinkedList<>();
        int L = 0,R = 0;//[)
        int N = nums.length;
        ArrayList<Integer> res = new ArrayList<>();
        while(L<N||R<N){
            if(R-L<windowSize&&R<N){
                enq(R++,nums,queue);
            }else{
                res.add(nums[queue.getFirst()]);
                for(int i = 0;i<step;i++){
                    if(L<N){
                        deq(L++,nums,queue);
                    }
                    if(R<N){
                        enq(R++,nums,queue);
                    }
                }
            }
        }
        int size = res.size();
        int[] r = new int[size];
        for(int i = 0; i< size; i++){
            r[i] = res.get(i);
        }
        return r;
    }
    //血琳琳教训，要么用add,poll,peek，要么用getFirst,pollFirst
    private void deq(int L, int[] nums, LinkedList<Integer> queue) {
        while(!queue.isEmpty()&&queue.getFirst()<=L){
            queue.pollFirst();
        }
    }

    private void enq(int R, int[] nums, LinkedList<Integer> queue) {
        //从后入
        while (!queue.isEmpty()&&nums[queue.getLast()]<=nums[R]){
            queue.pollLast();
        }
        queue.addLast(R);
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Main2().slideWindow(new int[]{1,3},3,1)));
    }
}
