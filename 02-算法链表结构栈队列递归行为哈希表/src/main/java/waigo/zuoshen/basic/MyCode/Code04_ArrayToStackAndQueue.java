package waigo.zuoshen.basic.MyCode;


import waigo.zuoshen.basic.MyCode.utils.LoggerUtil;


/**
 * author waigo
 * create 2021-02-10 22:04
 */
public class Code04_ArrayToStackAndQueue {
    //[1,2,3,4]入栈顺序为1,2,3,4出栈顺序为4,3,2,1也就是有序加入，然后从后面输出就可以了
    public static class MyStack<T> {
        private Object[] a;
        private int size;//指向当前可以插入的位置
        private int limit;

        public MyStack(int initialCapacity) {
            this.limit = initialCapacity;
            a = new Object[initialCapacity];
        }

        public void push(T value) {
            if (size >= limit) throw new RuntimeException("the collection is full!!!");
            a[size++] = value;
        }

        @SuppressWarnings("unchecked")
        public T pop() {
            return (T) a[--size];
        }

        public void printStack() {
            int temp = size - 1;
            StringBuilder builder = new StringBuilder(size == 0 ? "栈顶[]栈底" : "栈顶[");
            while (temp >= 0) {
                builder.append(a[temp--]);
                builder.append(",");
            }
            if (size != 0) {
                builder.deleteCharAt(builder.length() - 1);
                builder.append("]栈底");
            }
            LoggerUtil.log(builder.toString());
            ;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }
    }

    //[1,2,3,4]入队顺序为1,2,3,4出队顺序为1,2,3,4需要设计为环形队列
    //尾进头出，上面这个队头为1
    public static class MyQueue<T> {
        private Object[] elementData;
        private int limit;
        private int size;
        private int front;//队头
        private int rear;//队尾后一个那个位置，一开始都初始化为0

        //[front...rear)
        public MyQueue(int initialCapacity) {
            this.limit = initialCapacity;
            elementData = new Object[limit];
        }

        public void push(T value) {
            if (isFull())
                throw new RuntimeException("the collection is full!!!");
            elementData[rear] = value;
            rear = (rear + 1) % limit;
            size++;
        }
        //从头出
        @SuppressWarnings("unchecked")
        public T poll() {
            final T value = (T) elementData[front];
            elementData[front] = null;
            front = (front + 1)%limit;
            size--;
            return value;
        }

        public void printQueue() {
            StringBuilder builder = new StringBuilder(size == 0 ? "队头[]队尾" : "队头[");
            int temp = front;
            for (int i = 0; i < size; i++) {
                builder.append(elementData[temp]);
                builder.append(",");
                temp = (temp + 1) % limit;
            }
            if (size != 0) {
                builder.deleteCharAt(builder.length() - 1);
                builder.append("]队尾");
            }
            LoggerUtil.log(builder.toString());
        }
        public boolean isFull(){
            return size == limit;
        }
        public boolean isEmpty(){
            return size == 0;
        }

    }
}
