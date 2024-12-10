package waigo.zuosheng.MyCode;

import org.junit.Assert;
import org.junit.Test;

/**
 * author waigo
 * create 2024-12-05 22:31
 */
public class Test_Code1269_numWays {
   @Test
   public void testNumWays() {
      Code1269_numWays code1269_numWays = new Code1269_numWays();
      Assert.assertEquals(code1269_numWays.numWaysDp(3, 2), code1269_numWays.numWays(3, 2));
      Assert.assertEquals(code1269_numWays.numWaysDp(2, 4), code1269_numWays.numWays(2, 4));
      Assert.assertEquals(code1269_numWays.numWaysDp(4, 2), code1269_numWays.numWays(4, 2));
      Assert.assertEquals(code1269_numWays.numWaysDp(1, 6), code1269_numWays.numWays(1, 6));
      Assert.assertEquals(code1269_numWays.numWaysDp(27, 7), 127784505);



      Assert.assertEquals(code1269_numWays.numWaysDpOptimism1(3, 2), code1269_numWays.numWays(3, 2));
      Assert.assertEquals(code1269_numWays.numWaysDpOptimism1(2, 4), code1269_numWays.numWays(2, 4));
      Assert.assertEquals(code1269_numWays.numWaysDpOptimism1(4, 2), code1269_numWays.numWays(4, 2));
      Assert.assertEquals(code1269_numWays.numWaysDpOptimism1(1, 6), code1269_numWays.numWays(1, 6));
      Assert.assertEquals(code1269_numWays.numWaysDpOptimism1(27, 7), 127784505);


      Assert.assertEquals(code1269_numWays.numWaysDpOptimism2(3, 2), code1269_numWays.numWays(3, 2));
      Assert.assertEquals(code1269_numWays.numWaysDpOptimism2(2, 4), code1269_numWays.numWays(2, 4));
      Assert.assertEquals(code1269_numWays.numWaysDpOptimism2(4, 2), code1269_numWays.numWays(4, 2));
      Assert.assertEquals(code1269_numWays.numWaysDpOptimism2(1, 6), code1269_numWays.numWays(1, 6));
      Assert.assertEquals(code1269_numWays.numWaysDpOptimism2(27, 7), 127784505);
   }
}
