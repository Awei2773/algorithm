package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-11 0:32
 */

import java.util.Stack;

/**
 * 如何用栈结构实现队列结构
 */
public class Code06_StackImplementQueue {
    //队列先入先出，而栈是先入后出的，所以需要准备两个栈，栈1出一遍进入栈2，此时栈2从上到下就是当时入栈的顺序了
    public static class MyQueue<T> {
        private Stack<T> inStack;
        private Stack<T> outStack;
        private int size;

        public MyQueue() {
            inStack = new Stack<>();
            outStack = new Stack<>();
        }

        public void push(T value) {
            inStack.push(value);
            size++;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public T poll() {
            //先判断出栈是否为空
            if (isEmpty()) throw new RuntimeException("the queue is null!!!");
            size--;
            if(!outStack.isEmpty()) return outStack.pop();
            //出栈为空，将入栈一次性全部倒入出栈
            while(!inStack.isEmpty())
                outStack.push(inStack.pop());
            return outStack.pop();
        }
    }
}
