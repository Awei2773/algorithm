package waigo.zuoshen.basic;

import org.junit.Test;
import waigo.zuoshen.basic.MyCode.Pratice;

import java.util.ArrayList;

/**
 * author waigo
 * create 2021-02-07 22:07
 */
public class TestPratice {
    @Test
    public void testTitle1(){
        Pratice.title1(3,4);
        Pratice.title2(new int[]{1,3,4,3,2,3,1,4,2});
        Pratice.title3(12);
        Pratice.title4(new int[]{1,3,4,1,3,4,5,6,5,5,4,5});
        Pratice.title5(new int[]{1,2,4,4,3,3,2,2},4);
//        Pratice.title6(new int[]{1,2,3,3,5,6},4);、

    }
}
