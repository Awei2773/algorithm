package waigo.zuoshen.basic.MyCode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * author waigo
 * create 2021-03-02 19:57
 */
/*
一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲。
给你每一个项目开始的时间和结束的时间
你来安排宣讲的日程，要求会议室进行的宣讲的场次最多。
返回最多的宣讲场次。
*/
public class Code04_BestArrange {
    //宣讲类
    private static class Speech {
        private int begin;
        private int end;

        public Speech(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        public String toString() {
            return "Speech{" + "begin=" + begin + ", end=" + end + '}';
        }
    }

    private static int getMaxSpeechCount_fr(Speech[] speechs) {
        if (speechs == null || speechs.length == 0) return 0;
        return process(speechs, 0, 0);
    }

    /**
     * 返回从index开始能够最多完成的宣讲场次
     * 思路：暴力递归，穷举所有的安排情况，每个会议都有演讲和不讲两个选择，选最大值
     *
     * @param speechs             演讲数组
     * @param index               0....index都已经决策完了，不需要管
     * @param meetingRoomFreeTime 什么时候会议室可用
     * @return
     */
    private static int process(Speech[] speechs, int index, int meetingRoomFreeTime) {
        if (index == speechs.length) return 0;
        //讲,如果会议室有空时间在会议开始时间之前才能开始这个会议
        int count1 = Integer.MIN_VALUE;
        int begin = speechs[index].begin;
        if(meetingRoomFreeTime<= begin){
            int end = speechs[index].end;
            count1 = 1 + process(speechs, index + 1, end);
        }
        //不讲
        int count2 = process(speechs, index + 1, meetingRoomFreeTime);
        return Math.max(count1, count2);
    }
    private static Speech[] generalRandomSpeechs(int maxSize,int maxSpeechTime){
        int size = (int) (Math.random()*maxSize)+1;//1~maxSize
        Speech[] speeches = new Speech[size];
        for(int i = 0;i < size;i++){
            int time1 = (int) (Math.random()*maxSpeechTime);
            int time2 = (int) (Math.random()*maxSpeechTime);
            if(time1==time2){
                speeches[i] = new Speech(time1,time1+3);
            }else{
                speeches[i] = new Speech(Math.min(time1,time2),Math.max(time1,time2));
            }

        }
        return speeches;
    }
    //贪心策略：结束时间早的先做，晚的后做，使用小根堆按end建堆
    //思路，一开始选一个为了能尽可能多的安排，那么肯定是最快结束的最好，这样能够最快的去安排下一个
    public static int getMaxSpeechCount_tx(Speech[] speechs){
        if (speechs == null || speechs.length == 0) return 0;
        PriorityQueue<Speech> smallHeap = new PriorityQueue<>(Comparator.comparingInt(o->o.end));
        smallHeap.addAll(Arrays.asList(speechs));
        int meetingRoomFreeTime = 0;
        int count = 0;
        while (!smallHeap.isEmpty()){
            Speech heapTop = smallHeap.poll();
            if(meetingRoomFreeTime<=heapTop.begin){
                count++;
                meetingRoomFreeTime = heapTop.end;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int maxSize = 10,maxSpeechTimes = 20,testTimes = 1000000;
        int i = 0;
        for(;i<testTimes;i++){
            Speech[] speeches = generalRandomSpeechs(maxSize, maxSpeechTimes);
            Arrays.sort(speeches, Comparator.comparingInt(o -> o.begin));
            if(getMaxSpeechCount_fr(speeches)!=getMaxSpeechCount_tx(speeches)) break;
        }
        System.out.println(i==testTimes?"finish!!!":"fucking");
    }
}
