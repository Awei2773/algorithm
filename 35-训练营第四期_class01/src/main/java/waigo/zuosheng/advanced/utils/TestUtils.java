package waigo.zuosheng.advanced.utils;

import waigo.zuosheng.advanced.MyCode.Code02_MostNearlyKSubSum;
import waigo.zuosheng.advanced.MyCode.Code03_MaxSumOfRectangleLessK;
import waigo.zuosheng.advanced.TeaCode.Code02_MaxSubArraySumLessOrEqualK;
import waigo.zuosheng.advanced.TeaCode.Code03_MaxSumofRectangleNoLargerThanK;

import java.util.Arrays;
import java.util.function.Function;

/**
 * author waigo
 * create 2021-05-29 8:48
 */
public class TestUtils {
    int maxLen = 50;
    int maxVal = 50;
    int testTimes = 1000;
    public void testInAryOutInt(Function<int[],Integer> fun1,Function<int[],Integer> fun2){
        int i = 0;
        for (; i < testTimes; i++) {
            int[] ary = generalRandomArray2();
            int[] ary2= Arrays.copyOf(ary,ary.length);
            int res1 = fun1.apply(ary);
            int res2 = fun2.apply(ary2);
            if(res1!=res2){
                System.out.println(Arrays.toString(ary));
                System.out.println(res1);
                System.out.println(res2);
                break;
            }
        }
        System.out.println(i==testTimes?"finish!!!":"fucking!!!");
    }
    public int[] generalRandomArray(){
        int size = (int) (Math.random()*maxLen)+1;
        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            res[i] = (int) (Math.random()*maxVal-Math.random()*maxVal);
        }
        return res;
    }
    //for Code02
    public int[] generalRandomArray2(){
        int size = (int) (Math.random()*maxLen)+1;
        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            res[i] = (int) (Math.random()*maxVal)+1;
        }
        int[] dest = new int[res.length+1];
        dest[0] = 1;
        System.arraycopy(res,0,dest,1,res.length);
        return dest;
    }
    //general String
    public String generalRandomStr(){
        int size = (int) (Math.random()*maxLen)+1;
        char[]  res = new char[size];
        for (int i = 0; i < size; i++) {
            res[i] = (char) ((int) (Math.random()*5)+'a');
        }
        return String.valueOf(res);
    }

    public void testStrToStr(Function<String,String> func1,Function<String,String> func2){
        int i = 0;
        for (; i < testTimes; i++) {
            String str = generalRandomStr();
            String res1 = func1.apply(str);
            String res2 = func2.apply(str);
            if(!res1.equals(res2)){
                System.out.println(res1);
                System.out.println(res2);
                break;
            }
        }
        System.out.println(i==testTimes?"finish!!!":"fucking!!!");
    }
    public void testStrToInt(Function<String,Integer> func1,Function<String,Integer> func2){
        int i = 0;
        for (; i < testTimes; i++) {
            String str = generalRandomStr();
            int res1 = func1.apply(str);
            int res2 = func2.apply(str);
            if(res1!=res2){
                System.out.println(str);
                System.out.println(res1);
                System.out.println(res2);
                break;
            }
        }
        System.out.println(i==testTimes?"finish!!!":"fucking!!!");
    }
    /**
     * 输入为int[],int，输出为int
     * for Code02
     */
    public void testAryIntToInt(){
        int i = 0;
        for (; i < testTimes; i++) {
            int[] nums = generalRandomArray();
            int K = (int) (Math.random() * maxVal);
            int res1 = Code02_MostNearlyKSubSum.getMostNearly(nums,K);
            int res2 = Code02_MaxSubArraySumLessOrEqualK.getMaxLessOrEqualK(nums,K);
            if(res1!=res2){
                System.out.println(K);
                System.out.println(Arrays.toString(nums));
                System.out.println(res1);
                System.out.println(res2);
                break;
            }
        }
        System.out.println(i==testTimes?"finish!!!":"fucking!!!");
    }
    /**
     * for Code03
     */
    public int[][] generalMatrix(){
        int len = (int) (Math.random()*maxLen)+1;
        int len1 = (int) (Math.random()*maxLen)+1;
        int[][] matrix = new int[len][len1];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len1; j++) {
                matrix[i][j] = (int) (Math.random()*maxVal-Math.random()*maxVal);
            }
        }
        return matrix;
    }

    public void testCode03(){
        int i = 0;
        for (; i < testTimes; i++) {
            int[][] matrix = generalMatrix();
            int k = (int) (Math.random()*maxVal);
            int res1 = Code03_MaxSumOfRectangleLessK.maxSumSubmatrix(matrix, k);
            int res2 = Code03_MaxSumofRectangleNoLargerThanK.maxSumSubmatrix(matrix, k);
            if(res1!=res2){
                System.out.println(k);
                for (int j = 0; j < matrix.length; j++) {
                    System.out.println(Arrays.toString(matrix[j]));
                }
                System.out.println(res1);
                System.out.println(res2);
                break;
            }
        }
        System.out.println(i==testTimes?"finish!!!":"fucking!!!");
    }
}
