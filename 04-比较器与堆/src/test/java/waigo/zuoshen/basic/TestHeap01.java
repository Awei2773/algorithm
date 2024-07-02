package waigo.zuoshen.basic;

import org.junit.Test;
import waigo.zuoshen.basic.MyCode.Code02_Heap01;
import waigo.zuoshen.basic.MyCode.utils.MathUtil;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-12 23:06
 */
public class TestHeap01 {
    @Test
    public void testHeapInsert() {
//        Code02_Heap01.MyHeap myHeap = new Code02_Heap01.MyHeap(15);
        Code02_Heap01.TestHeap myHeap = new Code02_Heap01.TestHeap(15);
        myHeap.add(5);
        myHeap.add(12);
        myHeap.add(14);
        myHeap.add(12);
        myHeap.add(16);
        myHeap.add(2);
        myHeap.add(3);
        myHeap.add(13);
        myHeap.add(7);
        while (!myHeap.isEmpty()) {
            Logger.getGlobal().info("the pop value=" + myHeap.pop());
        }
    }

    @Test
    public void testMyHeap() {
        int maxSize = 10, maxValue = 100, times = 1000000;
        int i = 0;
        for (; i < times; i++) {
            int[] array = MathUtil.generalRandomArray(maxSize, maxValue);
            Code02_Heap01.MyHeap myHeap = new Code02_Heap01.MyHeap(array.length);
            Code02_Heap01.TestHeap testHeap = new Code02_Heap01.TestHeap(array.length);
            for (int i1 : array) {
                myHeap.add(i1);
                testHeap.add(i1);
            }
            for (int i1 : array) {
                int myHeapPop = myHeap.pop();
                int testHeapPop = testHeap.pop();
                if (myHeapPop != testHeapPop) {
                    Logger.getGlobal().info(Arrays.toString(array));
                    Logger.getGlobal().info("fucking");
                    return;
                }
            }
        }
        Logger.getGlobal().info("finish");
    }

    @Test
    public void testPow() {
        //102
        int x = 102;
        int d = 3;
        Logger.getGlobal().info((x / ((int) Math.pow(10, d-1))) % 10 + "");
    }
}
