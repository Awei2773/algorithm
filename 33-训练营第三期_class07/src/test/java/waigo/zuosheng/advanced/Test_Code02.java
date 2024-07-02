package waigo.zuosheng.advanced;

import org.junit.Test;
import waigo.zuosheng.advanced.MyCode.Code02_SmallestUnFormedSum;
import waigo.zuosheng.advanced.utils.TestUtils;

/**
 * author waigo
 * create 2021-05-29 9:34
 */
public class Test_Code02 {
    @Test
    public void test(){
        new TestUtils().testInAryOutInt(Code02_SmallestUnFormedSum::dp,
                Code02_SmallestUnFormedSum::smallestUnFormedSum2);
    }
}
