package waigo.zuoshen.basic;

import org.junit.Test;
import waigo.zuoshen.basic.MyCode.Code05_BSNearLeft;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-08 13:30
 */
public class TestCode05_BSNearLeft {
    @Test
    public void testEqlAndCopyAry() {
        int i = 0;
        for (; i < 10000; i++) {
            int[] nums = Code05_BSNearLeft.generateRandomArray(10, 100);
            int[] copyNums = Code05_BSNearLeft.deepCopyAry(nums);
            if (!Code05_BSNearLeft.isAryEqual(nums, copyNums)) {
                Code05_BSNearLeft.printArray(nums);
                Code05_BSNearLeft.printArray(copyNums);
                break;
            }
        }
        Logger.getGlobal().info(i == 10000 ? "测试通过,NICE" : "测试失败,FUCKING");
    }

    @Test
    public void testQuickSort() {
        int i = 0;
        for (; i < 10000; i++) {
            int[] nums = Code05_BSNearLeft.generateRandomArray(1000, 10000);
            int[] copyNums = Code05_BSNearLeft.deepCopyAry(nums);
            Arrays.sort(nums);
            Code05_BSNearLeft.quickSort(copyNums, 0, copyNums.length - 1);
            if (!Code05_BSNearLeft.isAryEqual(nums, copyNums)) {
                Code05_BSNearLeft.printArray(nums);
                Code05_BSNearLeft.printArray(copyNums);
                break;
            }
        }
        Logger.getGlobal().info(i == 10000 ? "测试通过,NICE" : "测试失败,FUCKING");
    }

    @Test
    public void testNearestLeftIdx() {
        //[0,nums.length-1]
        int maxVal = 100000;
        int[] nums = Code05_BSNearLeft.generateRandomArray(1000, maxVal);
        int[] copyNums = Code05_BSNearLeft.deepCopyAry(nums);
        Arrays.sort(nums);
        Code05_BSNearLeft.quickSort(copyNums, 0, copyNums.length - 1);
        int i = 0;
        for (; i < 10000; i++) {
            int num = (int) (Math.random() * maxVal);
            int test = Code05_BSNearLeft.test(copyNums, num);
            int nea = Code05_BSNearLeft.nearestLeftIdx(nums, num);
            if (test != nea) {
                Logger.getGlobal().info("num:"+num+",test:"+test);
                Code05_BSNearLeft.printArray(nums);
                Logger.getGlobal().info("num:"+num+",nearestLeftIdx:"+nea);
                Code05_BSNearLeft.printArray(copyNums);
                break;
            }
        }
        Logger.getGlobal().info(i == 10000 ? "测试通过,NICE" : "测试失败,FUCKING");
    }
}
