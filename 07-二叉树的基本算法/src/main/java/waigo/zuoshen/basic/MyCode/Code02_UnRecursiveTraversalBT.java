package waigo.zuoshen.basic.MyCode;

import java.util.Stack;
import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-20 19:30
 */
//使用非递归方式来遍历二叉树
public class Code02_UnRecursiveTraversalBT {
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

    //口诀：1）弹打印2）如有右，压入右3）如有左，压入左
    public static void pre(Node head) {
        if (head != null) {
            StringBuilder preOrder = new StringBuilder("preOrder:");
            Stack<Node> stack = new Stack<>();
            stack.push(head);
            while (!stack.isEmpty()) {
                head = stack.pop();
                preOrder.append(head.value).append("-");
                //先右
                if (head.right != null) {
                    stack.push(head.right);
                }
                //再左
                if (head.left != null) {
                    stack.push(head.left);
                }
            }
            preOrder.deleteCharAt(preOrder.length() - 1);
            Logger.getGlobal().info(preOrder.toString());
        }
    }

    public static void in(Node head) {
        if (head != null) {
            StringBuilder inOrder = new StringBuilder("inOrder:");
            Stack<Node> stack = new Stack<>();
            //中序遍历一个树的流程，这个数的头如果不为空我就将它的左子树全部压入
            //若是碰到头为空了，表示最后一个节点的左子树处理完，就弹出最后压入的节点打印
            //处理它的右子树
            while (head != null || !stack.isEmpty()) {
                if (head != null) {//处理左
                    stack.push(head);
                    head = head.left;
                } else {//处理右
                    head = stack.pop();
                    inOrder.append(head.value).append("-");
                    head = head.right;
                }
            }
            //基本思路就是先压入[头、左...]这样的顺序，然后没有左了就pop出最后一个左边的节点，它的右如果有就压入
            // ，此时这个左节点的左子树和它自己已经处理过了，然后压入右子树处理符合中序。
            //它的右子树处理完全弹出后再次弹出的就是这个左节点的父，然后这个父又去处理它的右子树，周而复始，完成遍历
            inOrder.deleteCharAt(inOrder.length() - 1);
            Logger.getGlobal().info(inOrder.toString());
        }
    }

    //我们已经知道前序，头左右是先处理头然后有右就压入右，再有左压入左
    //修改一下就会产生一个处理头右左的序列，但是我们先不进行打印，而是再次入栈进行一个逆序那就是后序了
    public static void pos(Node head) {
        if (head != null) {
            StringBuilder posOrder = new StringBuilder("posOrder:");
            Stack<Node> stack1 = new Stack<>();
            Stack<Node> stack2 = new Stack<>();
            stack1.push(head);
            while (!stack1.isEmpty()) {
                head = stack1.pop();
                if (head.left != null) {
                    stack1.push(head.left);
                }
                if (head.right != null) {
                    stack1.push(head.right);
                }
                stack2.push(head);//弹出来的东西进栈2
            }
            while (!stack2.isEmpty()) {
                posOrder.append(stack2.pop().value).append("-");
            }
            posOrder.deleteCharAt(posOrder.length() - 1);
            Logger.getGlobal().info(posOrder.toString());
        }

    }

//    但是经过前序思路修改的后序需要多一个容器，能不能考虑只用一个容器呢？
//    可以。仔细研究中序的思路，你会发现中序的思路是先把左处理完后马上处理头，然后处理右
//    这样右子树处理完之后再次弹出来的就是这次处理的头的父，然后它去处理它的右子树
//    能不能，把左处理完之后先不处理头，然后处理右边，等右边也处理完了再处理头呢？
//    要完成这个的关键点是如何区分到底回来头这里的情况，是才完成了左边还是已经完成了右边了。
//    方案：我整个指针，每次都指向上一次处理的节点，然后回到头的时候看一下那个指针不就知道上一次完成的是左还是右了吗
    public static void pos2(Node head) {
        if (head != null) {
            StringBuilder posOrder = new StringBuilder("posOrder:");
            //也就是需要两个指针，这里就复用了head，用head来做那个移动遍历的指针
            Node last = head;//这个指针一开始还没有我们需要的意思，第一次处理了一个节点后它才有用，所以先指向一个不会影响的地方
            //指向空的话可能这个二叉树不是满二叉树，这样就会出现问题，比如一个节点只有左孩子没有右孩子，此时比对right的
            //时候null==null成立，就会不接着处理左孩子了
            Stack<Node> stack = new Stack<>();//栈还是得有的，总得记住还来不及处理的节点
            stack.push(head);
            while (!stack.isEmpty()) {
                head = stack.peek();
                if (head.left != null && last != head.left && last != head.right) {
                    //有左，而且上一次处理的不是左而且不是右，表示这个点才开始处理
                    stack.push(head.left);
                } else if (head.right != null && last != head.right) {
                    //来到这个if表示左肯定是处理过了，只要上一次没处理过右，而且有右，那就处理右去
                    stack.push(head.right);
                } else {
                    //来到这里表示左右都处理过了，处理它自己,而且将它弹出去
                    posOrder.append(stack.pop().value).append("-");
                    last = head;
                }
            }
            posOrder.deleteCharAt(posOrder.length() - 1);
            Logger.getGlobal().info(posOrder.toString());
        }
        //这里思路上用的中序的思路，但是实现上确是和前序相似，因为如果这时候用中序类似的代码实现，
        //就是一开始不压入头，这会出现这么个问题，就是遍历开始前需要先压入当前head,如果这个点左右
        //处理过了，你又得给它弹出来，一次遍历完成之后你得给head指向下一个它需要处理的点，可能没有
        //需要处理的点了，你还得判断一下栈是否为空,就是下面的pos3，可以比较一下
    }
    //这样不好，反复拿出来又放回去，影响效率

    public static void pos3(Node head) {
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            StringBuilder posOrder = new StringBuilder("posOrder:");
            Node last = head;
            while (head != null || !stack.isEmpty()) {
                stack.push(head);
                if (head.left != null && last != head.left && last != head.right) {
                    head = head.left;
                } else if (head.right != null && last != head.right) {
                    head = head.right;
                } else {
                    posOrder.append(stack.pop().value).append("-");
                    last = head;
                    if (stack.isEmpty()) break;
                    head = stack.pop();
                }
            }

            posOrder.deleteCharAt(posOrder.length() - 1);
            Logger.getGlobal().info(posOrder.toString());
        }
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.left.left = new Node(4);
        head.right = new Node(3);
        head.right.left = new Node(6);
        head.right.left.left = new Node(5);
        head.right.right = new Node(7);
        head.right.right.left = new Node(9);
        head.right.right.left.right = new Node(19);
        head.right.right.left.left = new Node(39);
        head.right.right.left.left.right = new Node(29);
        pre(head);
        in(head);
        pos(head);
        pos2(head);
        pos3(head);

    }

}
