package com.waigo.nowcoder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * author waigo
 * create 2021-08-10 0:17
 */

public class Solution {
    public ArrayList<ArrayList<Integer>> permuteUnique(int[] num) {
        //全排列的思路：将每个位置进行固定，全排列的复杂的为O(N^N)，所以数据量肯定不会太大
        //基于全排列的思路：两种做法1，基于辅助数组2.基于交换
        //用set进行去重，必要的
        Set<String> set = new HashSet<>();
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if(num==null||num.length<=0) return res;
        int N = num.length;
        char[] path = new char[N];
        getPermuteUnique(num,set,path,0,res,N);
        return res;
    }
    public void getPermuteUnique(int[] num,Set<String> set,
                                 char[] path,int cur,
                                 ArrayList<ArrayList<Integer>> res,int N){
        if(cur==N){
            String truPath = new String(path);
            if(!set.contains(truPath)){
                ArrayList<Integer> path2 = new ArrayList<>(N);
                for(int d:path){
                    if(d==65535) d = -1;
                    path2.add(d);
                }
                set.add(truPath);
                res.add(path2);
            }
            return;
        }
        for(int i = cur;i<N;i++){
            //现在需要确定i位置的值
            if(i!=cur&&num[i]==num[cur]) continue;
            swap(num,i,cur);
            path[cur] = (char) num[cur];
            getPermuteUnique(num,set,path,cur+1,res,num.length);
            swap(num,i,cur);//轨迹擦除
        }
    }
    public void swap(int[] num,int a,int b){
        if(a!=b){
            num[a]^=num[b];
            num[b]^=num[a];
            num[a]^=num[b];
        }
    }
}
