package waigo.zuoshen.basic.MyCode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * author waigo
 * create 2021-02-25 21:18
 */
//Breadth First Search宽度优先搜索算法，搜索和遍历一样意思
public class Code03_BFS {
    public static String nodeToString(Node node) {
        return node == null ? "" : "Node{" + node.value + "}";
    }

    public static void bfs(int[][] matrix, int begin) {
        //转成我们习惯的结构
        Graph graph = GraphGenerator.graphGenerator(matrix);
        if (!graph.nodesMap.containsKey(begin)) return;//没这个开始点直接退出，遍历不了
        //宽度优先遍历使用队列，不过这里可能存在环不能重复进队列，所以使用一个HashSet结构辅助
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> nodeSearched = new HashSet<>();
        queue.add(graph.nodesMap.get(begin));
        nodeSearched.add(graph.nodesMap.get(begin));
        while (!queue.isEmpty()) {
            Node queueTop = queue.poll();
            System.out.println(nodeToString(queueTop));
            for (Node next : queueTop.nexts) {
                if (!nodeSearched.contains(next)) {//没遍历过的才需要继续遍历
                    queue.add(next);
                    nodeSearched.add(next);
                }
            }
        }
    }
    //宽度优先遍历纯享版
    public static void bfs2(Node begin){
        if(begin==null) return;
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        queue.add(begin);
        set.add(begin);
        while (!queue.isEmpty()){
            Node top = queue.poll();
            System.out.println(nodeToString(top));
            for(Node next:top.nexts){
                if(!set.contains(next)){
                    queue.add(next);
                    set.add(next);
                }
            }
        }
    }
    public static void main(String[] args) {
        int[][] matrix =
        {
                {0,1,4},
                {0,1,2},
                {0,2,3},
                {0,3,2},
                {0,3,4},
                {0,4,5},
                {0,6,3},
                {0,5,6},
                {0,6,6},
        };
//        bfs(matrix,1);
        Graph graph = GraphGenerator.graphGenerator(matrix);
        bfs2(graph.nodesMap.get(1));
    }
}
