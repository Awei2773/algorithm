package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-25 20:39
 */
//边结构描述：1）权值2）从哪个节点出发的3）到哪个节点
public class Edge {
    public int weight;
    public Node from;
    public Node to;

    public Edge(int weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
