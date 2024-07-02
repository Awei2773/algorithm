package waigo.zuoshen.basic;

import org.junit.Test;
import waigo.zuoshen.basic.MyCode.Code02_SmallSum;
import waigo.zuoshen.basic.MyCode.Code01_MergeSort;

import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-08 20:23
 */
public class TestCode02_SmallSum {
    @Test
    public void testMergeSort() {
        int[] nums = {1, 4, 2, 3, 5, 3, 2, 5, 6, 4};
        Code02_SmallSum.mergeSort(nums,0,nums.length-1);
        Code01_MergeSort.printArray(nums);
    }
    @Test
    public void testSmallSum(){
        int i = 0;
        for(;i < 10000;i++){
            int[] nums = Code01_MergeSort.generateRandomArray(1000,1000);
            int[] copyNums = Code01_MergeSort.deepCopyAry(nums);
            if(Code02_SmallSum.getSmallSum(nums)!=Code02_SmallSum.test(copyNums)){
                Code01_MergeSort.printArray(nums);
                Code01_MergeSort.printArray(copyNums);
                break;
            }

        }
        Logger.getGlobal().info(i == 10000 ? "测试通过,NICE" : "测试失败,FUCKING");
    }
}
