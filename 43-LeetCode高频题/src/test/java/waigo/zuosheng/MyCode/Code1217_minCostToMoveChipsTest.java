package waigo.zuosheng.MyCode;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

/**
 * author waigo
 * create 2024-07-03 0:48
 *
 * leetcode1217: 对数器
 */
@RunWith(MockitoJUnitRunner.class)
public class Code1217_minCostToMoveChipsTest extends TestCase {
    @Spy
    Code1217_minCostToMoveChips solution;

    @Test
    public void testMinCostToMoveChips() {
        Assert.assertEquals(solution.minCostToMoveChips(new int[]{1, 2, 3}), 1, 0);
        Assert.assertEquals(solution.minCostToMoveChips(new int[]{2, 2, 2, 3, 3}), 2, 0);
        Assert.assertEquals(solution.minCostToMoveChips(new int[]{1, 1000000000}), 1, 0);
        Assert.assertEquals(solution.minCostToMoveChips(new int[]{1000000000}), 0, 0);

        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            int[] chips = generateRandomChips();

            assertEquals(String.format("Fucking, test failure!!!\n\rchips is %s", Arrays.toString(chips)),
                    solution.minCostToMoveChips(chips), solution.minCostToMoveChips2(chips), 0);
        }
    }

    public int[] generateRandomChips() {
        int minLen = 1;
        int maxLen = 100;
        long minValue = 1L;
        long maxValue = 10_000_000_000L;

        int len = (int) (Math.random() * maxLen) + 1;
        int[] randomArray = new int[len];
        for (int i = 0; i < len; i++) {
            randomArray[i] = (int)(Math.random() * maxValue) + 1;
        }

        return randomArray;
    }
}