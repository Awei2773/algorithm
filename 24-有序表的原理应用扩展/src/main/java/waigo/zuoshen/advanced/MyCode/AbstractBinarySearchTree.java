package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-04-30 9:18
 */
public class AbstractBinarySearchTree<T extends Comparable<T>> {
    protected class Node {
        T value;
        Node left, right;
        Node parent;

        public Node(T value) {
            this.value = value;
        }

        public Node(T value, Node left, Node right, Node parent) {
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    private Node root;
    private int size;

    /**
     * @param value specify value to search
     * @return a Node the value mapping to the bst if exists or null;
     */
    public Node search(T value) {
        Node search = root;
        while (search != null) {
            int judge = search.value.compareTo(value);
            if (judge < 0) {
                search = search.right;
            } else if (judge > 0) {
                search = search.left;
            } else {
                break;
            }
        }
        return search;
    }

    /**
     * insert the value into the bst
     *
     * @param value specify value to insert
     * @return a Node the value mapping to the bst
     */
    public Node insert(T value) {
        Node newNode = new Node(value);//the node prepare to insert
        //1.the bst is empty
        if (root == null) {
            this.root = newNode;
            return newNode;
        }
        //2.find a father where the value should insert in.
        Node father = root, son = root;
        while (son != null) {
            father = son;
            //1. right slip（value>son.value）
            if (father.value.compareTo(value) < 0) {
                son = father.right;
                //2.left slip(value<=son.value),ignore all repeat value
            } else {
                son = father.left;
            }
        }
        if (father.value.compareTo(value) < 0) {
            father.right = newNode;
        } else {
            father.left = newNode;
        }
        newNode.parent = father;
        size++;
        return newNode;
    }

    private void printTreeInOrder(Node root) {
        if (root == null) return;
        printTreeInOrder(root.left);
        System.out.print(root.value + "|");
        printTreeInOrder(root.right);
    }

    public void printTreeInOrder() {
        printTreeInOrder(root);
    }

    /**
     * delete a node which the value mapping
     *
     * @param value specify value
     * @return the deleted node
     */
    public Node delete(T value) {
        Node search = search(value);
        return search == null ? null : delete(search);
    }

    /**
     * delete specify node
     *
     * @param delNode will be deleted
     * @return the delNode's parent
     */
    private Node delete(Node delNode) {
        //find successor who can replace my position
        Node successor = getSuccessor(delNode);
        //if successor is null or delNode's child,we should't to do anymore,delete is successful.
        replaceEnv(delNode, successor);
        //otherwise,we should take deleted node's child to it.
        if (successor != null && delNode.left != null && delNode.right != null) {
            successor.left = delNode.left;
            delNode.left.parent = successor;
            successor.right = delNode.right;
            delNode.right.parent = successor;
        }
        return successor;
    }

    private Node getSuccessor(Node delNode) {
        if (delNode.left == null && delNode.right == null) return null;
        Node successor;
        if (delNode.left != null && delNode.right != null) {
            successor = getMostLeft(delNode.right);
            replaceEnv(successor, successor.right);//resolve successor's problem
        } else {
            successor = delNode.left == null ? delNode.right : delNode.left;
        }
        return successor;
    }

    /**
     * set old node's parent point to the new node
     *
     * @param nodeBeReplaced can't be null
     * @param newNode        possible be null
     */
    private void replaceEnv(Node nodeBeReplaced, Node newNode) {
        if (nodeBeReplaced.parent != null) {
            if (nodeBeReplaced.parent.left == nodeBeReplaced) {
                nodeBeReplaced.parent.left = newNode;
            } else {
                nodeBeReplaced.parent.right = newNode;
            }
        } else {
            root = newNode;
        }
        if (newNode != null) {
            newNode.parent = nodeBeReplaced.parent;
        }
    }

    private Node getMostLeft(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    public  void printBTree(Node head) {
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
    private  void process(Node head, int floor, int N, String type) {
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
    private  String formatVal(String val, int N) {
        int length = val.length();
        if (length > N) return val;
        int spaceFront = (N - length) / 2;
        return getSpace(spaceFront) + val + getSpace(N - spaceFront);
    }

    private  String getSpace(int len) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }
    public static void main(String[] args) {
        AbstractBinarySearchTree<Integer> bst = new AbstractBinarySearchTree<>();
        bst.insert(3);
        bst.insert(4);
        bst.insert(5);
        bst.insert(3);
        bst.insert(3);
        bst.delete(3);
        bst.printBTree(bst.root);
    }
}
