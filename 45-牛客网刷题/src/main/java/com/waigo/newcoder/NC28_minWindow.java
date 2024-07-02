package com.waigo.newcoder;

import javax.swing.tree.TreeNode;
import java.util.*;

/**
 * author waigo
 * create 2021-08-19 16:54
 */
public class NC28_minWindow {
    public String minWindow (String S, String T) {
        if(S==null||T==null||S.length()<T.length()) return "";
        //遍历，假设答案法:每次可能的子串都抓，每次抓1.起点2.长度
        //两个指针start,end;存一个窗口中已有字符
        int start = 0,end = 0;
        char[] tChars = T.toCharArray();
        char[] sChars = S.toCharArray();
        int sN = sChars.length;
        int tN = tChars.length;
        //存一个窗口中已有字符
        HashMap<Character,Integer> wind = new HashMap<>();
        //存T中各个字符个数
        HashMap<Character,Integer> tCharMap = new HashMap<>();
        for(int i = 0;i<tN;i++){
            tCharMap.put(tChars[i],tCharMap.getOrDefault(tChars[i],0)+1);
        }
        //窗口[)
        int minLen = Integer.MAX_VALUE;
        int minStart = 0;
        while(start<sN&&end<=sN){
            int len = end-start;
            boolean isNoFull = noCapAll(wind, tCharMap);
            if(end!=sN&&isNoFull){
                //右边动，进一个
                wind.put(sChars[end],wind.getOrDefault(sChars[end],0)+1);
                end++;
                continue;
            }else if(len<minLen&&!isNoFull){
                //装够了，抓一个答案
                minStart = start;
                minLen = len;
            }
            //左边边界右移
            wind.put(sChars[start],wind.get(sChars[start])-1);
            start++;
        }
        return minLen==Integer.MAX_VALUE?"":new String(sChars,minStart,minLen);
    }
    public boolean noCapAll(HashMap<Character,Integer> wind,HashMap<Character,Integer> tCharMap){
        //遍历tCharMap，看看wind是否满足tCharMap中的所有元素要求的个数
        for(Map.Entry<Character,Integer> entry:tCharMap.entrySet()){
            Character key = entry.getKey();
            int value = entry.getValue();
            if(!wind.containsKey(key)||wind.get(key)<value){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        //
//        "XDOYEZODEYXNZ","XYZ"

        System.out.println(new NC28_minWindow().minWindow("XDOYEZODEYXNZ","EXDZ"));
        Queue<TreeNode> queue = new LinkedList<>();

    }
}
