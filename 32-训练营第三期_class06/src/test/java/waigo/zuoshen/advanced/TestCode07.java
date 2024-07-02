package waigo.zuoshen.advanced;

import org.junit.Test;
import waigo.zuoshen.advanced.MyCode.Code07_SplitArrayForFour;
import waigo.zuoshen.advanced.TeaCode.Code05_Split4Parts;

/**
 * author waigo
 * create 2021-05-24 11:40
 */
public class TestCode07 {
    public int[] generateRondomArray() {
        int[] res = new int[(int) (Math.random() * 10) + 7];
        for (int i = 0; i < res.length; i++) {
            res[i] = (int) (Math.random() * 10) + 1;
        }
        return res;
    }

    @Test
    public void check() {
        int testTime = 3000000;
        int i = 0;
        for (; i < testTime; i++) {
            int[] arr = generateRondomArray();
            if (Code05_Split4Parts.canSplits1(arr) ^ Code07_SplitArrayForFour.isSplitFour(arr)) {
                System.out.println("Error");
            }
        }

        System.out.println(i==testTime?"finish!!!":"fucking!!!");
    }
}
