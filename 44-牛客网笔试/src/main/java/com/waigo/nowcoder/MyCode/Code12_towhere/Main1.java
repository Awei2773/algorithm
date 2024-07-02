package com.waigo.nowcoder.MyCode.Code12_towhere;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * author waigo
 * create 2021-09-14 13:16
 */
class Solution2 {
    /* Write Code Here */
    public String solution(int[] d) {
        if(d==null||d.length==0) return "";
        //降序排序
        int N = d.length;
        Integer[] n = new Integer[N];
        for(int i = 0;i<N;i++){
            n[i] = d[i];
        }
        Arrays.sort(n,(o1,o2)->o2-o1);
        //想要整除3，那各个位加起来要是3的倍数
        int max = process(n,0,0,new ArrayList<>());
        return max==0?"":(max+"");
    }

    public int process(Integer[] d,int idx,int pre,ArrayList<Integer> preList){
        if(idx==d.length){
            if(pre!=0&&pre%3==0){
                StringBuilder builder = new StringBuilder();
                for(int path:preList){
                    builder.append(path);
                }
                return Integer.valueOf(builder.toString());
            }
            return Integer.MIN_VALUE;
        }
        //当前位要还是不要
        //1.不要
        int max = process(d,idx+1,pre,preList);
        //2.要
        preList.add(d[idx]);
        max = Math.max(process(d,idx+1,pre+d[idx],preList),max);
        //轨迹擦除
        preList.remove(preList.size()-1);
        return max;
    }
}

public class Main1 {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String res;

        int _d_size = 0;
        _d_size = Integer.parseInt(in.nextLine().trim());
        int[] _d = new int[_d_size];
        int _d_item;
        for(int _d_i = 0; _d_i < _d_size; _d_i++) {
            _d_item = Integer.parseInt(in.nextLine().trim());
            _d[_d_i] = _d_item;
        }

        res = new Solution2().solution(_d);
        System.out.println(res);
    }
}