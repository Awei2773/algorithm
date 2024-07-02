package waigo.zuoshen.basic.MyCode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * author waigo
 * create 2021-02-11 0:57
 */
public class Code07_QueueImplementStack {
    public static class MyStack<T> {
        private Queue<T> dataQueue;
        private Queue<T> helpQueue;
        private int size;

        public MyStack() {
            dataQueue = new LinkedList<>();
            helpQueue = new LinkedList<>();
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void push(T value) {
            dataQueue.offer(value);
            size++;
        }

        public T pop() {
            while (dataQueue.size() > 1) helpQueue.offer(dataQueue.poll());//把data队列中除了最后一个全部加入到help队列中去
            final T value = dataQueue.poll();
            size--;
            //交换data和help
            Queue<T> temp = dataQueue;
            dataQueue = helpQueue;
            helpQueue = temp;
            return value;
        }
    }
}
