package com.waigo.nowcoder.MyCode.Code09_tianronxin;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

/**
 * author waigo
 * create 2021-09-04 18:52
 */
public class Main1 {
    public static final HashMap<Integer,Character> map = new HashMap<>();
    static{
        map.put(10,'A');
        map.put(11,'B');
        map.put(12,'C');
        map.put(13,'D');
        map.put(14,'E');
        map.put(15,'F');
    }
    public String solve (int M, int N) {
        if(M<N) return M+"";
        StringBuilder builder = new StringBuilder();
        //余数入栈，到时候要倒过来
        Stack<Integer> stack = new Stack<>();
        while(M!=0){
            stack.push(M%N);
            M/=N;
        }
        while(!stack.isEmpty()){
            int cur = stack.pop();
            if(cur<10){
                builder.append(cur);
            }else{
                builder.append(map.get(cur));
            }
        }
        return builder.toString();
    }
    public static void main(String[] args) {

    }
}
