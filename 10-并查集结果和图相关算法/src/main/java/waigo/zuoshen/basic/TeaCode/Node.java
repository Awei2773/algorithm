package waigo.zuoshen.basic.TeaCode;

import java.util.ArrayList;

// 点结构的描述
public class Node {
	public int value;
	public int in;
	public int out;
	public ArrayList<Node> nexts;//点可以重复所以使用next，可能有个环自己指向了自己呢
	public ArrayList<Edge> edges;

	public Node(int value) {
		this.value = value;
		in = 0;
		out = 0;
		nexts = new ArrayList<>();
		edges = new ArrayList<>();
	}
}
