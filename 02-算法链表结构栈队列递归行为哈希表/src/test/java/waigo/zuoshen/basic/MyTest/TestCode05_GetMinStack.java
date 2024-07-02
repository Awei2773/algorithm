package waigo.zuoshen.basic.MyTest;

import org.junit.Test;
import waigo.zuoshen.basic.MyCode.Code05_GetMinStack;
import waigo.zuoshen.basic.MyCode.utils.LoggerUtil;

import java.util.Comparator;

/**
 * author waigo
 * create 2021-02-11 0:20
 */
public class TestCode05_GetMinStack {
    @Test
    public void testMyStack() {
        Code05_GetMinStack.MyStack<Integer> myStack = new Code05_GetMinStack.MyStack<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        myStack.push(10);
        LoggerUtil.log("current min = " + myStack.getMin());
        myStack.push(9);
        LoggerUtil.log("current min = " + myStack.getMin());
        myStack.push(11);
        LoggerUtil.log("current min = " + myStack.getMin());
        myStack.push(7);
        LoggerUtil.log("current min = " + myStack.getMin());
        while(!myStack.isEmpty()){
            myStack.pop();
            LoggerUtil.log("current min = " + myStack.getMin());
        }

    }
}
