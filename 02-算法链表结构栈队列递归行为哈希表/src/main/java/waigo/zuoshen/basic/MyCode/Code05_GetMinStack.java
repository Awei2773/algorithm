package waigo.zuoshen.basic.MyCode;

import java.util.Comparator;
import java.util.Stack;

/**
 * author waigo
 * create 2021-02-11 0:08
 */
public class Code05_GetMinStack {
    public static class MyStack<T> {
        private Stack<T> dataStack;
        private Stack<T> minStack;
        private int size;
        private Comparator<T> comparator;

        public MyStack(Comparator<T> comparator) {
            dataStack = new Stack<>();
            minStack = new Stack<>();
            this.comparator = comparator;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void push(T value) {
            if (isEmpty()) {
                //为空直接压栈
                dataStack.push(value);
                minStack.push(value);
            } else {
                //不为空，看看此时需要压栈的元素和最小栈栈顶的元素谁小
                T minTop = minStack.peek();//peek()返回栈顶，但是不是出栈
                if (this.comparator.compare(minTop, value) <= 0)
                    minStack.push(minTop);
                else
                    minStack.push(value);
                dataStack.push(value);
            }
            size++;
        }
        public T pop(){
            minStack.pop();
            size--;
            return dataStack.pop();
        }
        public T getMin(){
            if(size==0) return null;
            return minStack.peek();
        }
    }
    public static class MyStack2<T> {
        private Stack<T> dataStack;
        private Stack<T> minStack;
        private int size;
        private Comparator<T> comparator;

        public MyStack2(Comparator<T> comparator) {
            dataStack = new Stack<>();
            minStack = new Stack<>();
            this.comparator = comparator;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void push(T value) {
            if (isEmpty()) {
                //为空直接压栈
                dataStack.push(value);
                minStack.push(value);
            } else {
                //不为空，看看此时需要压栈的元素和最小栈栈顶的元素谁小
                T minTop = minStack.peek();//peek()返回栈顶，但是不是出栈
                if (this.comparator.compare(minTop, value) <= 0)
                    minStack.push(minTop);
                else
                    minStack.push(value);
                dataStack.push(value);
            }
            size++;
        }
        public T pop(){
            minStack.pop();
            size--;
            return dataStack.pop();
        }
        public T getMin(){
            if(size==0) return null;
            return minStack.peek();
        }
    }
}
