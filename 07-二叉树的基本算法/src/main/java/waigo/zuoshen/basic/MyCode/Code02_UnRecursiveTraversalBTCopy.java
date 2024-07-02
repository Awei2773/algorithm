package waigo.zuoshen.basic.MyCode;

import java.util.Stack;
import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-20 19:30
 */
//使用非递归方式来遍历二叉树
public class Code02_UnRecursiveTraversalBTCopy {
    public static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" + "value=" + value + '}';
        }
    }
    public static void pre(Node head){
        if(head!=null){
            Stack<Node> stack = new Stack<>();
            stack.push(head);
            while (!stack.isEmpty()){
                head = stack.pop();
                System.out.print(head.value+"-");
                if(head.right!=null){
                    stack.push(head.right);
                }
                if(head.left!=null){
                    stack.push(head.left);
                }
            }
        }
    }
    public static void in(Node head){//这里一开始不压入head就将所有节点的处理方式统一了，管你是left还是right，我都当做一个子树头来处理
        if(head!=null){//也就不会出现查看Left是否为空的情况，避免死循环
            Stack<Node> stack = new Stack<>();
            while(!stack.isEmpty()||head!=null){
                if(head!=null){
                    stack.push(head);
                    head = head.left;
                }else{
                    head = stack.pop();
                    System.out.print(head.value +"-");
                    head = head.right;
                }
            }
        }
    }
    //用Head做为标志指针
    public static void pos(Node head){//这里需要判断的是左右子树是否处理了，而不是当前这个节点的情况，所以一开始需要压入
        //一开始不压入那么一上来判断的就是head是否为空，会加很多判断，不够这样子方便简洁
        if(head!=null){
            Node cur = null;
            Stack<Node> stack = new Stack<>();
            stack.push(head);
            while (!stack.isEmpty()){
                cur = stack.peek();
                if(cur.left!=null&&head!=cur.left&&head!=cur.right){
                    stack.push(cur.left);
                }else if(cur.right!=null&&head!=cur.right){
                    stack.push(cur.right);
                }else{
                    System.out.print(stack.pop().value+"-");
                    head = cur;//指向标志点
                }
            }
        }
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);
        System.out.println("\n========");
        pre(head);
        System.out.println("\n========");
        in(head);
        System.out.println("\n========");
        pos(head);

    }

}
