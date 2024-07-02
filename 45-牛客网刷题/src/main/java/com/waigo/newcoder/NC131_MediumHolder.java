package com.waigo.newcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.stream.Stream;

/**
 * author waigo
 * create 2021-08-28 13:29
 */
public class NC131_MediumHolder {
    public double[] flowmedian (int[][] operations) {
        MedianHolder holder = new MedianHolder();
        ArrayList<Double> list = new ArrayList<>();
        for(int[] opr:operations){
            if(opr[0]==1){
                holder.insert(opr[1]);
            }else{
                list.add(holder.findMedium());
            }
        }
        double[] res = new double[list.size()];
        for(int i = 0;i<list.size();i++){
            res[i] = list.get(i);
        }
        return res;
    }
    public static class MedianHolder{
        PriorityQueue<Integer> left = new PriorityQueue<>((o1, o2)->o2-o1);
        PriorityQueue<Integer> right = new PriorityQueue<>();
        public void insert(int a){
            //插入
            if(left.isEmpty()||a<=left.peek()){
                left.add(a);
            }else{
                right.add(a);
            }
            //匀
            if(right.size()==left.size()+1){
                left.add(right.poll());
            }else if(left.size()==right.size()+2){
                right.add(left.poll());
            }
        }
        public double findMedium(){
            int n = left.size()+right.size();
            if((n&1)==0){//偶数
                return (left.peek()+right.peek())*1.0/2;
            }else{
                return left.peek()*1.0;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println((double)5);
    }
}
