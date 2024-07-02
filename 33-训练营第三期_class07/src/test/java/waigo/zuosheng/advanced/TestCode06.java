package waigo.zuosheng.advanced;

import org.junit.Test;
import waigo.zuosheng.advanced.MyCode.Code06_LongestNoRepeat;
import waigo.zuosheng.advanced.TeaCode.Code06_LongestNoRepeatSubstring;
import waigo.zuosheng.advanced.utils.TestUtils;

/**
 * author waigo
 * create 2021-05-30 10:56
 */
public class TestCode06 {
    @Test
    public void test(){
        new TestUtils().testStrToInt(Code06_LongestNoRepeat::getLNRCSSLen,
                Code06_LongestNoRepeatSubstring::maxUnique);
    }
}
