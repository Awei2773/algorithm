package waigo.zuosheng.MyCode;

import java.lang.reflect.Proxy;
import java.util.HashMap;

/**
 * author waigo
 * create 2021-07-21 14:43
 */
//缺点：频率桶还得自己维护链表，麻烦，而且节点已经维护一个双向链表了，桶还维护，重复了
//优化：将频率桶使用哈希表来存，这样根据节点频率就能查到该操作的频率桶了
/*public class Code460_LFUCache {
    public int[] LFU (int[][] operators, int k) {
        // write code here
    }
    //缓存关键点：
    //1.删最少调用的那个记录
    //2.和LRU的差别就在于最少调用的记录如果有多个就删掉上一次操作最早那个
    //关键点set,get都要O(1)，所以需要一个哈希表存储<x,y>
    public static class LFUCache{
        //一个哈希表存<x,Node>
        HashMap<Integer,Node> cache = new HashMap<>();
        //一个哈希表存<times,Bucket> 频率 to 桶（双向链表）
        HashMap<Integer,Bucket> timesMap = new HashMap<>();
        //记一下当前最少操作的操作数minOpr
        //需要维护的时刻
        //1.插入新记录必定是变成1
        //2.在进行操作的时候总需要维护一下，如果所在的桶空了就得上移了
        int minOpr;
        //缓存容量
        int capacity;
        //记录数
        int size;
        public LFUCache(int capacity){
            this.capacity = capacity;
        }
        public void set(int key,int value){
            if(capacity<=0) return;
            if(cache.containsKey(key)){
                Node node = cache.get(key);
                node.value = value;
                Bucket bucket = timesMap.get(node.times++);
                bucket.removeNode(node);
                if(bucket.size==0){
                    minOpr++;
                }
                //入新桶
                if(!timesMap.containsKey(node.times)){
                    timesMap.put(node.times,new Bucket());
                }
                timesMap.get(node.times).insert(node);
            }else{
                if(size==capacity){
                    erase();//删一个
                }
                //进
                if(!timesMap.containsKey(1)){
                    timesMap.put(1,new Bucket());
                }
                Node node = new Node(key,value);
                cache.put(key,node);
                size++;
                minOpr = 1;
            }
        }
        public int get(int key){
            if(capacity<=0||!cache.containsKey(key)) return -1;
            Node node = cache.get(key);
            //从旧桶中删除
            Bucket bucket = timesMap.get(node.times++);
            bucket.removeNode(node);
            if(bucket.size==0){
//                Proxy.newProxyInstance()
            }
            //查看
            return 0;
        }
        //删除记录
        public void erase(){
            //1.找到对应的桶,将尾巴删去
            Bucket minOprBucket = timesMap.get(minOpr);
            Node node = minOprBucket.removeNode(minOprBucket.tail);
            //2.同样的，记录表去掉，记录数减少
            size--;
            cache.remove(node.key);
        }
    }
    //双向链表需要的功能
    //设计双向链表，1.哑节点2.head3.tail
    //1.将某个节点摘出来，并且连好前后环境 void removeNode(node)
    //2.将某个节点插入队头 void insert(node)
    //设计双向链表越靠近队头就越是新操作节点
    public static class Bucket{
        Node head = new Node(0,0);
        Node tail = head;
        int size;
        public Node removeNode(Node node){
            node.pre.next = node.next;
            if(node==tail){
                tail = node.pre;
            }else{
                node.next.pre = node.pre;
            }
            size--;
            return node;
        }
        public void insert(Node node){
            //插入队头
            node.next = head.next;
            node.pre = head;
            if(head==tail){
                tail = node;
            }else{
                head.next.pre = node;
            }
            head.next = node;
            size++;
        }
    }
    public static class Node{
        int key,value,times=1;
        Node pre,next;
        public Node(int key,int value){
            this.key = key;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        Code460_LFUCache lfuCache = new Code460_LFUCache(3);
    }
}*/
