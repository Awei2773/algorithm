package waigo.zuoshen.basic.MyTest;

import org.junit.Test;
import waigo.zuoshen.basic.MyCode.Code04_ArrayToStackAndQueue;
import waigo.zuoshen.basic.MyCode.utils.LoggerUtil;

/**
 * author waigo
 * create 2021-02-10 22:20
 */
public class TestArrayStackAndQueue {
    @Test
    public void testMyStack(){
        Code04_ArrayToStackAndQueue.MyStack<Integer> stack = new Code04_ArrayToStackAndQueue.MyStack<>(5);
        stack.push(10);
        stack.push(13);
        stack.push(12);
        stack.printStack();
        while (!stack.isEmpty()){
            LoggerUtil.log("popValue = "+stack.pop());
            stack.printStack();
        }
    }
    @Test
    public void testMyQueue(){
        Code04_ArrayToStackAndQueue.MyQueue<Integer> queue = new Code04_ArrayToStackAndQueue.MyQueue<>(5);
        queue.push(10);
        queue.push(9);
        queue.push(8);
        LoggerUtil.log("pollValue = "+queue.poll());
        LoggerUtil.log("pollValue = "+queue.poll());
        queue.push(7);
        queue.printQueue();
        queue.push(6);
        queue.push(5);
        queue.push(4);
        while(!queue.isEmpty()){
            LoggerUtil.log("pollValue = "+queue.poll());
            queue.printQueue();
        }

    }
}
