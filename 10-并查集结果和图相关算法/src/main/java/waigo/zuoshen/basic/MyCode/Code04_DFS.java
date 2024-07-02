package waigo.zuoshen.basic.MyCode;

import java.util.HashSet;
import java.util.Stack;

/**
 * author waigo
 * create 2021-02-25 21:49
 */
//Deep First Search深度优先搜索，一条路走到头再回溯直到遍历结束
public class Code04_DFS {
    public static void dfs1(int[][] matrix, int begin) {
        Graph graph = GraphGenerator.graphGenerator(matrix);
        if (!graph.nodesMap.containsKey(begin)) return;
        Stack<Node> path = new Stack<>();
        HashSet<Node> set = new HashSet<>();
        Node beginNode = graph.nodesMap.get(begin);
        path.push(beginNode);
        set.add(beginNode);
        //由于涉及到路线回溯，所以一个节点可能会加入弹出多次，所以需要在第一次加入的时候就打印
        System.out.println("Node{" + beginNode.value + "}");
        while (!path.isEmpty()) {
            Node cur = path.pop();
            for (Node next : cur.nexts) {
                if (!set.contains(next)) {
                    path.push(cur);
                    path.push(next);
                    set.add(next);
                    System.out.println("Node{" + next.value + "}");
                    break;
                }
            }
        }
    }
    //纯享版
    public static void dfs2(Node begin){
        Stack<Node> path = new Stack<>();
        HashSet<Node> set = new HashSet<>();
        path.push(begin);
        set.add(begin);
        System.out.println("Node{"+begin.value+"}");
        while (!path.isEmpty()){
            Node cur = path.pop();
            for(Node next:cur.nexts){
                if(!set.contains(next)){
                    path.push(cur);
                    path.push(next);
                    set.add(next);
                    System.out.println("Node{"+next.value+"}");
                    break;
                }
            }
        }
    }
    public static void main(String[] args) {
        int[][] matrix = {
                {0, 1, 4},
                {0, 1, 2},
                {0, 2, 3},
                {0, 3, 2},
                {0, 3, 4},
                {0, 4, 5},
                {0, 4, 7},
                {0, 6, 3},
                {0, 5, 6},
                {0, 6, 6},
        };
//        dfs1(matrix, 1);
        Graph graph = GraphGenerator.graphGenerator(matrix);
        dfs2(graph.nodesMap.get(1));
    }
}
