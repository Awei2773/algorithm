package waigo.zuoshen.basic.MyTest;

import org.junit.Test;
import waigo.zuoshen.basic.MyCode.Code04_ArrayToStackAndQueue;
import waigo.zuoshen.basic.MyCode.Code07_QueueImplementStack;
import waigo.zuoshen.basic.MyCode.utils.LoggerUtil;

/**
 * author waigo
 * create 2021-02-11 1:07
 */
public class TestCode07_QueueImplementStack {
    @Test
    public void testMyStack(){
        Code07_QueueImplementStack.MyStack<Integer> stack = new Code07_QueueImplementStack.MyStack<>();
        stack.push(10);
        stack.push(13);
        stack.push(12);
        stack.push(11);
        stack.push(13);
        stack.push(14);
        while (!stack.isEmpty()){
            LoggerUtil.log("popValue = "+stack.pop());
        }
    }
}
