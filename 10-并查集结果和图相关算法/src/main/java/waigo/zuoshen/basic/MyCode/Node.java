package waigo.zuoshen.basic.MyCode;

import java.util.ArrayList;

/**
 * author waigo
 * create 2021-02-25 20:39
 */
//一个点需要哪些信息？入度：in,出度：out,直接邻居：nexts,连接到它或者它发出的边：edges，点的编号：value
public class Node {
    public int value;
    public int in;
    public int out;
    public ArrayList<Edge> edges;
    public ArrayList<Node> nexts;

    public Node(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
