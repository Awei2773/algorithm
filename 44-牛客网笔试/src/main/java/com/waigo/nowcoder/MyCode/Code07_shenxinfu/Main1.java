package com.waigo.nowcoder.MyCode.Code07_shenxinfu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * author waigo
 * create 2021-08-31 16:18
 */
public class Main1 {
    //i是下标 nums[i]>0
    //对i找到其最近的两个位置x,y，满足x<i且nums[x]<nums[i],y>i且nums[y]>nums[i]
    //单调栈的概念题
    //输出结果是nums[y]-nums[x]右-左,找不到x,y则认为nums[x]=0,nums[y]=0
    //注意元素可能重复[9,3,3,6]
    public int[] nearestDiff (int[] nums) {

        if(nums==null||nums.length==0) return new int[]{};
        int[][] lMXRB = new int[nums.length][2];//lMXRB[i][0]：左边小，lMXRB[i][1]：右边大
        //栈中存的都是下标
        Stack<List<Integer>> stack = new Stack<>();
        //栈是一个从下到上递减的单调栈，栈中压的是同值list
        //1.找出每个i位置右边最近比自己大的
        //每个数出现的时候从栈中弹出比当前小的，此时当前数就是弹出数右边最近最大的
        int N = nums.length;
        int cur;
        for(int i = 0;i<N;i++){
            cur = nums[i];
            while(!stack.isEmpty()&&nums[stack.peek().get(0)]<cur){
                List<Integer> top = stack.pop();
                for(int idx:top){
                    lMXRB[idx][1] = cur;
                }
            }
            if(stack.isEmpty()||nums[stack.peek().get(0)]!=cur){
                stack.push(new ArrayList<>());
            }
            stack.peek().add(i);
        }
        //栈中都清空，已经没有右边比自己大的了，所以都是0
        stack.clear();

        //栈是一个从下到上递增的单调栈，栈中压的是同值list
        //2.找出每个i位置左边最近比自己小的
        //每个数出现的时候从栈中弹出比当前大的，此时压着的就是比自己小的
        for(int i = 0;i<N;i++){
            cur = nums[i];
            while(!stack.isEmpty()&&nums[stack.peek().get(0)]>cur){
                List<Integer> top = stack.pop();
                for(int idx:top){
                    lMXRB[idx][0] = stack.isEmpty()?0:nums[stack.peek().get(0)];
                }
            }
            if(stack.isEmpty()||nums[stack.peek().get(0)]!=cur){
                stack.push(new ArrayList<>());
            }
            stack.peek().add(i);
        }
        //清出来，此时和上面不同，可能还有压着有答案的
        while(!stack.isEmpty()){
            List<Integer> top = stack.pop();
            for(int idx:top){
                lMXRB[idx][0] = stack.isEmpty()?0:nums[stack.peek().get(0)];
            }
        }
        //计算结果
        int[] res = new int[N];
        for(int i = 0;i<N;i++){
            res[i] = lMXRB[i][1] - lMXRB[i][0];
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Main1().nearestDiff(new int[]{9,3,3,6})));
    }
}
