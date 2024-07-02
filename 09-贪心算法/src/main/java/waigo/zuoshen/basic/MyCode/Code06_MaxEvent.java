package waigo.zuoshen.basic.MyCode;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * author waigo
 * create 2021-03-24 11:27
 */

/*
给你一个数组 events，其中 events[i] = [startDayi, endDayi] ，表示会议 i 开始于 startDayi ，结束于 endDayi 。
你可以在满足 startDayi <= d <= endDayi 中的任意一天 d 参加会议 i 。注意，一天只能参加一个会议。
请你返回你可以参加的 最大 会议数目。
 */
public class Code06_MaxEvent {
    public static class Meeting {
        private int startDay,endDay;

        public Meeting(int startDay, int endDay) {
            this.startDay = startDay;
            this.endDay = endDay;
        }
    }
    public static int maxEvents(int[][] events) {
        //根据开始时间小根堆
        PriorityQueue<Meeting> smallHeap = new PriorityQueue<>(Comparator.comparingInt(o -> o.startDay));
        for(int i = 0;i < events.length;i++){
            smallHeap.add(new Meeting(events[i][0],events[i][1]));
        }
        int count = 0,startDay = 1;
        PriorityQueue<Meeting> canJoin = new PriorityQueue<>(Comparator.comparingInt(o->o.endDay));
        while (!smallHeap.isEmpty()||!canJoin.isEmpty()){
            while (!smallHeap.isEmpty()&&smallHeap.peek().endDay<startDay){
                smallHeap.poll();
            }
            while (!canJoin.isEmpty()&&canJoin.peek().endDay<startDay){
                canJoin.poll();
            }
            while (!smallHeap.isEmpty()&&smallHeap.peek().startDay<=startDay){
                canJoin.add(smallHeap.poll());
            }
            if(!canJoin.isEmpty()){
                canJoin.poll();
                count++;
            }
            startDay++;
        }
        return count;
    }

    public static int maxEvents2(int[][] events) {
        //根据开始时间排序
        Arrays.sort(events, Comparator.comparingInt(o -> o[0]));
        int count = 0,startDay = 1,i=0;
        PriorityQueue<Integer> canJoin = new PriorityQueue<>(Comparator.comparingInt(o -> o));

        while (i<events.length||!canJoin.isEmpty()){
            while (!canJoin.isEmpty()&&canJoin.peek()<startDay){//可能有些已经超过参加时间了，处理一下
                canJoin.poll();
            }
            while (i<events.length&&events[i][0]<=startDay){//把当前能够参加的会议的结束时间都丢进canJoin中，
                canJoin.add(events[i++][1]);                //当前不能参加的表示startDay还没到，之后可能还有机会参加
            }
            if(!canJoin.isEmpty()){
                canJoin.poll();
                count++;
            }
            startDay++;
        }
        return count;
    }
    public static int maxEvents1(int[][] events) {
        boolean[] isJoin = new boolean[events.length];
        int minDay = events[0][0];
        int maxDay = events[0][1];
        for(int i = 1;i < events.length;i++){
            minDay = Math.min(minDay,events[i][0]);
            maxDay = Math.max(minDay,events[i][1]);
        }
        return process(events,minDay,maxDay,isJoin);
    }
    //暴力递归
    private static int process(int[][] events, int curDay, int maxDay, boolean[] isJoin) {
        if(curDay>maxDay) return 0;
        int max = 0;
        for(int i = 0;i<events.length;i++){
            if(!isJoin[i]&&curDay>=events[i][0]&&curDay<=events[i][1]){
                //这个会议可以参加
                isJoin[i] = true;
                max = Math.max(1+process(events,curDay+1,maxDay,isJoin),max);
                isJoin[i] = false;
            }
            max = Math.max(process(events,curDay+1,maxDay,isJoin),max);
        }
        return Math.max(process(events,curDay+1,maxDay,isJoin),max);//max找到的是这天所有选择下的最多会议安排
    }
    private static int[][] generalRandomEvents(int endDay,int maxLen){
        int len = (int) (Math.random()*maxLen)+1;
        int[][] events = new int[len][2];
        for(int i = 0;i < len;i++){
            int day1 = (int) (Math.random()*endDay)+1;
            int day2 = (int) (Math.random()*endDay)+1;
            events[i][0] = Math.min(day1,day2);
            events[i][1] = Math.max(day1,day2);
        }
        return events;
    }

    public static void main(String[] args) {
//        int[][] events = new int[][]{{1,2},{2,3},{3,4}};
        int endDay = 5,maxLen = 5;
        int[][] events = generalRandomEvents(endDay, maxLen);
        for(int[] i:events){
            System.out.println(Arrays.toString(i));
        }
        System.out.println(maxEvents2(events));

    }
}
