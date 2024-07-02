package com.waigo.nowcoder.MyCode.Code08_weilaiNIO;

import java.util.HashMap;
import java.util.HashSet;

/**
 * author waigo
 * create 2021-08-31 18:57
 */
public class Main1 {
    //1.用set记一下已经有过的数
    //2.已经有过数对的开始放入set
    public int get_pair_count (int[] arr) {
        if(arr==null||arr.length<2) return 0;
        HashSet<Integer> found = new HashSet<>();
        HashSet<Integer> collected = new HashSet<>();
        for(int a:arr){
            if(found.contains(a-1)){
                collected.add(a-1);//有个a-1开始的数对
            }
            if(found.contains(a+1)){
                collected.add(a);//有个a开始的数对
            }
            found.add(a);
        }
        return collected.size();
    }
}
