package waigo.zuoshen.basic.MyCode;

import java.util.*;

/**
 * author waigo
 * create 2021-04-01 10:36
 */
/*
厨房里总共有 n 个橘子，你决定每一天选择如下方式之一吃这些橘子：
1.吃掉一个橘子。
2.如果剩余橘子数 n 能被 2 整除，那么你可以吃掉 n/2 个橘子。
3.如果剩余橘子数 n 能被 3 整除，那么你可以吃掉 2*(n/3) 个橘子。
每天你只能从以上 3 种方案中选择一种方案。
请你返回吃掉所有 n 个橘子的最少天数
*/
public class Code12_MinDays {
    public static int minDays(int n) {
        return process(n);
    }

    //返回吃掉n个橘子最少的天数
    private static int process(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        int min = Integer.MAX_VALUE;
        if (n % 2 == 0) {
            min = Math.min(min, 1 + process(n - (n / 2)));
        }
        if (n % 3 == 0) {
            min = Math.min(min, 1 + process(n - (2 * (n / 3))));
        }
        return Math.min(min, 1 + process(n - 1));
    }

    //记忆化搜索,时间过不了
    public static int mem_search(int n) {
        HashMap<Integer, Integer> map = new HashMap<>();
        return process2(n, map);
    }

    private static int process2(int n, HashMap<Integer, Integer> map) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        if (map.containsKey(n)) return map.get(n);
        int min = 1 + process2(n - 1, map);
        if (n % 2 == 0) {
            min = Math.min(min, 1 + process2(n - (n >> 1), map));
        }
        if (n % 3 == 0) {
            min = Math.min(min, 1 + process2(n - ((n / 3) << 1), map));
        }
        map.put(n, min);
        return min;
    }

    //优化到动态规划，内存过不了
    public static int dp(int n) {
        if (n == 0) return 0;
        int[] dp = new int[n + 1];
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {//注意这里的i别写成n了
            dp[i] = 1 + dp[i - 1];
            if ((i & 1) == 0) {//同于n%2==0
                dp[i] = Math.min(dp[i], 1 + dp[i - (i >> 1)]);
            }
            if (i % 3 == 0) {
                dp[i] = Math.min(dp[i], 1 + dp[i - ((i / 3) << 1)]);//(n/3)<<1同于2*(n/3)
            }
        }
        return dp[n];
    }

    //其实根本不用求解那么多位置，很多-1的操作是没必要的，在有3有2的时候肯定是3,2快，所以就有这样的递归
    //这是贪心策略，只要有2,3其中一个就不用1，通过对数器检验正确
    //证明了不可能出现2,3的时候你还吃一个而且正确的情况
    public static int f2(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        int i1 = n % 2;
        int i2 = n % 3;//离最近一个整除三的数的步数
        //找到离整除最近的位置需要花费时间为i1、i2，找到之后它们分别吃的还剩下2/n和3/n,加上吃的那天+1
        //n-i1和n-i2这种操作是否有必要，就像是57让你求十位数位是多少一样，有必要(57-7)/10嘛？因为余数那部分不超过除数，所以可以直接除
//        return Math.min(f2((n-i1)>>1)+i1+1,f2((n-i2)/3)+i2+1);
        return Math.min(f2(n >> 1) + i1 + 1, f2(n / 3) + i2 + 1);
    }

    //记忆化搜索
    public static int mem_search2(int n) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        map.put(2, 2);//base case
        return process3(n, map);
    }

    private static int process3(int n, HashMap<Integer, Integer> map) {
        if (map.containsKey(n)) return map.get(n);
        map.put(n, Math.min(f2(n >> 1) + n % 2 + 1, f2(n / 3) + n % 3 + 1));
        return map.get(n);
    }

    //将n,n/2,n/3都当做节点，结果就是从n节点到达1节点的最短距离加一，这可以使用dijkstra算法来做
    //n节点去n/2节点的开销是n%2+1,n去n/3节点的开销是n%3+1
    public static int dijkstra(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        //自己维护堆，因为可能出现修改value的情况
        Heap smallHeap = new Heap();
        smallHeap.addOrUpdateNodeRecord(new NodeRecord(0, n));
        HashSet<NodeRecord> visited = new HashSet<>();
        NodeRecord curNode;
        int ans = 0;
        while (!smallHeap.isEmpty()) {
            curNode = smallHeap.poll();
            if (visited.contains(curNode)) continue;
            if (curNode.node == 1) {
                ans = curNode.distance + 1;//1还得去0，没有能够不去1直接去0的
                break;
            }
            //去n/2节点
            smallHeap.addOrUpdateNodeRecord(new NodeRecord(curNode.distance + curNode.node % 2 + 1, curNode.node / 2));
            //去n/3节点
            smallHeap.addOrUpdateNodeRecord(new NodeRecord(curNode.distance + curNode.node % 3 + 1, curNode.node / 3));
            visited.add(curNode);//锁住当前已经访问过的节点
        }
        return ans;
    }

    public static class NodeRecord {
        private int distance;
        private int node;

        public NodeRecord(int distance, int node) {
            this.distance = distance;
            this.node = node;
        }

        //hashmap的key是引用对象的话一定要重写这个hashCode方法，不然按照地址来算就不是按照值来算了
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NodeRecord that = (NodeRecord) o;
            return node == that.node;
        }

        @Override
        public int hashCode() {
            return Objects.hash(node);
        }

    }

    public static class Heap {
        private ArrayList<NodeRecord> vals = new ArrayList<>();//距离表
        private HashMap<NodeRecord, Integer> indexMap = new HashMap<>();//<key,value>=<节点，下标>
        private int size = 0;

        private void heapInsert(int son) {
            int father;
            //小根堆，距离近的放在上面
            while (son - 1 > 0 && compare(son, father = ((son - 1) >> 1)) < 0) {
                swap(father, son);
                son = father;
            }
        }

        public int compare(int a, int b) {
            return vals.get(a).distance - vals.get(b).distance;
        }

        private void heapify(int father) {
            // leftChild = father*2+1
            //至少有一个孩子才需要进行heapify
            int leftChild;
            while ((leftChild = father << 1 | 1) < size) {
                int smallest = leftChild + 1 < size && vals.get(leftChild + 1).distance < vals.get(leftChild).distance ? leftChild + 1 : leftChild;
                smallest = vals.get(father).distance < vals.get(smallest).distance ? father : smallest;
                if (smallest == father) break;
                swap(smallest, father);
                father = smallest;
            }
        }

        public void addOrUpdateNodeRecord(NodeRecord newNode) {
            if (!indexMap.containsKey(newNode)) {//没有就添加
                indexMap.put(newNode, size);
                vals.add(size, newNode);
                heapInsert(size++);//调整堆
            } else if (indexMap.get(newNode) != -1) {//有就更新
                System.out.println(1);
                Integer oldIdx = indexMap.get(newNode);
                NodeRecord old = vals.get(oldIdx);
                old.distance = newNode.distance < old.distance ? newNode.distance : old.distance;
                //可能需要调整
                heapify(oldIdx);
                heapInsert(oldIdx);
            }
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public NodeRecord poll() {
            if (isEmpty()) return null;
            final NodeRecord val = vals.get(0);
            swap(0, --size);
            heapify(0);
            vals.set(size, null);//删除掉对应表
            indexMap.put(val, -1);//表示曾经来过
            return val;
        }

        private void swap(int a, int b) {
            //交换值
            NodeRecord temp1 = vals.get(a);
            NodeRecord temp2 = vals.get(b);
            vals.set(a, temp2);
            vals.set(b, temp1);
            //维护下标表
            indexMap.put(temp1, b);
            indexMap.put(temp2, a);
        }
    }


    //不自己维护堆，遇到重复值也不修改，直接丢进去，反正距离小的会自己浮上来，大的就丢在下面不管了
    public static int dijkstra1(int n) {
        //int[] = {节点，距离}
        PriorityQueue<int[]> heap = new PriorityQueue<>((o1, o2) -> {
            //距离相同的时候肯定节点小的上去啦
            return o1[1] == o2[1] ? o1[0] - o2[0] : o1[1] - o2[1];
        });
        HashSet<Integer> visited = new HashSet<>();
        heap.add(new int[]{n, 0});
        int[] curNode;
        int node, rest;
        int ans = 0;
        while (!heap.isEmpty()) {
            curNode = heap.poll();
            node = curNode[0];
            rest = curNode[1];
            if (visited.contains(node)) continue;
            if (node == 1) {
                ans = rest + 1;
                break;
            }
            //去n/2
            heap.add(new int[]{node >> 1, rest + 1 + (node % 2)});
            //去n/3
            heap.add(new int[]{node / 3, rest + 1 + (node % 3)});
            visited.add(node);
        }
        return ans;
    }

    public static void main(String[] args) {
//        int i = 1, testTimes = 10000;
//        for (; i < testTimes; i++)
//            if (dijkstra1(i) != mem_search2(i)) {
//                System.out.println(i);
//                System.out.println(dijkstra1(i));
//                System.out.println(mem_search2(i));
//                break;
//            }
//        System.out.println(i == testTimes ? "finish!!!" : "fucking!!!");
//        for(int i = 0;i<100;i++)
//            System.out.println(i+":"+dijkstra1(i));
        dijkstra(10000000);
    }
}
