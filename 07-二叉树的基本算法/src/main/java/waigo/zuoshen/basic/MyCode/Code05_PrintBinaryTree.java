package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-22 18:53
 */
//如何设计一个打印整棵树的打印函数
//    2
//  4   5
//2  2 4  3
//这题的要求是整棵树打印出来，要一眼就看树的结构
public class Code05_PrintBinaryTree {
    public static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static void printBTree(Node head) {
        System.out.println("---------------let's get it-------------");
        process(head, 0, 17, "H");
        System.out.println("--------------------end-----------------");
    }

    /**
     * @param head  要打印的树头节点
     * @param floor 表示层数，根是第0层
     * @param N     N表示打印的一个块，用来对齐节点，比如打印一个34234，就这样______34234______，_是空格的意思
     * @param type  头结点：H3H，这样出来，左孩子^3^这样，右孩子v4v这样，^和v表示箭头指向头
     */
    private static void process(Node head, int floor, int N, String type) {
        if (head == null) return;
        process(head.right, floor + 1, N, "v");
        System.out.println(getSpace(floor * N) + formatVal(type + head.value + type, N));
        process(head.left, floor + 1, N, "^");
    }

    /**
     * @param val 值
     * @param N   一个打印块的长度
     * @return 返回标准化后的值
     */
    private static String formatVal(String val, int N) {
        int length = val.length();
        if (length > N) return val;
        int spaceFront = (N - length) / 2;
        return getSpace(spaceFront) + val + getSpace(N - spaceFront);
    }

    private static String getSpace(int len) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }
    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.35) return null;
        Node head = new Node((int) (Math.random() * maxValue)-(int) (Math.random() * maxValue));
        head.left = generate(level+1, maxLevel, maxValue);
        head.right = generate(level+1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(-222222222);
        head.right = new Node(3);
        head.left.left = new Node(Integer.MIN_VALUE);
        head.right.left = new Node(55555555);
        head.right.right = new Node(66);
        head.left.left.right = new Node(777);
        printBTree(head);

        head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.right.left = new Node(5);
        head.right.right = new Node(6);
        head.left.left.right = new Node(7);
        printBTree(head);

        head = new Node(1);
        head.left = new Node(1);
        head.right = new Node(1);
        head.left.left = new Node(1);
        head.right.left = new Node(1);
        head.right.right = new Node(1);
        head.left.left.right = new Node(1);
        printBTree(head);

        printBTree(generateRandomBST(4,100));

    }
}
