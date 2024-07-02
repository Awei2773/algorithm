package waigo.zuoshen.basic;

import org.junit.Test;
import waigo.zuoshen.basic.MyCode.Code02_SmallSum;
import waigo.zuoshen.basic.MyCode.Code01_MergeSort;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-08 18:39
 */
public class TestMergeSort {
    @Test
    public void testMerge(){
        int[] nums = {3,4,5,1,2,3};
        Code01_MergeSort.mergeSort1(nums);
        Logger.getGlobal().info(Arrays.toString(nums));
    }
    @Test
    public void testMergeSort() {
        int i = 0;
        for (; i < 10000; i++) {
            int[] nums = Code01_MergeSort.generateRandomArray(1000, 10000);
            int[] copyNums = Code01_MergeSort.deepCopyAry(nums);
            Arrays.sort(nums);
            Code01_MergeSort.mergeSort1(copyNums);
            if (!Code01_MergeSort.isAryEqual(nums, copyNums)) {
                Code01_MergeSort.printArray(nums);
                Code01_MergeSort.printArray(copyNums);
                break;
            }
        }
        Logger.getGlobal().info(i == 10000 ? "测试通过,NICE" : "测试失败,FUCKING");
    }
}
