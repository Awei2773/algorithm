package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-14 13:56
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 给定一个数组arr，如果有某个数出现次数超过了数组长度的一半，打印这个数，如果没有不打印
 *
 * 给定一个数组arr和整数k，arr长度为N，如果有某些数出现次数超过了N/K，打印这些数，如果没有不打印
 *
 * 解决方法都是一样的，超过一半也就是出现次数超过了N/2，所以最多只有一个
 * 超过了N/K,那么说明最多也就只有K-1个
 *
 * 如果说有某个数出现的次数超过了N/K次，那么他出现的次数减N/K次都不会为0，而出现次数没有超过N/K次的
 * 减N/K次必定会小于0，所以说整个Map存待选值，然后map存满之后就开始PK，一次删去K个不同的值，
 * 最多也就N/K刀全部就会删去，我最后一刀不下，最后剩下留在Map里的肯定是有符合条件和不符合条件的。
 * 为什么会有不符合条件的？
 * 因为符合条件的少于K-1个，所以有些滥竽进来充数了
 */
public class Code07_FindKMajority {
    public static void printKMajor(int[] arr, int K) {
        if(arr==null||arr.length<=K||K<2) return;
        HashMap<Integer, Integer> map = new HashMap<>(K-1);
        for (int cur : arr) {
            if (map.size() < K - 1 || map.containsKey(cur)) {
                map.put(cur, map.getOrDefault(cur, 0) + 1);
            } else {//删去K个
                Set<Map.Entry<Integer, Integer>> entries = map.entrySet();
                Iterator<Map.Entry<Integer, Integer>> iterator = entries.iterator();
                while (iterator.hasNext()){
                    Map.Entry<Integer, Integer> entry = iterator.next();
                    if (entry.getValue() == 1) {
                        iterator.remove();
//                        map.remove(entry.getKey());
                    } else {
                        map.put(entry.getKey(), entry.getValue() - 1);
                    }
                }
            }
        }
        //如果存在N/K，都进Map了
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            entry.setValue(0);
        }
        for (int key : arr) {
            if(map.containsKey(key)){
                map.put(key,map.get(key)+1);
            }
        }
        int keyword = arr.length/K;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if(entry.getValue()>keyword){
                System.out.println(entry.getKey());
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = { 1,1,1,1,1,1,1,3,3,3,8,9};
        int K = 2;
        printKMajor(arr,K);
    }
}
