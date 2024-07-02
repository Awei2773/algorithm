package waigo.zuoshen.advance.MyCode;

import java.util.*;

/**
 * author waigo
 * create 2021-03-08 17:25
 */
/*
给定一个只包含正数的数组arr，arr中任何一个子数组sub，
一定都可以算出(sub累加和 )* (sub中的最小值)是什么，
那么所有子数组中，这个值最大是多少？

思路：为了使得(sub累加和)* (sub中的最小值)最大，那么只需要？
1）确定最小值是什么，肯定是某个位置上的值
2）找到这个最小值所在子数组中累加和最大那个，那肯定就是这个最小值左边最近比它要小的位置L，和右边最近比它要小的位置R
中间所夹元素的和(因为都是正数)，毕竟再大一点囊括了一个比它小的值它就不是最小值了，所以可以使用单调栈得到左右离它最近最小的位置
*/
public class Code04_AllTimesMinToMax {
    public static int getMax(int[] arr){
        if(arr==null||arr.length==0) return 0;
        int[][] nearLessIdxAry = getNearLessIdx(arr);
        int[] prefixSumAry = prefixSumAry(arr);
        int max = 0;
        for(int i = 0;i<arr.length;i++){
            int left = nearLessIdxAry[i][0];
            int right = nearLessIdxAry[i][1];
            right = right == -1?arr.length:right;
            max = Math.max((prefixSumAry[right-1]-prefixSumAry[left+1]+arr[left+1])*arr[i],max);
        }
        return max;
    }

    public static int[][] getNearLessIdx(int[] arr){
        Stack<List<Integer>> stack = new Stack<>();
        int[][] res = new int[arr.length][2];
        for(int i = 0;i < arr.length;i++){
            //栈中  底-->顶 小-->大
            while (!stack.isEmpty()&&arr[i]<arr[stack.peek().get(0)]){
                List<Integer> curIdxList = stack.pop();
                int leftNearLessIdx = stack.isEmpty()?-1:stack.peek().get(stack.peek().size()-1);
                for(Integer curIdx:curIdxList){
                    res[curIdx][0] = leftNearLessIdx;
                    res[curIdx][1] = i;
                }
            }
            if(stack.isEmpty()||arr[stack.peek().get(0)]!=arr[i]){
                ArrayList<Integer> curIdxList = new ArrayList<>();
                curIdxList.add(i);
                stack.push(curIdxList);
            }else{
                stack.peek().add(i);
            }
        }
        while (!stack.isEmpty()){
            List<Integer> curIdxList = stack.pop();
            int leftNearLessIdx = stack.isEmpty()?-1:stack.peek().get(stack.peek().size()-1);
            for(Integer curIdx:curIdxList){
                res[curIdx][0] = leftNearLessIdx;
                res[curIdx][1] = -1;
            }
        }
        return  res;
    }

    //前缀和数组，方便求数组某个区间的和
    public static int[] prefixSumAry(int[] arr){
        int[] ary  = new int[arr.length];
        ary[0] = arr[0];
        for(int i = 1;i<arr.length;i++){
            ary[i] = arr[i]+ary[i-1];
        }
        return ary;
    }

    //for test
    public static int rightWay(int[] arr){
        if(arr==null||arr.length==0) return 0;
        //找出所有的子数组，然后计算这个值，抓出最大那个
        int max = 0;
        int[] prefixSumAry = prefixSumAry(arr);
        for(int begin = 0;begin < arr.length;begin++){
            for(int end = arr.length-1;end>=begin;end--){
                //begin...end就是一个子数组
                int subSum = prefixSumAry[end] - prefixSumAry[begin] + arr[begin];
                int min = arr[begin];
                for(int i = begin+1;i<=end;i++){
                    min = Math.min(min,arr[i]);
                }
                max = Math.max(subSum*min,max);
            }
        }
        return max;
    }
    //for test
    public static int[] generalRandomAry(int maxLen,int maxVal){
        int size = (int) (Math.random()*maxLen)+1;
        int[] ary = new int[size];
        for(int i = 0;i < size;i++){
            ary[i] = (int) (Math.random()*maxVal);
        }
        return ary;
    }

    public static void main(String[] args) {
        int testTimes = 1000000,maxLen = 10,maxVal = 100;
        int i = 0;
        for(;i<testTimes;i++){
            int[] arr = generalRandomAry(maxLen, maxVal);
            int rightRes = rightWay(arr);
            int res1 = getMax(arr);
            if(rightRes!=res1) break;
        }
        System.out.println(i==testTimes?"finish!!!":"fucking!!!");
    }
}
