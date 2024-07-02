package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-09 22:09
 */

import waigo.zuoshen.basic.MyCode.utils.LoggerUtil;


/**
 * 面试题：单链表和双链表如何反转
 */
public class Code01_ReverseList {
    /**
     * 单链表
     */
    public class SimpleLinkedList<T> {
        private Node head;
        private int size;

        private class Node {
            T value;
            Node next;

            public Node(T value) {
                this.value = value;
            }
        }

        public void add(T value) {
            Node newNode = new Node(value);
            if (size++ == 0) {
                head = newNode;
            } else {
                Node headNext = head.next;
                head.next = newNode;
                newNode.next = headNext;
            }
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void reverseLinkedList() {
            if (size > 1) {
                Node pre = null, next;
                while (head != null) {
                    next = head.next;
                    head.next = pre;
                    pre = head;
                    head = next;
                }
                head = pre;
            }

        }

        public void printLinkedList() {
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

        public int size() {
            return size;
        }

        public void removeAll(T value) {
            //可能需要删除的数是头,先把前面有这个value的给删除干净，确定新头
            while (head != null) {
                if (!head.value.equals(value)) break;
                head = head.next;
            }
            if (head != null) {
                Node cur = head.next, pre = head;
                while (cur != null) {
                    if (!cur.value.equals(value)) {
                        pre.next = cur;
                        pre = cur;
                    }
                    cur = cur.next;
                }
                //最后出来的时候,pre由于指向最后一个不等于value的位置，所以pre下一个肯定为null
                pre.next = null;
            }

        }
    }

    /**
     * 双链表
     */
    public class DoubleLinkedList<T> {
        private Node head;
        private int size;

        public class Node {
            private T value;
            private Node pre;
            private Node next;

            public Node(T value) {
                this.value = value;
            }

            @Override
            public String toString() {
                return value.toString();
            }
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void add(T value) {
            Node newNode = new Node(value);
            if (size == 0) {
                head = newNode;
            } else if (size == 1) {
                head.next = newNode;
                newNode.pre = head;
            } else {
                newNode.next = head.next;
                newNode.pre = head;
                head.next = newNode;
                newNode.next.pre = newNode;
            }
            size++;
        }

        public void printLinkedList() {
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

        /**
         * 循环遍历一遍，时间复杂度O(N)
         */
        public void reverseLinkedList() {
            if (size > 1) {
                Node pre = null, next;
                while (head != null) {
                    next = head.next;
                    head.next = pre;
                    head.pre = next;
                    pre = head;
                    head = next;
                }
                head = pre;
            }

        }

        /**
         * 删除所有给定的数
         */
        public void removeAll(T value) {
            //先删头
            while (head != null) {
                if (!head.value.equals(value)) break;
                head = head.next;
            }
            //此时head指向第一个不用删的位置
            if (head != null) {
                Node pre = head;
                Node cur = head.next;
                while (cur != null) {
                    if(!cur.value.equals(value)){
                        pre.next = cur;
                        cur.pre = pre;
                        pre = cur;
                    }
                    cur = cur.next;
                }
                pre.next = null;
            }
        }
    }
}
