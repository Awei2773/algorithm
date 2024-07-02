package waigo.zuoshen.basic.MyCode;

import java.util.*;

/**
 * author waigo
 * create 2021-02-25 23:31
 */
//最小生成树：将连通图中所有的节点连起来并且所有边的权值和是最小的，连通图：任意两点之间可达
//Kruskal算法，俗称k算法，将所有的边从小到大进行考虑，
//如果新考虑的边和已经考虑过的旧的集合不会形成环的话，则将这个线加入最小生成树中
//这个涉及到连通区的合并，使用并查集来做最好了，并查集中除了代表节点之外是不会产生环的

public class Code06_Kruskal {
    public static class UnionSet {
        private HashMap<Node, Node> parents;
        private HashMap<Node, Integer> sizeMap;

        public UnionSet(Collection<? extends Node> nodes) {
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            if (nodes != null) {
                for (Node node : nodes) {
                    parents.put(node, node);
                    sizeMap.put(node, 1);
                }
            }
        }

        public Node findFather(Node v) {
            Node cur = v;
            Stack<Node> stack = new Stack<>();
            while (cur != parents.get(cur)) {
                cur = parents.get(cur);
                stack.push(cur);
            }
            //扁平化
            while (!stack.isEmpty()) {
                parents.put(stack.pop(), cur);
            }
            return cur;
        }

        public boolean isSameSet(Node a, Node b) {
            if (!parents.containsKey(a) || !parents.containsKey(b)) return false;
            return findFather(a) == findFather(b);
        }

        public void union(Node a, Node b) {
            if (isSameSet(a, b)) return;
            Node aFather = findFather(a);
            Node bFather = findFather(b);
            Node big = sizeMap.get(aFather) > sizeMap.get(bFather) ? aFather : bFather;
            Node small = aFather == big ? bFather : aFather;
            parents.put(small, big);//小挂大
            sizeMap.put(big, sizeMap.get(small) + sizeMap.get(big));
            sizeMap.remove(small);
        }
    }

    public static Set<Edge> kruskal(Graph graph) {
        if (graph == null) return null;
        PriorityQueue<Edge> smallHeap = new PriorityQueue<>(Comparator.comparingInt(o -> o.weight));
        smallHeap.addAll(graph.edges);
        UnionSet unionFind = new UnionSet(graph.nodesMap.values());
        Set<Edge> result = new HashSet<>();
        while (!smallHeap.isEmpty()){
            Edge minEdge = smallHeap.poll();
            if(!unionFind.isSameSet(minEdge.from,minEdge.to)){
                unionFind.union(minEdge.from,minEdge.to);
                result.add(minEdge);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {100,3,4},
                {100,4,3},
                {10,3,5},
                {10,5,3},
                {3,5,4},
                {3,4,5},
                {5,5,6},
                {5,6,5},
                {15,6,9},
                {15,9,6},
                {27,9,8},
                {27,8,9},
                {30,6,7},
                {30,7,6},
                {48,7,8},
                {48,8,7},
                {3,6,8},
                {3,8,6}
        };
        Set<Edge> smallTreeEdges = kruskal(GraphGenerator.graphGenerator(matrix));
        for(Edge edge:smallTreeEdges){
            System.out.println("Edge{weight:"+edge.weight+",from:"+edge.from.value+",to:"+edge.to.value+"}");
        }
    }
}
