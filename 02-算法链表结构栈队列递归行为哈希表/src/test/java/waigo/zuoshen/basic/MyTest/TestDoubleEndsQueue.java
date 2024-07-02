package waigo.zuoshen.basic.MyTest;

import org.junit.Test;
import waigo.zuoshen.basic.MyCode.Code03_DoubleEndsQueueToStackAndQueue;
import waigo.zuoshen.basic.MyCode.utils.LoggerUtil;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * author waigo
 * create 2021-02-10 15:55
 */
public class TestDoubleEndsQueue {
    @Test
    public void testAddFromHead() {

        Code03_DoubleEndsQueueToStackAndQueue.DoubleEndsQueue<Integer> myQueue = new Code03_DoubleEndsQueueToStackAndQueue.DoubleEndsQueue<>();
        myQueue.addFromHead(13);
        myQueue.addFromHead(11);
        myQueue.addFromHead(12);
        myQueue.addFromHead(14);
        myQueue.printDoubleEndsQueue();
    }
    @Test
    public void testAddFromTail() {

        Code03_DoubleEndsQueueToStackAndQueue.DoubleEndsQueue<Integer> myQueue = new Code03_DoubleEndsQueueToStackAndQueue.DoubleEndsQueue<>();
        myQueue.addFromTail(13);
        myQueue.addFromTail(11);
        myQueue.addFromTail(14);
        myQueue.addFromTail(156);
        myQueue.printDoubleEndsQueue();
        for(int i = 0; i < myQueue.size(); i++){
            Integer value = myQueue.popFromTail();
            myQueue.printDoubleEndsQueue();
            LoggerUtil.log("popValue = "+value);
        }
    }
    @Test
    public void testMyStack(){
        Code03_DoubleEndsQueueToStackAndQueue.MyStack<Integer> stack = new Code03_DoubleEndsQueueToStackAndQueue.MyStack<>();
        stack.push(13);
        stack.push(12);
        stack.push(11);
        stack.push(10);
        stack.push(9);
        stack.printStack();
        while(!stack.isEmpty()){
            LoggerUtil.log("popValue = "+stack.pop());
            stack.printStack();
        }
    }
    @Test
    public void testMyQueue(){
        Code03_DoubleEndsQueueToStackAndQueue.MyQueue<Integer> queue = new Code03_DoubleEndsQueueToStackAndQueue.MyQueue<>();
        queue.push(13);
        queue.push(12);
        queue.push(11);
        queue.push(10);
        queue.push(9);
        queue.printQueue();
        while(!queue.isEmpty()){
            LoggerUtil.log("popValue = "+queue.pop());
            queue.printQueue();
        }
    }
    @Test
    public void finalTest(){
        int testTimes = 1000000;
        //系统栈，系统队列
        Stack<Integer> sysStack = new Stack<>();
        Queue<Integer> sysQueue = new LinkedList<>();
        //LinkedList实现的队列是尾进头出，offer方法底层调用的是add方法然后add方法将插入的元素连在最后
        sysQueue.offer(10);
        sysQueue.offer(9);
        sysQueue.offer(8);
        sysQueue.offer(7);
        while(!sysQueue.isEmpty()){
            LoggerUtil.log("popValue = "+sysQueue.poll());
        }
    }
}
