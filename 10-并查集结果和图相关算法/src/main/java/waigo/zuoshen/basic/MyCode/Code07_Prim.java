package waigo.zuoshen.basic.MyCode;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * author waigo
 * create 2021-02-26 8:38
 */
//Prim算法，一开始解锁点集V{v1},边集E{v1所连接的边},找出v1所连接的边中最小的那个看看连接的点是否已经解锁，
//没解锁则解锁，然后它的边全部加入E,Prim算法和Kruskal区别就是kruskal算法中涉及到多个区域的合并，
//而Prim算法从始至终只有一个区域
public class Code07_Prim {
    public static Set<Edge> prim(Graph graph){
        if(graph==null||graph.edges==null) return null;
        //解锁点集
        HashSet<Node> unLockNode = new HashSet<>();
        PriorityQueue<Edge> unLockEdge = new PriorityQueue<>(Comparator.comparingInt(e->e.weight));
        Set<Edge> result = new HashSet<>();
        for(Node node:graph.nodesMap.values()){//处理森林
            if(!unLockNode.contains(node)){
                unLockNode.add(node);
                unLockEdge.addAll(node.edges);//所有解锁边
                while (!unLockEdge.isEmpty()){
                    Edge minEdge = unLockEdge.poll();
                    if(!unLockNode.contains(minEdge.to)){
                        result.add(minEdge);
                        unLockNode.add(minEdge.to);
                        unLockEdge.addAll(minEdge.to.edges);
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {2,1,3},
                {5,1,2},
                {8,2,3},
                {9,3,4},
                {10,4,5},
                {11,5,2},
                {12,6,5},
                {13,2,6},
        };
        Set<Edge> result = prim(GraphGenerator.graphGenerator(matrix));
        int resultSum = 0;
        for(Edge e:result){
            resultSum+=e.weight;
        }
        System.out.println(resultSum);
    }
}
