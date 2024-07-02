package waigo.zuoshen.basic.TeaCode;

import java.util.HashMap;
import java.util.HashSet;

public class Graph {
	public HashMap<Integer, Node> nodes;
	public HashSet<Edge> edges;//边不会重复所以使用HashSet
	
	public Graph() {
		nodes = new HashMap<>();
		edges = new HashSet<>();
	}
}
