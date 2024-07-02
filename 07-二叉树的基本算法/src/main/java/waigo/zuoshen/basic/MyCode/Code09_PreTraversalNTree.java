package waigo.zuoshen.basic.MyCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * author waigo
 * create 2021-03-27 23:24
 */
/*
给定一个 N 叉树，返回其节点值的 前序遍历 。
N 叉树 在输入中按层序遍历进行序列化表示，每组子节点由空值 null 分隔（请参见示例）。
进阶：
递归法很简单，你可以使用迭代法完成此题吗?
*/
public class Code09_PreTraversalNTree {
    public static class Node {
        public int val;
        public List<Node> children = new ArrayList<>();

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }
    public static List<Integer> preorder(Node root) {
        if(root==null) return new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        Node cur;
        while (!stack.isEmpty()){
            cur = stack.pop();
            res.add(cur.val);
            for(int i = cur.children.size()-1;i>=0;i--){
                stack.push(cur.children.get(i));
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        Node e = new Node(3);
        head.children.add(e);
        head.children.add(new Node(2));
        head.children.add(new Node(4));
        e.children.add(new Node(5));
        e.children.add(new Node(6));
        List<Integer> preorder = preorder(head);
        System.out.println(Arrays.toString(preorder.toArray()));
    }
}
