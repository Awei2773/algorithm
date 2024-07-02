package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-20 15:55
 */
//recursive adj.递归的 traversal n.遍历
public class Code01_RecursiveTraversalBT {
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
    //打印递归序1-2-4-4-4-2-5-5-5-2-1-3-6-6-6-3-7-7-7-3-1这就是递归序，每个节点会到达3次，
    // 第一次到达就打印是前序，中序和后序同理
    public static void logRecursiveSeq(Node head){
        if(head==null) return;
        System.out.print(head.value+"-");
        logRecursiveSeq(head.left);
        System.out.print(head.value+"-");
        logRecursiveSeq(head.right);
        System.out.print(head.value+"-");
    }
    public static void pre(Node head){
        if(head == null) return;
        System.out.print(head.value+"-");
        pre(head.left);
        pre(head.right);
    }
    public static void in(Node head){
        if(head==null) return;
        in(head.left);
        System.out.print(head.value+"-");
        in(head.right);
    }
    public static void pos(Node head){
        if(head==null) return;
        pos(head.left);
        pos(head.right);
        System.out.print(head.value+"-");
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);
        logRecursiveSeq(head);
        System.out.println("\n========");
        pre(head);
        System.out.println("\n========");
        in(head);
        System.out.println("\n========");
        pos(head);
        System.out.println("\n========");
    }
}
