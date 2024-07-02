package waigo.zuoshen.advanced;

import org.junit.Test;
import waigo.zuoshen.advanced.MyCode.Code03_ExpressionNumber;
import waigo.zuoshen.advanced.TeaCode.Code02_ExpressionNumber;

/**
 * author waigo
 * create 2021-05-21 8:14
 */
public class TestCode03 {
    @Test
    public void testCode03(){
        String a = "1^0&0|1&1^0&0^1|0|1&1";
        System.out.println(Code03_ExpressionNumber.dpWay(a.toCharArray(),false));
        System.out.println(Code02_ExpressionNumber.num1(a,false));
    }
}
