package com.waigo.nowcoder.MyCode.Code07_shenxinfu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * author waigo
 * create 2021-08-31 16:49
 */
public class Main2 {

    private static final int SEGMENT = 4;//地址四段

    //1.解法类似天际线问题，就是先将IP地址进行一个排序，然后开始遍历，全程维护start,end
    //一旦出现断层就收集一个答案。
    //具体实现
    //1.先将IP地址给分装成对象
    //2.确定对象的比较策略，就是IP地址如何比较谁在前，谁在后的问题
    //很明显，从前到后，进行比较IP地址的四个部分，出现小了，那小的那个在前面
    //将每部分都以数字的方式存储，简单化比较的复杂度
    public static class IPv4Address implements Comparable<IPv4Address>{
        int[] address = new int[SEGMENT];
        public IPv4Address(String ip){
            String[] split = ip.split("\\.");
            for(int i = 0;i<4;i++){
                address[i] = Integer.valueOf(split[i]);
            }
        }

        @Override
        public int compareTo(IPv4Address o) {
            for(int i = 0;i<SEGMENT-1;i++){
                if(this.address[i]<o.address[i]){
                    return -2;//o1在前
                }else if(this.address[i]>o.address[i]){
                    return 2;//o2在前
                }
            }
            //前三位都相同，则返回最后一位的差值,根据最后一位返回-1可以判断是否连续
            return this.address[SEGMENT-1]-o.address[SEGMENT-1];
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for(int i = 0;i<SEGMENT;i++){
                builder.append(address[i]).append(".");
            }
            builder.deleteCharAt(builder.length()-1);
            return builder.toString();
        }
    }
    public static class AddressZone{
        IPv4Address start;
        IPv4Address end;
        public AddressZone(String ipZone){
            if(ipZone.contains("-")){
                String[] split = ipZone.split("-");
                start = new IPv4Address(split[0]);
                end = new IPv4Address(split[1]);
            }else{
                start = new IPv4Address(ipZone);
                end = start;
            }
        }

        public AddressZone(IPv4Address start, IPv4Address end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            if(start==end||start.compareTo(end)==0){
                return start.toString();
            }
            return start.toString()+"-"+end.toString();
        }
    }
    public static class ZoneComparator implements Comparator<AddressZone>{

        @Override
        public int compare(AddressZone o1, AddressZone o2) {
            return o1.start.compareTo(o2.start);
        }
    }
    public ArrayList<String> merge(ArrayList<String> input) {
        if(input==null||input.size()==0) return new ArrayList<>();
        int size = input.size();
        AddressZone[] zones = new AddressZone[size];
        for(int i = 0; i< size; i++){
            zones[i] = new AddressZone(input.get(i));
        }
        //排序
        Arrays.sort(zones,new ZoneComparator());
        ArrayList<String> res = new ArrayList<>();
        IPv4Address start = zones[0].start;
        IPv4Address end = zones[0].end;
        for(int i = 1;i<size;i++){
            AddressZone curZone = zones[i];
            int gap = end.compareTo(curZone.start);
            if(Math.abs(gap)!=2){
                //最后一位判断
                if(gap==-1){
                    //连续
                    end = curZone.end;
                }else if(gap<0){
                    //收集
                    res.add(new AddressZone(start,end).toString());
                    //修改start,end
                    start = curZone.start;
                    end = curZone.end;
                }else{
                    //改end,只有新end要大才改
                    int endGap = end.compareTo(curZone.end);
                    end = endGap<0?curZone.end:end;
                }
            }else{
                //前三位就能够确定了，不存在连续的情况
                if(gap<0){
                    //收集
                    res.add(new AddressZone(start,end).toString());
                    //修改start,end
                    start = curZone.start;
                    end = curZone.end;
                }else{
                    //改end,只有新end要大才改
                    int endGap = end.compareTo(curZone.end);
                    end = endGap<0?curZone.end:end;
                }
            }
        }
        //最后没有触发收集了，手动收集一下最后一次
        res.add(new AddressZone(start,end).toString());
        return res;
    }

//    public static void main(String[] args) {
        /*List<String> strings = Arrays.asList("192.168.0.1", "192.168.0.12-192.168.0.15", "192.168.0.2", "192.168.0.7-192.168.0.9", "192.168.0.11", "192.168.0.3-192.168.0.5", "0.0.0.0-255.255.255.255","192.168.0.16", "192.168.0.100");
        ArrayList<String> merge = new Main2().merge(new ArrayList<>(strings));
        System.out.println(merge);
        Thread thread = new Thread();
        thread.suspend();*/

//    }
}
enum AccountType
{
    SAVING, FIXED, CURRENT;
    private AccountType()
    {
        System.out.println("It is a account type");
    }
}
class EnumOne
{
    public static void main(String[]args)
    {
        System.out.println(AccountType.FIXED);
    }
}