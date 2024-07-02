package waigo.zuosheng.advanced;

import org.junit.Test;

import java.util.HashMap;

/**
 * author waigo
 * create 2021-07-03 7:59
 */
public class Test2 {
    @Test
    public void test() {
        HashMap<String, Integer> map = new HashMap<>(12);
        map.put("xxx", 2);
    }

    /*@Test
    public void resize() {
        for (int j = 0; j < oldCap; ++j) {
            //e是当前处理的桶，e = oldTab[j],j是当前桶的下标
            Node<K, V> loHead = null, loTail = null;
            Node<K, V> hiHead = null, hiTail = null;
            Node<K, V> next;
            do {
                next = e.next;//抓一下数组上那个节点后面带出来的链条
                //jdk1.8比起jdk1.7计算次数少的关键算法，裸哈希&老容量为0就停在原地
                //jdk1.7是直接裸哈希&(n-1)算新位置，全得&一遍
                //比如之前在13，现在还在13
                if ((e.hash & oldCap) == 0) {
                    if (loTail == null) loHead = e;
                    else loTail.next = e;
                    loTail = e;
                } else {//不为0就+oldCap，之前在13，现在在16+13=29，假设oldCap = 16
                    if (hiTail == null) hiHead = e;
                    else hiTail.next = e;
                    hiTail = e;
                }
                //处理好一个桶，留在原地的用loHead串起来，要走的用hiHead穿起来
            } while ((e = next) != null);
            if (loTail != null) {//留在原地的
                loTail.next = null;
                newTab[j] = loHead;
            }
            if (hiTail != null) {//走的
                hiTail.next = null;
                newTab[j + oldCap] = hiHead;
            }
        }
    }*/
}
