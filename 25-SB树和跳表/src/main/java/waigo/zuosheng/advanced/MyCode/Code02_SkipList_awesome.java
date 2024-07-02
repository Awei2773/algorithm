package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-23 17:13
 */

import com.sun.deploy.net.HttpRequest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * 不使用任何库函数，设计一个跳表。
 * 跳表是在 O(log(n)) 时间内完成增加、删除、搜索操作的数据结构。跳表相比于树堆与红黑树，其功能与性能相当，
 * 并且跳表的代码长度相较下更短，其设计思想与链表相似。
 * 例如，一个跳表包含 [30, 40, 50, 60, 70, 90]，然后增加 80、45 到跳表中，以下图的方式操作：
 * <p>
 * Artyom Kalinin [CC BY-SA 3.0], via Wikimedia Commons
 * <p>
 * 跳表中有很多层，每一层是一个短的链表。在第一层的作用下，增加、删除和搜索操作的时间复杂度不超过 O(n)。
 * 跳表的每一个操作的平均时间复杂度是 O(log(n))，空间复杂度是 O(n)。
 * <p>
 * 在本题中，你的设计应该要包含这些函数：
 * <p>
 * bool search(int target) : 返回target是否存在于跳表中。
 * void add(int num): 插入一个元素到跳表。
 * bool erase(int num): 在跳表中删除一个值，如果 num 不存在，直接返回false. 如果存在多个 num ，删除其中任意一个即可。
 * 了解更多 : https://en.wikipedia.org/wiki/Skip_list
 * <p>
 * 注意，跳表中可能存在多个相同的值，你的代码需要处理这种情况。
 * 样例:
 * Skiplist skiplist = new Skiplist();
 * <p>
 * skiplist.add(1);
 * skiplist.add(2);
 * skiplist.add(3);
 * skiplist.search(0);   // 返回 false
 * skiplist.add(4);
 * skiplist.search(1);   // 返回 true
 * skiplist.erase(0);    // 返回 false，0 不在跳表中
 * skiplist.erase(1);    // 返回 true
 * skiplist.search(1);   // 返回 false，1 已被擦除
 * 约束条件:
 * <p>
 * 0 <= num, target <= 20000
 * 最多调用 50000 次 search, add, 以及 erase操作。
 */
@SuppressWarnings("Duplicates")
public class Code02_SkipList_awesome {
    static class Skiplist {
        public class Node {
            Integer value;
            //节点高度
            List<Node> nexts;

            public Node(Integer value) {
                this.value = value;
                this.nexts = new ArrayList<>();
            }
        }

        Node head = new Node(null);
        int maxLevel = 1;//最少也得有1层，从0开始，省得get的时候还要减一了

        public Skiplist() {
            head.nexts.add(null);
        }

        public boolean search(int target) {
            //在跳表中找到小于这个target的最右一个数
            Node mostRight = mostLessInSkipList(target);
            return mostRight.nexts.get(0)!=null&&mostRight.nexts.get(0).value == target;
        }

        //通过多层索引找到targetLevel层小于target的最右一个节点
        private Node mostLessInSkipList(int target) {
            //从level层开始找
            Node mostLessTarget = head;
            int tempLevel = maxLevel-1;
            while (tempLevel >= 0) {
                mostLessTarget = mostLessInLevel(mostLessTarget, tempLevel--, target);
            }
            return mostLessTarget;
        }

        //从当前节点开始找在这一层上小于target的最右一个节点
        private Node mostLessInLevel(Node curNode, int specifyLevel, int target) {
            Node temp = curNode;
            while (temp.nexts.get(specifyLevel) != null
                    && temp.nexts.get(specifyLevel).value < target) {
                temp = temp.nexts.get(specifyLevel);
            }

            return temp;
        }
        public static abstract class A{
            public static void main(String[] args) {
                System.out.println(Math.ceil(3.14));

            }
        }
        public void add(int num) {
            //丢硬币看看落几层
            int curNodeLevel = 1;
            while (Math.random() < 0.5) curNodeLevel++;
            //如果这个level比起表头要高了，就加高
            while (maxLevel < curNodeLevel) {
                head.nexts.add(null);
                maxLevel++;
            }
            //OK，建出当前节点
            Node curNode = new Node(num);
            for (int i = 0; i < curNodeLevel; i++) {
                curNode.nexts.add(null);
            }
            //从高到低连，高层索引就只用走一遍,充分利用高层索引，确保时间复杂度O(logN)这是重点
            Node pre = head;
            for (int level = maxLevel-1; level>=0; level--) {
                //找到每一层小于当前节点的最右一个位置
                pre = mostLessInLevel(pre,level,num);
                if(level<curNodeLevel){//得到了新节点需要操作的层才连
                    curNode.nexts.set(level,pre.nexts.get(level));
                    pre.nexts.set(level,curNode);
                }
            }
        }

        public boolean erase(int num) {
            if (!search(num)) return false;
            //从高层往下删
            Node pre = head;
            Node find;
            for (int level = maxLevel-1; level >= 0; level--) {
                pre = mostLessInLevel(pre, level, num);
                find = pre.nexts.get(level);
                if(find !=null&&find.value==num){//到了该删的节点了
                    pre.nexts.set(level,find.nexts.get(level));
                }
                if(level!=0&&pre==head&&pre.nexts.get(level)==null){//这一层已经莫得了，删掉它
                    pre.nexts.remove(level);
                    maxLevel--;
                }
            }
            return true;
        }
    }

    public static void main(String[] args) {
        Skiplist skiplist = new Skiplist();

        skiplist.add(1);
        skiplist.add(2);
        skiplist.add(3);
        System.out.println(skiplist.search(0));   // 返回 false
        skiplist.add(4);
        System.out.println(skiplist.search(1));   // 返回 true
        System.out.println(skiplist.erase(0));    // 返回 false，0 不在跳表中
        System.out.println(skiplist.erase(1));    // 返回 true
        System.out.println(skiplist.search(1)); // 返回 false，1 已被擦除

    }
}
