package waigo.zuoshen.basic;

import org.junit.Test;

/**
 * author waigo
 * create 2021-02-27 8:31
 */
public class Test1 {
    @Test
    public void test(){
        System.out.println(Integer.toBinaryString(-2>>>1).length());
        System.out.println(Integer.toBinaryString(-1>>>1));
    }
}
