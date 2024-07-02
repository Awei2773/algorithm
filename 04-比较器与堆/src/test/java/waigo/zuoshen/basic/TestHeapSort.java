package waigo.zuoshen.basic;

import org.junit.Test;
import waigo.zuoshen.basic.MyCode.Code04_HeapSort;
import waigo.zuoshen.basic.MyCode.utils.MathUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-13 14:28
 */
public class TestHeapSort {
    @Test
    public void testIsEqlArray(){
        System.out.println(Code04_HeapSort.isEqlArray(new int[0],new int[0]));
    }
    @Test
    public void testHeapify(){
        //----------------0,1,2,3,4,5,6
        int[] testNums = {1,5,7,3,2,6,4};//堆中数据所在数组是按层序遍历形成堆的
        Code04_HeapSort.heapify(testNums,0,testNums.length);

    }
    @Test
    public void testHeapInsert(){
        int[] testNums = {65, -46, -19, -35, -80, -44, 17, -77, -23, -88};
        Code04_HeapSort.heapInsert(testNums,testNums.length-1);
    }
    @Test
    public void testHeapSort(){
        int maxSize = 10,maxValue = 100,times = 1000000;
        int i = 0;
        for(;i < times;i++){
            int[] nums = MathUtil.generalRandomArray(maxSize, maxValue);
            int[] cpyNums = Code04_HeapSort.copyArray(nums);
            Code04_HeapSort.heapSort(nums);
            Arrays.sort(cpyNums);
            if(!Code04_HeapSort.isEqlArray(nums,cpyNums)){
                Logger.getGlobal().info(Arrays.toString(nums));
                Logger.getGlobal().info(Arrays.toString(cpyNums));
                break;
            }
        }
        Logger.getGlobal().info(i == times?"finish":"fucking");
    }
}
