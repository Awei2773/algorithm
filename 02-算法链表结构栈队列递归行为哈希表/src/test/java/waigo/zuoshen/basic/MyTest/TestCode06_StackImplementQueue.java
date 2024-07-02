package waigo.zuoshen.basic.MyTest;

import org.junit.Test;
import waigo.zuoshen.basic.MyCode.Code06_StackImplementQueue;
import waigo.zuoshen.basic.MyCode.utils.LoggerUtil;

/**
 * author waigo
 * create 2021-02-11 0:49
 */
public class TestCode06_StackImplementQueue {
    @Test
    public void testMyQueue(){
        Code06_StackImplementQueue.MyQueue<Integer> queue = new Code06_StackImplementQueue.MyQueue<>();
        queue.push(10);
        queue.push(9);
        LoggerUtil.log("popValue="+queue.poll());
        queue.push(8);
        LoggerUtil.log("popValue="+queue.poll());
        queue.push(7);
        LoggerUtil.log("popValue="+queue.poll());
        queue.push(6);
        LoggerUtil.log("popValue="+queue.poll());
        queue.push(5);
        LoggerUtil.log("popValue="+queue.poll());
        queue.push(4);
        while (!queue.isEmpty()){
            LoggerUtil.log("popValue="+queue.poll());
        }
    }
}
