package waigo.zuoshen.basic.MyCode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * author waigo
 * create 2021-02-26 16:02
 */
//dijkstra算法完成的是找到某个节点到其他各个节点的最短距离，规定所有路线权重都是大于0的，一开始需要给一个点，
//因为完成的就是找这个点到其他各个节点的最短距离
public class Code08_dijkstra {
    public static HashMap<Node, Integer> dijkstra(Node begin) {
        if (begin == null) return null;
        HashMap<Node, Integer> nodeDistanceMap = new HashMap<>();
        nodeDistanceMap.put(begin, 0);
        //锁住已经处理过的节点
        HashSet<Node> selectedNode = new HashSet<>();
        //每次找到当前距离最短的节点
        while (true) {
            int minDistance = Integer.MAX_VALUE;
            Node minNode = null;
            for (Map.Entry<Node, Integer> entry : nodeDistanceMap.entrySet()) {
                Integer distance = entry.getValue();
                if (!selectedNode.contains(entry.getKey()) && distance < minDistance) {
                    minDistance = distance;
                    minNode = entry.getKey();
                }
            }
            if (minDistance == Integer.MAX_VALUE) break;
            for (Edge next : minNode.edges) {
                if (!nodeDistanceMap.containsKey(next.to)) {
                    nodeDistanceMap.put(next.to, minDistance + next.weight);
                } else {
                    nodeDistanceMap.put(next.to, Math.min(nodeDistanceMap.get(next.to), minDistance + next.weight));
                }
            }
            //锁住Node,以后不用管它了
            selectedNode.add(minNode);
        }
        return nodeDistanceMap;
    }

    //将找到最小距离的节点给抽离出一个函数，其实是再写一遍，不太熟练
    public static HashMap<Node, Integer> dijkstra1(Node begin) {
        if (begin == null) return null;
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(begin, 0);
        HashSet<Node> lockedNodes = new HashSet<>();
        Node minNode;
        while ((minNode = getMinDistanceNode(distanceMap, lockedNodes)) != null) {
            for (Edge edge : minNode.edges) {
                Integer distance = distanceMap.get(minNode);
                Node to = edge.to;
                if (!distanceMap.containsKey(to)) {
                    //add
                    distanceMap.put(to, distance + edge.weight);
                } else {
                    //update
                    distanceMap.put(to, Math.min(distance + edge.weight, distanceMap.get(to)));
                }
            }
            lockedNodes.add(minNode);//锁住
        }
        return distanceMap;
    }

    public static Node getMinDistanceNode(HashMap<Node, Integer> distanceMap, HashSet<Node> lockedNodes) {
        int min = Integer.MAX_VALUE;
        Node minNode = null;
        for (Map.Entry<Node, Integer> entry : distanceMap.entrySet()) {
            if (!lockedNodes.contains(entry.getKey()) && entry.getValue() < min) {
                min = entry.getValue();
                minNode = entry.getKey();
            }
        }
        return minNode;
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
