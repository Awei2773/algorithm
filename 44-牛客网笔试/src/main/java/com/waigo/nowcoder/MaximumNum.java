package com.waigo.nowcoder;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * author waigo
 * create 2021-08-13 15:20
 */
public class MaximumNum {
    public static class Comp implements Comparator<String> {
        public int compare(String o1,String o2){
            String r1 = o1 + o2;
            String r2 = o2 + o1;
            for(int i = 0;i<r1.length();i++){
                int i1 = r1.charAt(i) - '0';
                int i2 = r2.charAt(i) - '0';
                if(i1 > i2){
                    return -1;
                }
            }
            return 1;
        }
    }
    public String solve (int[] nums) {
        //贪心，让大数尽可能往前放
        String[] numStrs = new String[nums.length];
        for(int i = 0;i<nums.length;i++){
            numStrs[i] = nums[i]+"";
        }
        Arrays.sort(numStrs,new Comp());
        StringBuilder builder = new StringBuilder();
        for(String numStr:numStrs){
            builder.append(numStr);
        }
        return builder.toString();
    }

    public static void main(String[] args) {
/*//        System.out.println(new MaximumNum().solve(new int[]{30,1}));
        //
        TreeSet<Integer> set = new TreeSet<>();
        set.add(3);
        set.add(4);
        set.add(7);
        set.add(6);
        Integer ceiling = set.ceiling(5);
        System.out.println(ceiling);
        Integer floor = set.floor(5);
        System.out.println(floor);
        SortedSet<Integer> integers = set.subSet(4, 8);
        System.out.println(integers);
        HashSet<Integer> list = new HashSet<>();
        for(int i = 0;i<20077;i++){
            list.add(i);
        }
//        System.out.println(list);

        Hashtable<Object, Object> objectObjectHashtable = new Hashtable<>();
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
//        objectObjectHashtable.put("3",null);
        objectObjectHashMap.put("3",null);
        objectObjectHashMap.put("4",null);
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(1);
        objects.add(2);
        objects.add("A");
        objects.add("B");
        Collections.shuffle(objects);
        System.out.println(objects);*/
        ArrayList<Integer> arrayList = new ArrayList<Integer>() {{
            add(3);
            add(4);
            add(5);
        }};
        Iterator<Integer> iterator = arrayList.iterator();
        while(iterator.hasNext()){
            int i = iterator.next();
            if(i==4){
                iterator.remove();
            }
        }
        System.out.println(arrayList);
    }

}
