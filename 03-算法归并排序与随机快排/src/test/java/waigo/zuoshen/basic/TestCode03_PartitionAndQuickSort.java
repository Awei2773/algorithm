package waigo.zuoshen.basic;

import com.sun.deploy.util.ArrayUtil;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.junit.Test;
import waigo.zuoshen.basic.MyCode.Code03_PartitionAndQuickSort;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-11 23:33
 */
public class TestCode03_PartitionAndQuickSort {
    @Test
    public void testRandomArray(){
        int maxSize = 10,maxValue = 100;
        for(int i = 0;i < 10;i++)
            Code03_PartitionAndQuickSort.printArray(Code03_PartitionAndQuickSort.generalRandomArray(maxSize,maxValue));
    }
    @Test
    public void testQuickSort1(){
        int maxSize = 10,maxValue = 100,times = 100;
        int i = 0;
        for(;i < times;i++){
            int[] arr = Code03_PartitionAndQuickSort.generalRandomArray(maxSize,maxValue);
            int[] copyArr = Code03_PartitionAndQuickSort.deepCopyArray(arr);
            Code03_PartitionAndQuickSort.quickSort1(arr);
            Arrays.sort(copyArr);
            if(!Code03_PartitionAndQuickSort.isArrayEql(arr,copyArr)){
                Code03_PartitionAndQuickSort.printArray(arr);
                Code03_PartitionAndQuickSort.printArray(copyArr);
                break;
            }
        }
        Logger.getGlobal().info(i==times?"finish":"fucking");
    }
    @Test
    public void testNetherlandsFlag(){
        int maxSize = 10,maxValue = 100;
        int[] arr = Code03_PartitionAndQuickSort.generalRandomArray(maxSize,maxValue);
        int[] ints = Code03_PartitionAndQuickSort.netherlangsFlag(arr, 0, arr.length - 1);
        Code03_PartitionAndQuickSort.printArray(arr);
        Logger.getGlobal().info("smallTop="+ints[0]+",bigLow="+ints[1]);
    }
    @Test
    public void testQuickSort2(){
        int maxSize = 10,maxValue = 100,times = 1070000;
        int i = 0;
        for(;i < times;i++){
            int[] arr = Code03_PartitionAndQuickSort.generalRandomArray(maxSize,maxValue);
            int[] copyArr = Code03_PartitionAndQuickSort.deepCopyArray(arr);
            Code03_PartitionAndQuickSort.quickSort2(arr);
            Arrays.sort(copyArr);
            if(i == 80){
                Code03_PartitionAndQuickSort.printArray(arr);
                Code03_PartitionAndQuickSort.printArray(copyArr);
            }
            if(!Code03_PartitionAndQuickSort.isArrayEql(arr,copyArr)){
                Code03_PartitionAndQuickSort.printArray(arr);
                Code03_PartitionAndQuickSort.printArray(copyArr);
                break;
            }
        }
        Logger.getGlobal().info(i==times?"finish":"fucking");
    }
    @Test
    public void testQuickSort3(){
        int maxSize = 10,maxValue = 100,times = 100000;
        int i = 0;
        for(;i < times;i++){
            int[] arr = Code03_PartitionAndQuickSort.generalRandomArray(maxSize,maxValue);
            int[] copyArr = Code03_PartitionAndQuickSort.deepCopyArray(arr);
            Code03_PartitionAndQuickSort.quickSort3(arr);
            Arrays.sort(copyArr);
            if(i == 80){
                Code03_PartitionAndQuickSort.printArray(arr);
                Code03_PartitionAndQuickSort.printArray(copyArr);
            }
            if(!Code03_PartitionAndQuickSort.isArrayEql(arr,copyArr)){
                Code03_PartitionAndQuickSort.printArray(arr);
                Code03_PartitionAndQuickSort.printArray(copyArr);
                break;
            }
        }
        Logger.getGlobal().info(i==times?"finish":"fucking");
    }
}
