package waigo.zuoshen.advanced.MyCode;

import java.util.HashMap;
import java.util.Map;

/**
 * author waigo
 * create 2021-05-19 9:59
 */
/*
设计和构建一个“最近最少使用”缓存，该缓存会删除最近最少使用的项目。缓存应该从键映射到值(允许你插入和检索特定键对应的值)，
并在初始化时指定最大容量。当缓存被填满时，它应该删除最近最少使用的项目。

它应该支持以下操作： 获取数据 get 和 写入数据 put 。

获取数据 get(key) - 如果密钥 (key) 存在于缓存中，则获取密钥的值（总是正数），否则返回 -1。
写入数据 put(key, value) - 如果密钥不存在，则写入其数据值。当缓存容量达到上限时，
它应该在写入新数据之前删除最近最少使用的数据值，从而为新的数据值留出空间。

示例:

LRUCache cache = new LRUCache( 2  //缓存容量  );

        cache.put(1, 1);
        cache.put(2, 2);
        cache.get(1);       // 返回  1
        cache.put(3, 3);    // 该操作会使得密钥 2 作废
        cache.get(2);       // 返回 -1 (未找到)
        cache.put(4, 4);    // 该操作会使得密钥 1 作废
        cache.get(1);       // 返回 -1 (未找到)
        cache.get(3);       // 返回  3
        cache.get(4);       // 返回  4

 */
public class Code04_LeastRecentlyUsedCache {
    static class Node{
        Node next;
        Node last;
        int value;
        int key;
        public Node(int value,int key) {
            this.value = value;
            this.key = key;
        }
    }
    static class _LinkedList{
        Node head,tail;
        public void addLast(Node newNode){
            newNode.next = null;
            if(head==null){
                this.head = newNode;
                tail = head;
            }else{
                tail.next = newNode;
                newNode.last = tail;
                tail = newNode;
            }
        }
        public Node pollHead(){
            Node ret = head;
            if(head==null||head==tail){
                head = null;
                tail = null;
            }else{
                head = head.next;
                head.last = null;
                ret.next = null;
            }
            return ret;
        }
        public void setNodeToTail(Node node){
            if(head==tail||node==tail) return;
            if(node==head){
                head = node.next;
                head.last = null;
            }else{
                node.last.next=node.next;
                node.next.last=node.last;
            }
            addLast(node);
        }
    }
    static class LRUCache {
        int cap;
        Map<Integer,Node> map;
        _LinkedList list = new _LinkedList();
        public LRUCache(int capacity) {
            map = new HashMap<>(capacity);
            this.cap= capacity;
        }

        public int get(int key) {
            if(!map.containsKey(key)) return -1;
            Node node = map.get(key);
            int ret = node.value;
            list.setNodeToTail(node);
            return ret;
        }

        public void put(int key, int value) {
            //更新就好
            if(map.containsKey(key)){
                Node node = map.get(key);
                node.value = value;
                list.setNodeToTail(node);
            }else{//确实没有才需要加入
                if(map.size()==cap){//满了，进行页面置换
                    map.remove(list.pollHead().key);
                }
                Node node = new Node(value,key);
                list.addLast(node);
                map.put(key,node);
            }
        }

    }
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(1);
        cache.put(2,1);
        System.out.println(cache.get(2));
        cache.put(3,2);
        System.out.println(cache.get(2));
        System.out.println(cache.get(3));
    }
}
