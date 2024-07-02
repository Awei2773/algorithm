package com.waigo.nowcoder.MyCode.Code12_towhere;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * author waigo
 * create 2021-09-14 14:06
 */
public class Main2 {
    /* Write Code Here */
    public int solution(int[] arr) {
        if(arr==null||arr.length==0)
        //升序
        Arrays.sort(arr);
        return process(arr,0,new ArrayList<>());
    }

    private int process(int[] arr, int idx, ArrayList<Integer> paths) {
        if(idx==arr.length){
            int res = 0;
            for(int i = paths.size()-1;i>=0;i--){
                res += paths.get(i) * (i+1);
            }
            return res;
        }
        //1.不要
        int max = process(arr,idx+1,paths);
        //2.要
        paths.add(arr[idx]);
        max = Math.max(process(arr,idx+1,paths),max);
        paths.remove(paths.size()-1);
        return max;
    }

    public static void main(String[] args) {
        System.out.println(new Main2().solution(new int[]{-1,1,4,-9,-8}));
    }
}
