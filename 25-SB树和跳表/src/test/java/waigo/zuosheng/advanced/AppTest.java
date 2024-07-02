package waigo.zuosheng.advanced;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
        Set<Boss> boss = new HashSet<>();
        boss.add(new Boss(2,3));
        boss.add(new Boss(2,4));
        boss.add(new Boss(2,5));
        System.out.println(boss.contains(new Boss(2,6)));
    }
    public static class Boss{
        int row;
        int col;
        public Boss(int row,int col){
            this.row = row;
            this.col = col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row,col);
        }

        @Override
        public boolean equals(Object obj) {
            return this.hashCode()==obj.hashCode();
        }
    }
}
