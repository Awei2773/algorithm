package waigo.zuosheng.advanced;

import org.junit.Test;
import waigo.zuosheng.advanced.MyCode.Code01_MinimumSubAryNeedSort;
import waigo.zuosheng.advanced.TeaCode.Code01_MinLengthForSort;
import waigo.zuosheng.advanced.utils.TestUtils;

import java.util.Arrays;

/**
 * author waigo
 * create 2021-05-29 8:31
 */
public class Test_MinimumSubAryNeedSort {
    public int[] generalRandomArray(int maxLen,int maxVal){
        int size = (int) (Math.random()*maxLen)+1;
        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            res[i] = (int) (Math.random()*maxVal-Math.random()*maxVal);
        }
        return res;
    }
    @Test
    public void test(){
        int testTimes = 1000000;
        int i = 0;
        for (; i < testTimes; i++) {
            int maxLen = 100;
            int maxVal = 100;
            int[] arys = generalRandomArray(maxLen, maxVal);
            int resMy = Code01_MinimumSubAryNeedSort.minimumNeedSort(arys);
            int resTea = Code01_MinLengthForSort.getMinLength(arys);
            if(resMy!=resTea) {
                System.out.println(Arrays.toString(arys));
                System.out.println(resMy);
                System.out.println(resTea);
                break;
            }
        }
        System.out.println(i==testTimes?"finish!!!":"fucking!!!");
    }
    @Test
    public void test1(){
        new TestUtils().testInAryOutInt(Code01_MinLengthForSort::getMinLength,
                Code01_MinimumSubAryNeedSort::minimumNeedSort);
    }
}
