package waigo.zuosheng.advanced;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        reverse(-131);

    }

    public int reverse(int x){
        int res = 0;
        int min = Integer.MIN_VALUE/10;
        int max = Integer.MAX_VALUE/10;
        while(x!=0){
            if(res<min||res>max){//常见的防止溢出手段，在溢出前的一个状态做一下判断
                return 0;
            }
            res = res*10+x%10;//负数的余数是负数
            x/=10;
        }
        return res;
    }
}
