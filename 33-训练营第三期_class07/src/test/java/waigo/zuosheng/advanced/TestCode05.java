package waigo.zuosheng.advanced;

import org.junit.Test;
import waigo.zuosheng.advanced.MyCode.Code05_RemoveDuplicateLetters;
import waigo.zuosheng.advanced.TeaCode.Code05_RemoveDuplicateLettersLessLexi;
import waigo.zuosheng.advanced.utils.TestUtils;

/**
 * author waigo
 * create 2021-05-30 10:29
 */
public class TestCode05 {
    @Test
    public void test(){
        new TestUtils().testStrToStr(Code05_RemoveDuplicateLetters::removeDuplicateLetters1,
                Code05_RemoveDuplicateLettersLessLexi::removeDuplicateLetters1);
    }
}
