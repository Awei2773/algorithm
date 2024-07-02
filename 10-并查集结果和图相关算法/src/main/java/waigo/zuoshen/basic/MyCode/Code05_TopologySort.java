package waigo.zuoshen.basic.MyCode;

import java.util.*;

/**
 * author waigo
 * create 2021-02-25 22:21
 */
//拓扑排序：像是大学中的课程安排，需要学习某些先修课程才能够学习另外一些课程
//算法思路：将入度为0的节点去掉(输出)并且消除它的影响，就是它的直接邻居入度都要减一
//不断的消去入度为0的节点过程中产生的节点序列即是拓扑排序
public class Code05_TopologySort {
    public static List<Node> topologySort(Graph graph) {
        HashMap<Node, Integer> inMap = new HashMap<>();//Node map to its in，消除影响肯定不能直接去消人家节点的值
        Queue<Node> zeroInQueue = new LinkedList<>();//入度为0的点
        List<Node> result = new ArrayList<>();
        for (Node node : graph.nodesMap.values()) {
            inMap.put(node, node.in);
            if (node.in == 0) zeroInQueue.add(node);
        }
        while (!zeroInQueue.isEmpty()) {
            Node zeroInNode = zeroInQueue.poll();
            result.add(zeroInNode);
            //消除影响
            for (Node next : zeroInNode.nexts) {
                inMap.put(next, inMap.get(next) - 1);
                if (inMap.get(next) == 0) zeroInQueue.add(next);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        for(Object node:topologySort(GraphGenerator.graphGenerator(new int[][]{
                {0,1,2},
                {0,1,4},
                {0,4,2},
                {0,2,3},
                {0,3,5},
                {0,4,5},
                {0,5,7},
                {0,4,7},
                {0,3,8},
                {0,5,8},
                {0,7,8},
        })).toArray()){
            System.out.println("Node{"+((Node)node).value+"}");
        }
    }
}
