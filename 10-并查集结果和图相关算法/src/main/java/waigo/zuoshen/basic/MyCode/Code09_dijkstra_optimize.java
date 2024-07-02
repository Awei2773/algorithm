package waigo.zuoshen.basic.MyCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * author waigo
 * create 2021-02-26 17:11
 */
//optimize v.优化
/*
    通过研究dijkstra算法我们可以发现，核心就两点
    1）每次都找到那个最小距离的到达点，
    2）遍历那个最小距离的点更新距离表
    很明显第二点是不可能优化的，你总得看看所有的边看看距离怎么样然后更新距离表
    但是第一点找到最小距离，我们很容易会想到使用最小堆是否能够优化，答案是可以的。
    因为最小堆每次进行堆的调整也不过是logN,也就是说我们可以直接使用一个小根堆来存储距离表
    不过注意一点，因为距离表中的距离是需要进行更新的，所以我们需要更新后保证堆正确性，这就不能使用PriorityQueue,
    需要我们自己实现resign功能，这需要维护一个indexMap需要知道哪个位置的元素发生了更新，这个更新只会变小，不会变大，
    所以肯定是向上进行heapInsert
    
    如何在堆中完成一个节点的锁住功能，避免已经处理过的节点再次插入堆中?
    做以下约定
    1）如果indexMap中没有这个Node，那肯定还没有，就直接加入
    2）如果有，但是index为-1，则表示加入过，但是已经弹出，直接跳过，弹出的肯定是找到最小距离了
    3) 以上都不符合表示还在堆中，进行更新操作，需要维护堆
      
*/
public class Code09_dijkstra_optimize {
    public static class Heap {
        private static class NodeRecord {
            private Node node;
            private Integer distance;

            public NodeRecord(Node node, Integer distance) {
                this.node = node;
                this.distance = distance;
            }
        }

        private ArrayList<NodeRecord> elementData;//Entry<Node,distance>
        private HashMap<Node, Integer> indexMap;//这里的Integer是下标
        private int heapSize;

        public Heap() {
            elementData = new ArrayList<>();
            indexMap = new HashMap<>();
        }

        public void swap(int aIdx, int bIdx) {
            //保证下标随着一起改
            NodeRecord aEntry = elementData.get(aIdx);
            NodeRecord bEntry = elementData.get(bIdx);
            elementData.set(aIdx, bEntry);
            elementData.set(bIdx, aEntry);
            indexMap.put(aEntry.node, bIdx);
            indexMap.put(bEntry.node, aIdx);
        }

        public void heapInsert(int index) {
            int father;
            //维护一个小根堆，如果父节点的distance比这个子节点要大的话需要交换
            while (compare(index, (father = (index - 1) / 2)) < 0) {
                swap(father, index);
            }
        }

        public void heapify(int index) {
            int leftChild;
            while ((leftChild = (index << 1) | 1) < heapSize) {
                int lowest = leftChild + 1 < heapSize && compare(leftChild + 1, leftChild) < 0 ? leftChild + 1 : leftChild;
                lowest = compare(index, lowest) < 0 ? index : lowest;
                if (index == lowest) break;
                swap(index, lowest);
                index = lowest;
            }
        }

        //比较两个下标处的Entry里distance大小
        private int compare(int i1, int i2) {
            return elementData.get(i1).distance - elementData.get(i2).distance;
        }

        //进行三个情况的检查和处理，完成操作
        public void addOrUpdateOrIgnore(Node node, int distance) {
            if (!indexMap.containsKey(node)) {//add
                elementData.add(heapSize, new NodeRecord(node, distance));
                //indexMap同步
                indexMap.put(node,heapSize++);
                heapInsert(heapSize - 1);
            } else if (indexMap.get(node) != -1) {//update
                Integer index = indexMap.get(node);
                elementData.get(index).distance = Math.min(elementData.get(index).distance, distance);
                heapInsert(index);//可能更新，维护一下
            }
        }

        public NodeRecord poll() {
            if (isEmpty()) throw new RuntimeException("no more ele...");
            final NodeRecord res = elementData.get(0);
            swap(0,--heapSize);
            elementData.set(heapSize,null);
            indexMap.put(res.node,-1);//-1:曾经来过
            heapify(0);
            return res;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }
    }
    public static HashMap<Node,Integer> dijkstra(Node begin){
        if(begin==null) return null;
        HashMap<Node,Integer> result = new HashMap<>();
        Heap smallRHeap = new Heap();
        smallRHeap.addOrUpdateOrIgnore(begin,0);
        while (!smallRHeap.isEmpty()){
            Heap.NodeRecord heapTop = smallRHeap.poll();
            for(Edge edge:heapTop.node.edges){
                smallRHeap.addOrUpdateOrIgnore(edge.to,edge.weight+heapTop.distance);
            }
            result.put(heapTop.node,heapTop.distance);
        }

        return result;
    }
    public static void main(String[] args) {
        int[][] matrix = {{10, 1, 3}, {100, 1, 6}, {30, 1, 5}, {5, 2, 3}, {50, 3, 4}, {20, 5, 4}, {10, 4, 6}, {60, 5, 6},};
        Graph graph = GraphGenerator.graphGenerator(matrix);
        HashMap<Node, Integer> result = dijkstra(graph.nodesMap.get(1));
        for (Map.Entry<Node, Integer> entry : result.entrySet()) {
            System.out.println("to Node{" + entry.getKey().value + "} distance:" + entry.getValue());
        }
    }
}
