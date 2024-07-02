package waigo.zuoshen.basic.MyCode;

import waigo.zuoshen.basic.MyCode.utils.LoggerUtil;

import java.util.*;

/**
 * author waigo
 * create 2021-02-11 8:57
 */
public class HashMapAndSortedMap {
    public static class Node {
        private int value;
        public Node(int value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return value == node.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    public static void main(String[] args) {
        //哈希表，通过哈希值来进行key的判断，key是唯一的，重复则修改
        HashMap<Integer,String> map = new HashMap<>();
        map.put(100,"hello world");
        map.put(100,"hello world1");
        map.put(1000,"hello world2");
        HashMap<Node,String> myMap = new HashMap<>();
        Node a = new Node(10);
        Node b = new Node(10);
        myMap.put(a,"hello world234");
        myMap.put(b,"hello world236");

        //有序表
        SortedMap<Integer,String> sortedMap = new TreeMap<>();
        sortedMap.put(1000,"lisi");
        sortedMap.put(100,"张三");
        sortedMap.put(10001,"王五");
        Integer integer = sortedMap.firstKey();
        LoggerUtil.log("firstKey:"+integer);
        //有序表比哈希表功能强的地方就是会将存入map的元素按照key的大小进行一个自动的排序
        /*
         return comparator==null ? ((Comparable<? super K>)k1).compareTo((K)k2)
            : comparator.compare((K)k1, (K)k2);
            TreeMap底层使用这个语句来进行一个元素的比大小，这是完成排序的关键，对于
            基本类型包装类，有系统自己实现的compareTo方法，而对于我们自己创建的Node类
            就需要提供一个比较器了
        */
        SortedMap<Node,String> map1 = new TreeMap<>();
        map1.put(a,"lisi");
        map1.put(b,"王五");
    }
}
