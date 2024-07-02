package waigo.zuoshen.basic.MyCode;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-22 20:45
 */
/*
面试题：
二叉树结构如下定义：
Class Node {
	V value;
	Node left;
	Node right;
	Node parent;
}
给你二叉树中的某个节点，返回该节点的后继节点
前驱和后继节点说的是在中序遍历中出现在某个节点前的叫前驱节点，出现在后面的叫后继节点
      1
  2      3     --> 中序序列为，4-2-5-1-6-3-7,1的前驱是5,2的后继是5
4   5  6   7
思路：1）求后继节点
            1.1）如果有右孩子，后继肯定是右孩子的最左节点
            1.2）如果没有右孩子，那说明在中序序列中，这是某个左子树的最右一个节点，下一跳就会回到它的头
     2）无独有偶，前驱也是同理
            2.1）如果有左孩子，前驱肯定是左子树的最右点
            2.2）如果没有左孩子，那说明在中序序列中，这是某个右子树最左的一个节点，它的上一个节点应该是它所在
            的这个右子树的头
*/
public class Code07_GetPreCursorOrSucceesorNode {
    public static class Node {
        private int value;
        private Node left;
        private Node right;
        private Node parent;

        public Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" + "value=" + value + '}';
        }
    }

    //for test
    public static Node generalRandomBTree(int maxLen, int maxValue) {
        return generalRandomBTree(maxLen, maxValue, 1);
    }

    //for test
    private static Node generalRandomBTree(int maxLen, int maxValue, int floor) {
        if (floor > maxLen || Math.random() < 0.35) return null;
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generalRandomBTree(maxLen, maxValue, floor + 1);
        if (head.left != null) head.left.parent = head;
        head.right = generalRandomBTree(maxLen, maxValue, floor + 1);
        if (head.right != null) head.right.parent = head;
        return head;
    }

    //for test
    public static Node getSucceedNode(Node specifyNode) {
        if (specifyNode == null) return null;
        //找到头
        Node head = specifyNode;
        while (head.parent != null) {
            head = head.parent;
        }
        //中序遍历把所有的节点都找到放到容器
        ArrayList<Node> list = new ArrayList<>();
        ins(head, list);
        int i = 0;
        for (; i < list.size(); i++) {
            if (specifyNode == list.get(i)) break;
        }
        return i == list.size() - 1 ? null : list.get(i + 1);
    }

    private static void ins(Node head, ArrayList<Node> list) {
        if (head == null) return;
        ins(head.left, list);
        list.add(head);
        ins(head.right, list);
    }

    public static Node getRandomNode(Node head) {
        ArrayList<Node> list = new ArrayList<>();
        ins(head, list);
        int index = (int) (Math.random() * list.size());
        return (index >= 0) && (index < list.size()) ? list.get(index) : null;//0~list.size-1
    }

    /**
     * 空间好的算法
     *
     * @param speNode
     * @return
     */
    public static Node getSuceedNode1(Node speNode) {
        if (speNode == null) return null;
        //第一种情况
        if (speNode.right != null) return getLeftMost(speNode.right);
        //第二种情况
        Node parent;
        //如果此时还没找到这个节点所属的左子树就继续往上走
        while (((parent = speNode.parent) != null) && (parent.left != speNode))
            speNode = parent;
        return parent;
    }

    public static Node getLeftMost(Node head) {
        while (head.left != null) {
            head = head.left;
        }
        return head;
    }

    public static void main(String[] args) {
        int times = 1000000, maxLen = 4, maxValue = 5;
        int i = 0;
        for(;i<times;i++){
            Node head = generalRandomBTree(maxLen, maxValue);
            Node randomNode = getRandomNode(head);
            if(getSucceedNode(randomNode) != getSuceedNode1(randomNode)){
               break;
            }
        }
        Logger.getGlobal().info(i==times?"finish!!!":"oops!!!");
    }

}
