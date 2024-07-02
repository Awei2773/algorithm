package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-15 9:40
 */

/**
 * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的循环双向链表。要求不能创建任何新的节点，只能调整树中节点指针的指向。
 * <p>
 * 为了让您更好地理解问题，以下面的二叉搜索树为例：
 * <p>
 * 我们希望将这个二叉搜索树转化为双向循环链表。链表中的每个节点都有一个前驱和后继指针。对于双向循环链表，第一个节点的前驱是最后一个节点，
 * 最后一个节点的后继是第一个节点。
 * <p>
 * 下图展示了上面的二叉搜索树转化成的链表。“head” 表示指向链表中有最小元素的节点。
 *  
 * 特别地，我们希望可以就地完成转换操作。当转化完成以后，树中节点的左指针需要指向前驱，树中节点的右指针需要指向后继。
 * 还需要返回链表中的第一个节点的指针。
 * <p>
 * 思路：使用树形的递归套路
 * 如何通过之前的位置快速得出当前位置这很重要
 * <p>
 * 定义递归定义，返回一个子树转成双向链表之后的头指针和尾指针，头结点和尾结点在递归完成之后再手动指，让它变成循环的
 * info{head,tail}
 * 1.特别位置，节点为空
 * 直接就是转化好的，返回{null,null}
 * 2.普通位置
 * 左子树转好，得到一个leftInfo
 * 右子树转好，得到一个rightInfo
 * 快速生成自己位置，将左右连起来，注意将前后的head,tail调好，具体看代码，挺简单的思路
 */
public class Code02_BSTtoDoubleLinkedList {
    class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }

    private static class Info {
        Node head;
        Node tail;

        public Info(Node head, Node tail) {
            this.head = head;
            this.tail = tail;
        }
    }

    public static Node treeToDoublyList(Node root) {
        if(root==null) return null;
        Info info = process(root);
        info.head.left=info.tail;
        info.tail.right=info.head;
        return info.head;
    }

    private static Info process(Node node) {
        if(node==null){
            return new Info(null,null);
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        //找到当前树转好后的前后头尾指针
        Node head = leftInfo.head==null?node:leftInfo.head;
        Node tail =rightInfo.tail==null?node:rightInfo.tail;
        //将left的tail和right的head指好当前节点
        if(leftInfo.tail!=null){
            leftInfo.tail.right=node;
        }
        if(rightInfo.head!=null){
            rightInfo.head.left=node;
        }
        //当前节点前后指好
        node.left=leftInfo.tail;
        node.right=rightInfo.head;
        return new Info(head,tail);
    }
}

