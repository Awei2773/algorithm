package waigo.zuosheng.MyCode;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * author waigo
 * create 2024-07-04 23:38
 */
@RunWith(MockitoJUnitRunner.class)
public class Code241_diffWaysToComputeTest extends TestCase {
    @Spy
    Code241_diffWaysToCompute solution;

    @Test
    public void testDiffWaysToCompute() {
        List<Integer> results = solution.diffWaysToCompute("2-1-1");
        results.sort(Comparator.comparingInt(o -> o));
        Assert.assertEquals("[0, 2]", results.toString());

        results = solution.diffWaysToCompute("2*3-4*5");
        results.sort(Comparator.comparingInt(o -> o));
        Assert.assertEquals("[-34, -14, -10, -10, 10]", results.toString());

        results = solution.diffWaysToCompute("2+1+1");
        results.sort(Comparator.comparingInt(o -> o));
        Assert.assertEquals("[4, 4]", results.toString());

        results = solution.diffWaysToComputeMemorySearch("2-1-1");
        results.sort(Comparator.comparingInt(o -> o));
        Assert.assertEquals("[0, 2]", results.toString());

        results = solution.diffWaysToComputeMemorySearch("2*3-4*5");
        results.sort(Comparator.comparingInt(o -> o));
        Assert.assertEquals("[-34, -14, -10, -10, 10]", results.toString());

        results = solution.diffWaysToComputeMemorySearch("2+1+1");
        results.sort(Comparator.comparingInt(o -> o));
        Assert.assertEquals("[4, 4]", results.toString());

        results = solution.diffWaysToComputeDP("2-1-1");
        results.sort(Comparator.comparingInt(o -> o));
        Assert.assertEquals("[0, 2]", results.toString());

        results = solution.diffWaysToComputeDP("2*3-4*5");
        results.sort(Comparator.comparingInt(o -> o));
        Assert.assertEquals("[-34, -14, -10, -10, 10]", results.toString());

        results = solution.diffWaysToComputeDP("2+1+1");
        results.sort(Comparator.comparingInt(o -> o));
        Assert.assertEquals("[4, 4]", results.toString());
    }
}