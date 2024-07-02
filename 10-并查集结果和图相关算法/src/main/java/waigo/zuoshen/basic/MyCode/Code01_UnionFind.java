package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-24 20:16
 */

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**并查集：
 * 有若干个样本a、b、c、d…类型假设是V
 * 在并查集中一开始认为每个样本都在单独的集合里
 * 用户可以在任何时候调用如下两个方法：
 *        boolean isSameSet(V x, V y) : 查询样本x和样本y是否属于一个集合
 *        void union(V x, V y) : 把x和y各自所在集合的所有样本合并成一个集合
 * 4） isSameSet和union方法的代价越低越好
 */
public class Code01_UnionFind {
    //样本存放在并查集中的节点
    public static class Node<V>{
        private V value;

        public Node(V value) {
            this.value = value;
        }
    }
    public static class UnionSet<V>{
        private HashMap<V,Node<V>> nodes;//找到样本对应所在的集合节点
        private HashMap<Node<V>,Node<V>> parents;//找到一个样本所在节点的父节点
        private HashMap<Node<V>,Integer> sizeMap;//一个样本节点对应的集合的大小

        //将样本全部放入并查集
        public UnionSet(List<V> values) {
            this.nodes = new HashMap<>();
            this.parents = new HashMap<>();
            this.sizeMap = new HashMap<>();
            for(V value:values){
                Node<V> node = new Node<>(value);
                nodes.put(value,node);
                parents.put(node,node);
                sizeMap.put(node,1);
            }
        }
        public Node<V> findFather(Node<V> x){
            //优化，每次找的过程中将路径扁平化，使得每个样本都直接指向代表节点
            //下一次再找就是O(1)了
            Stack<Node<V>> stack = new Stack<>();
            Node<V> cur = x;
            while (cur!=parents.get(cur)){
                stack.push(cur);
                cur = parents.get(cur);
            }
            //扁平化
            while (!stack.isEmpty()){
                parents.put(stack.pop(),cur);
            }
            return cur;
        }
        public boolean isSameSet(V x, V y){
            //看看他们所在集合的代表节点是否相同，相同就是同一个样本集合
            //先看看这个两个样本是不是存在并查集中
            if(!nodes.containsKey(x)||!nodes.containsKey(y)){
                return false;
            }
            return findFather(nodes.get(x))==findFather(nodes.get(y));

        }
        public void union(V x, V y){
            if(!nodes.containsKey(x)||!nodes.containsKey(y)) return;
            if(isSameSet(x,y)) return;
            //小挂大，这样大集合里的找头能少点
            Node<V> set1 = findFather(nodes.get(x));
            Node<V> set2 = findFather(nodes.get(y));
            Node<V> small = sizeMap.get(set1)>=sizeMap.get(set2)?set2:set1;
            Node<V> big = set1==small?set2:set1;
            parents.put(small,parents.get(big));//挂大
            //更新sizeMap
            sizeMap.put(big,sizeMap.get(small)+sizeMap.get(big));
            sizeMap.remove(small);
        }

    }
}
