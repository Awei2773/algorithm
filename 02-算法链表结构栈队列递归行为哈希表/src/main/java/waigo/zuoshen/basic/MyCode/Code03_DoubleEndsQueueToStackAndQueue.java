package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-10 9:27
 */

import waigo.zuoshen.basic.MyCode.utils.LoggerUtil;

/**
 * 双向链表实现栈和队列
 * 栈：犹如弹夹，先入后出，后入先出，从头进从头出
 * 队列：先入先出，从头进，从尾出
 */
public class Code03_DoubleEndsQueueToStackAndQueue {
    public static class DoubleEndsQueue<T> {
        private Node head;//头
        private Node tail;//尾
        private int size;

        private class Node {
            T value;
            Node pre;
            Node next;

            public Node(T value) {
                this.value = value;
            }
        }

        //从头进
        public void addFromHead(T value) {
            Node newNode = new Node(value);
            if (size < 1) {
                head = newNode;
                tail = newNode;
            } else {
                newNode.next = head;
                head.pre = newNode;
                head = newNode;
            }
            size++;
        }

        public void printDoubleEndsQueue() {
            StringBuilder listStr = new StringBuilder("[");
            Node temp = head;
            if (temp == null) {
                LoggerUtil.log("[]");
                return;
            }
            while (temp != null) {
                listStr.append(temp.value);
                listStr.append(",");
                temp = temp.next;
            }
            listStr.deleteCharAt(listStr.length() - 1);
            listStr.append("]");
            LoggerUtil.log(listStr.toString());
        }

        public void addFromTail(T value) {
            Node newNode = new Node(value);
            if (size < 1) {//此时还没有元素
                head = newNode;
                tail = newNode;
            } else {
                newNode.pre = tail;
                tail.next = newNode;
                tail = newNode;
            }
            size++;
        }

        public int size() {
            return size;
        }

        //从头出
        public T popFromHead() {
            //参考jdk源码，说是将head.value和head.next 都设置为null有利于GC
            //原因是堆中value和next此时有指针指向他们，可能GC不会将他们销毁，而只是将原先指向head的指针销毁了
            //久而久之就会内存泄漏
            if (size < 1) throw new RuntimeException("the list is null,can't get ele!!!");
            final T value = head.value;
            final Node next = head.next;
            head.value = null;
            head.next = null;//help GC

            head = next;
            if(next == null)//只有一个元素，已经弹出了
                tail = null;
            else
                next.pre = null;
            size--;
            return value;
        }

        //从尾出
        public T popFromTail() {
            if (size < 1) throw new RuntimeException("the lisi is null,can't pop ele!!!");
            T value = tail.value;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                tail = tail.pre;
                tail.next = null;
            }
            size--;
            return value;
        }
    }

    //头入头出
    public static class MyStack<T> {
        DoubleEndsQueue<T> queue;

        public MyStack() {
            queue = new DoubleEndsQueue<>();
        }

        public void push(T value) {
            queue.addFromHead(value);
        }

        public T pop() {
            return queue.popFromHead();
        }

        public boolean isEmpty() {
            return queue.size() == 0;
        }

        public void printStack() {
            queue.printDoubleEndsQueue();
        }

        public int size() {
            return queue.size();
        }
    }

    //尾入头出或者头入尾出，反正一头进一头出就完事了
    public static class MyQueue<T> {
        DoubleEndsQueue<T> queue;

        public MyQueue() {
            queue = new DoubleEndsQueue<>();
        }

        //尾入
        public void push(T value) {
            queue.addFromTail(value);
        }

        //头出
        public T pop() {
            return queue.popFromHead();
        }

        public int size() {
            return queue.size;
        }

        public boolean isEmpty() {
            return size() == 0;
        }
        public void printQueue(){
            queue.printDoubleEndsQueue();
        }
    }
}
