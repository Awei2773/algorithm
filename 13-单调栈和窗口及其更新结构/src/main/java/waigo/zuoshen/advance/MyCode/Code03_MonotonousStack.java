package waigo.zuoshen.advance.MyCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-03-08 16:16
 */
//Monotonous adj.单调的  [məˈnɒtənəs]
public class Code03_MonotonousStack {

    public static int[][] getNearLessIdxNoReapt(int[] arr){
        if(arr==null||arr.length==0) return new int[][]{};
        Stack<Integer> stack = new Stack<>();//单调栈从小到大
        int[][] res = new int[arr.length][2];
        for(int i = 0;i < arr.length;i++){
            //维护单调栈,记得栈中存的是下标而不是值
            while (!stack.isEmpty()&&arr[stack.peek()]>arr[i]){
                Integer curIdx = stack.pop();
                res[curIdx][0] = stack.isEmpty()?-1:stack.peek();
                res[curIdx][1] = i;
            }
            stack.push(i);
        }
        while (!stack.isEmpty()){
            Integer curIdx = stack.pop();
            res[curIdx][0] = stack.isEmpty()?-1:stack.peek();
            res[curIdx][1] = -1;
        }
        return res;
    }
    public static int[][] getNearLessIdx(int[] arr){
        if(arr==null||arr.length==0) return new int[][]{};
        int[][] res = new int[arr.length][2];
        Stack<List<Integer>> stack = new Stack<>();
        for(int i = 0;i < arr.length;i++){
            while (!stack.isEmpty()&&arr[stack.peek().get(0)]>arr[i]){
                List<Integer> curIdxList = stack.pop();
                int leftNearLessIdx = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
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
            int leftNearLessIdx = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
            for(Integer curIdx: curIdxList){
                res[curIdx][0] = leftNearLessIdx;
                res[curIdx][1] = -1;
            }
        }
        return res;
    }
    //for test
    public static int[][] rightWay(int[] arr){
        if(arr==null||arr.length==0) return new int[][]{};
        int[][] res = new int[arr.length][2];//每个下标[0]存的是左边离它最近比它小的位置
        for(int i = 0;i < arr.length;i++){
            //左边离它最近最小值位置
            int leftNearLessIdx = -1;
            for(int j = i-1;j >= 0;j--){
                if(arr[j]<arr[i]) {
                    leftNearLessIdx = j;
                    break;
                }
            }
            //右边离它最近最小值位置
            int rightNearLessIdx = -1;
            for(int j = i+1;j<arr.length;j++){
                if(arr[j]<arr[i]){
                    rightNearLessIdx = j;
                    break;
                }
            }
            res[i][0] = leftNearLessIdx;
            res[i][1] = rightNearLessIdx;
        }
        return res;
    }
    //for test
    public static int[] generalRandomArrayNoReapt(int maxLen){
        int size = (int) (Math.random()*maxLen)+1;
        int[] ary = new int[size];
        for(int i = 0;i < size;i++){
            ary[i] = i;
        }
        for(int i = 0;i < size;i++){
            int swapIdx = (int) (Math.random()*size);
            int temp = ary[swapIdx];
            ary[swapIdx] = ary[i];
            ary[i] = temp;
        }
        return ary;
    }
    //for test
    public static boolean isEqlAry(int[][] a,int[][] b){
        if(a==b) return true;
        if(a==null||b==null||a.length!=b.length||a[0].length!=b[0].length) return false;
        for(int i = 0;i < a.length;i++){
            for(int j = 0;j < a[0].length;j++){
                if(a[i][j]!=b[i][j]) return false;
            }
        }
        return true;
    }
    //for test
    public static void printAry(int[][] a){
        if(a==null) return;
        StringBuilder builder = new StringBuilder("[");
        for(int i = 0;i < a.length;i++){
            builder.append(Arrays.toString(a[i])).append(",");
        }
        if(a.length>0){
            builder.deleteCharAt(builder.length()-1);
        }
        builder.append("]");
        Logger.getGlobal().info(builder.toString());
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
        int testTimes = 1000000,maxLen = 10,maxVal = 10;
        int i = 0;
        for(;i<testTimes;i++){
            int[] ary = generalRandomAry(maxLen,maxVal);
            int[][] res1 = rightWay(ary);
            int[][] res2 = getNearLessIdx(ary);
            if(!isEqlAry(res1,res2)){
                System.out.println(Arrays.toString(ary));
                printAry(res1);
                printAry(res2);
                break;
            }
        }
        System.out.println(i==testTimes?"finish!!!":"fucking!!!");
    }
}
