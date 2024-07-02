package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-25 20:51
 */
public class GraphGenerator {
    //一种表达图的形式
    //[[weight,from,to]...]使用所有的边从哪个节点到哪里加上权值来表示这个图
    //常见的图表示法还有：邻接矩阵法，邻接表法，具体做法是将题目提供的表示方法转成我们习惯的结构，在我们的结构上完成算法
    public static Graph graphGenerator(int[][] matrix){
        Graph graph = new Graph();
        for(int i = 0; i<matrix.length;i++){
            int weight = matrix[i][0];
            int fromVal = matrix[i][1];
            int toVal = matrix[i][2];
            if(!graph.nodesMap.containsKey(fromVal)){
                graph.nodesMap.put(fromVal,new Node(fromVal));//from
            }
            if(!graph.nodesMap.containsKey(toVal)){
                graph.nodesMap.put(toVal,new Node(toVal));//to
            }
            Node from = graph.nodesMap.get(fromVal);
            Node to = graph.nodesMap.get(toVal);
            Edge f2t = new Edge(weight,from,to);
            from.edges.add(f2t);
            to.edges.add(f2t);
            to.in++;//from->to
            from.out++;
            from.nexts.add(to);
            graph.edges.add(f2t);
        }
        return graph;
    }
}
