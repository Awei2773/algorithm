package com.waigo.nowcoder.MyCode.Code11_58;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * author waigo
 * create 2021-09-11 20:25
 */
public class Main {
    public static int[] find (int[] arg) {
        if(arg==null||arg.length==0) return new int[]{};
        //直接用hashmap就行了
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int num:arg){
            map.put(num,map.getOrDefault(num,0)+1);
        }
        //用set找到所有为频次为1的
        Set<Integer> set = new HashSet<>();
        for(Map.Entry<Integer,Integer> entry:map.entrySet()){
            if(entry.getValue()==1){
                set.add(entry.getValue());
            }
        }
        //遍历一遍，将一次的抓出来
        Set<Integer> isFound = new HashSet<>();
        int[] res = new int[set.size()];
        int w = 0;
        for(int num:arg){
            if(set.contains(num)&&!isFound.contains(num)){
                res[w++] = num;
                isFound.add(num);
            }
        }
        return res;
    }
    public static int getTeams (int[] heros) {
        //每个位置出现的英雄个数给找出来，乘一下就是答案
        int[] heroFinds = new int[5];
        for(int hero:heros){
            heroFinds[hero] = heroFinds[hero]+1;
        }
        int res = 1;
        for(int find:heroFinds){
            res*=find;
        }
        return res;
    }
    public static void main(String[] args) {
//        int[] ints = find(new int[]{1, 1, 4, 6, 7, 7, 3});
        int teams = getTeams(new int[]{1, 3, 2, 4, 0, 0});
    }
}
