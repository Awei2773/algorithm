package waigo.zuoshen.basic.MyCode;

import java.util.HashMap;
import java.util.HashSet;

/**
 * author waigo
 * create 2021-02-25 20:39
 */
//图：由点集和边集构成
public class Graph {
    public HashMap<Integer,Node> nodesMap;//一个点的值或编号可以找到它对应的节点
    public HashSet<Edge> edges;//所有的边

    public Graph() {
        nodesMap = new HashMap<>();
        edges = new HashSet<>();
    }
}
